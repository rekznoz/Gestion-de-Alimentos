package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.existencia.ExistenciaDTO;
import daw2a.gestionalimentos.dto.existencia.ExistenciaCreateDTO;
import daw2a.gestionalimentos.dto.existencia.ExistenciaUpdateDTO;
import daw2a.gestionalimentos.services.ExistenciaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/existencias")
public class ExistenciasController {

    private final ExistenciaService existenciaService;

    public ExistenciasController(ExistenciaService existenciaService) {
        this.existenciaService = existenciaService;
    }

    @GetMapping
    public ResponseEntity<Page<ExistenciaDTO>> getAllExistencias(Pageable pageable) {
        return ResponseEntity.ok(existenciaService.getAllExistencias(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExistenciaDTO> getExistenciasById(@PathVariable Long id) {
        return existenciaService.getExistenciasById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExistenciaDTO> createExistencias(@RequestBody ExistenciaCreateDTO createDTO) {
        return ResponseEntity.ok(existenciaService.createExistencias(createDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExistenciaDTO> updateExistencias(@PathVariable Long id, @RequestBody ExistenciaUpdateDTO updateDTO) {
        return existenciaService.updateExistencias(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExistencias(@PathVariable Long id) {
        existenciaService.deleteExistencias(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alimento/{alimento_id}")
    public ResponseEntity<List<ExistenciaDTO>> getExistenciasByAlimentoId(@PathVariable Long alimento_id) {
        return ResponseEntity.ok(existenciaService.getExistenciasByAlimentoId(alimento_id));
    }

    @GetMapping("/ubicacion/{ubicacion_id}")
    public ResponseEntity<List<ExistenciaDTO>> getExistenciasByUbicacionId(@PathVariable Long ubicacion_id) {
        return ResponseEntity.ok(existenciaService.getExistenciasByUbicacionId(ubicacion_id));
    }

    @GetMapping("/alimento/{alimento_id}/ubicacion/{ubicacion_id}")
    public ResponseEntity<List<ExistenciaDTO>> getExistenciasByAlimentoIdAndUbicacionId(@PathVariable Long alimento_id, @PathVariable Long ubicacion_id) {
        return ResponseEntity.ok(existenciaService.getExistenciasByAlimentoIdAndUbicacionId(alimento_id, ubicacion_id));
    }

    @GetMapping("/alimento/{alimento_id}/ubicacion/{ubicacion_id}/fecha")
    public ResponseEntity<List<ExistenciaDTO>> getExistenciasByAlimentoIdAndUbicacionIdOrderByFechaEntradaAsc(@PathVariable Long alimento_id, @PathVariable Long ubicacion_id) {
        return ResponseEntity.ok(existenciaService.getExistenciasByAlimentoIdAndUbicacionIdOrderByFechaEntradaAsc(alimento_id, ubicacion_id));
    }

    @GetMapping("/fecha")
    public ResponseEntity<Page<ExistenciaDTO>> findAllByOrderByFechaEntradaAsc(Pageable pageable) {
        return ResponseEntity.ok(existenciaService.findAllByOrderByFechaEntradaAsc(pageable));
    }

}
