package daw2a.gestionalimentos.exceptions;

/**
 * Excepción que se lanza cuando un recurso no se encuentra en la base de datos.
 */
public class RecursoNoEncontradoException extends Throwable {
    /**
     * Constructor de la excepción.
     * @param message Mensaje de error
     */
    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}
