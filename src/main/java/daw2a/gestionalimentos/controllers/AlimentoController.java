package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.alimento.AlimentoDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoCreateDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoUpdateDTO;
import daw2a.gestionalimentos.services.AlimentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    @GetMapping
    public ResponseEntity<Page<AlimentoDTO>> getAllAlimentos(Pageable pageable) {
        try {
            return ResponseEntity.ok(alimentoService.getAllAlimentos(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoDTO> getAlimentoById(@PathVariable Long id) {
        try {
            return alimentoService.getAlimentoById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AlimentoDTO> createAlimento(@RequestBody @Valid AlimentoCreateDTO createDTO) {
        return ResponseEntity.ok(alimentoService.createAlimento(createDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDTO> updateAlimento(@PathVariable Long id, @RequestBody @Valid AlimentoUpdateDTO updateDTO) {
        return alimentoService.updateAlimento(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlimento(@PathVariable Long id) {
        try {
            alimentoService.deleteAlimento(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre")
    public ResponseEntity<Page<AlimentoDTO>> getAlimentosByNombre(@RequestParam String nombre, Pageable pageable) {
        try {
            return ResponseEntity.ok(alimentoService.getAlimentosByNombre(nombre, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/caducados")
    public ResponseEntity<Page<AlimentoDTO>> getAlimentosCaducados(@RequestParam LocalDate fecha, Pageable pageable) {
        try {
            return ResponseEntity.ok(alimentoService.getAlimentosCaducados(fecha, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/abierto")
    public ResponseEntity<Page<AlimentoDTO>> getAlimentosAbiertos(@RequestParam boolean abierto, Pageable pageable) {
        try {
            return ResponseEntity.ok(alimentoService.getAlimentosAbiertos(abierto, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/perecedero")
    public ResponseEntity<Page<AlimentoDTO>> findAlimentoByPerecedero(@RequestParam boolean perecedero, Pageable pageable) {
        try {
            return ResponseEntity.ok(alimentoService.getAlimentosPerecederos(perecedero, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/caducidad")
    public ResponseEntity<Page<AlimentoDTO>> findAllByOrderByFechaCaducidad(Pageable pageable) {
        try {
            return ResponseEntity.ok(alimentoService.getAlimentosOrderByFechaCaducidad(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
