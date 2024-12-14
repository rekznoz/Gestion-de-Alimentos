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
    public ResponseEntity<Page<AlimentoDTO>> getAllAlimentos(
            Pageable pageable,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) boolean abierto,
            @RequestParam(required = false) boolean perecedero,
            @RequestParam(required = false) LocalDate caducidad
    ) {
        try {
            if (nombre != null || abierto || perecedero || caducidad != null) {
                return ResponseEntity.ok(alimentoService.filtrarAlimentos(nombre, abierto, perecedero, caducidad, pageable));
            } else {
                return ResponseEntity.ok(alimentoService.getAllAlimentos(pageable));
            }
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

}
