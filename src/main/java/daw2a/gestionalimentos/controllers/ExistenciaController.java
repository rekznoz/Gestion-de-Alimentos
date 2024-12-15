package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.existencia.ExistenciaDTO;
import daw2a.gestionalimentos.dto.existencia.ExistenciaCreateDTO;
import daw2a.gestionalimentos.dto.existencia.ExistenciaUpdateDTO;
import daw2a.gestionalimentos.services.ExistenciaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


/**
 * Controlador de la entidad Existencia
 */
@RestController
@RequestMapping("/existencias")
public class ExistenciaController {

    private final ExistenciaService existenciaService;

    public ExistenciaController(ExistenciaService existenciaService) {
        this.existenciaService = existenciaService;
    }

    /**
     * Obtiene todas las existencias
     *
     * @param pageable
     * @param alimento
     * @param ubicacion
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<ExistenciaDTO>> getAllExistencias(
            @RequestParam(required = false) Long alimento,
            @RequestParam(required = false) Long ubicacion,
            Pageable pageable
    ) {
        try {
            if (alimento != null || ubicacion != null) {
                return ResponseEntity.ok(existenciaService.filterExistencias(alimento, ubicacion, pageable));
            } else {
                return ResponseEntity.ok(existenciaService.getAllExistencias(pageable));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene una existencia por su id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExistenciaDTO> getExistenciasById(@PathVariable Long id) {
        try {
            return existenciaService.getExistenciasById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea una existencia
     *
     * @param createDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<ExistenciaDTO> createExistencias(@RequestBody @Valid ExistenciaCreateDTO createDTO) {
        try {
            return ResponseEntity.ok(existenciaService.createExistencias(createDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza una existencia
     *
     * @param id
     * @param updateDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExistenciaDTO> updateExistencias(@PathVariable Long id, @RequestBody @Valid ExistenciaUpdateDTO updateDTO) {
        try {
            return existenciaService.updateExistencias(id, updateDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una existencia
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExistencias(@PathVariable Long id) {
        try {
            existenciaService.deleteExistencias(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todas las existencias ordenadas por fecha de entrada
     *
     * @param pageable
     * @return
     */
    @GetMapping("/fecha")
    public ResponseEntity<Page<ExistenciaDTO>> findAllByOrderByFechaEntradaAsc(Pageable pageable) {
        try {
            return ResponseEntity.ok(existenciaService.findAllByOrderByFechaEntradaAsc(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
