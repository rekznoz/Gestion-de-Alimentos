package daw2a.gestionalimentos.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que maneja las excepciones globales de la aplicación.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manejador para errores de validación.
     * @param ex Excepción de validación
     * @return Mapa con los errores de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    /**
     * Manejador para errores de recurso no encontrado.
     * @param ex Excepción de recurso no encontrado
     * @return Mensaje de error
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<String> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Manejador para errores de recurso duplicado.
     * @param ex Excepción de recurso duplicado
     * @return Mensaje de error
     */
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<String> handleRecursoDuplicadoException(RecursoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Manejador para errores de violación de integridad de datos.
     * @param ex Excepción de violación de integridad de datos
     * @return Mensaje de error
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar el usuario porque tiene inventarios asociados. Elimine los inventarios primero.");
    }


}
