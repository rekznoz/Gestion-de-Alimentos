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

/**
 * Controlador de la entidad Alimento
 */
@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    /**
     * Obtiene todos los alimentos
     *
     * @param pageable
     * @param nombre
     * @param abierto
     * @param perecedero
     * @param caducidad
     * @return
     */
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

    /**
     * Obtiene un alimento por su id
     *
     * @param id
     * @return
     */
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

    /**
     * Obtiene los alimentos proximos a caducar
     *
     * @param pageable
     * @return
     */
    @GetMapping("/proximos-a-caducar")
    public ResponseEntity<Page<AlimentoDTO>> getAlimentosProximosACaducar(Pageable pageable) {
        return ResponseEntity.ok(alimentoService.getAlimentosProximosACaducar(pageable));
    }

    /**
     * Obtiene los alimentos proximos a caducar por inventario
     *
     * @param id
     * @param pageable
     * @return
     */
    @GetMapping("/proximos-a-caducar/{id}")
    public ResponseEntity<Page<AlimentoDTO>> getAlimentosProximosACaducarPorInventario(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(alimentoService.getAlimentosProximosACaducarByUbicacion(id, pageable));
    }

    /**
     * Obtiene los alimentos caducados
     *
     * @param pageable
     * @return
     */
    @GetMapping("/caducados")
    public ResponseEntity<Page<AlimentoDTO>> getAlimentosCaducados(Pageable pageable) {
        return ResponseEntity.ok(alimentoService.getAlimentosCaducados(pageable));
    }

    /**
     * Obtiene los alimentos mas usados
     *
     * @param pageable
     * @return
     */
    @GetMapping("/mas-usados")
    public ResponseEntity<Page<AlimentoDTO>> getAlimentosMasUsados(Pageable pageable) {
        return ResponseEntity.ok(alimentoService.getAlimentosMasUsados(pageable));
    }

    /**
     * Obtiene los alimentos menos usados
     *
     * @param pageable
     * @return
     */
    @GetMapping("/menos-usados")
    public ResponseEntity<Page<AlimentoDTO>> getAlimentosMenosUsados(Pageable pageable) {
        return ResponseEntity.ok(alimentoService.getAlimentosMenosUsados(pageable));
    }

    /**
     * Crea un alimento
     *
     * @param createDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<AlimentoDTO> createAlimento(@RequestBody @Valid AlimentoCreateDTO createDTO) {
        return ResponseEntity.ok(alimentoService.createAlimento(createDTO));
    }

    /**
     * Actualiza un alimento
     *
     * @param id
     * @param updateDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDTO> updateAlimento(@PathVariable Long id, @RequestBody @Valid AlimentoUpdateDTO updateDTO) {
        return alimentoService.updateAlimento(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un alimento
     *
     * @param id
     * @return
     */
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
