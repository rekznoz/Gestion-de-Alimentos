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

import java.util.List;

@RestController
@RequestMapping("/existencias")
public class ExistenciaController {

    private final ExistenciaService existenciaService;

    public ExistenciaController(ExistenciaService existenciaService) {
        this.existenciaService = existenciaService;
    }

    @GetMapping
    public ResponseEntity<Page<ExistenciaDTO>> getAllExistencias(
            @RequestParam(required = false) Long alimento,
            @RequestParam(required = false) Long ubicacion,
            Pageable pageable
    ) {
        try {
            if (alimento != null || ubicacion != null) {
                return ResponseEntity.ok(existenciaService.filterExistencias(alimento, ubicacion, pageable));
            }else {
                return ResponseEntity.ok(existenciaService.getAllExistencias(pageable));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

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

    @PostMapping
    public ResponseEntity<ExistenciaDTO> createExistencias(@RequestBody @Valid ExistenciaCreateDTO createDTO) {
        try {
            return ResponseEntity.ok(existenciaService.createExistencias(createDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExistencias(@PathVariable Long id) {
        try {
            existenciaService.deleteExistencias(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/fecha")
    public ResponseEntity<Page<ExistenciaDTO>> findAllByOrderByFechaEntradaAsc(Pageable pageable) {
        try {
            return ResponseEntity.ok(existenciaService.findAllByOrderByFechaEntradaAsc(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
