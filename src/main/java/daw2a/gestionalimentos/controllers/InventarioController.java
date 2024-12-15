package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.alimento.AlimentoDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoUpdateDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioCreateDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioUpdateDTO;
import daw2a.gestionalimentos.services.InventarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    /**
     * Obtiene todos los inventarios
     * @param pageable
     * @param usuarioId
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<InventarioDTO>> getAllInventarios(
            @RequestParam(required = false) Long usuarioId,
            Pageable pageable
    ) {
        try {
            if (usuarioId != null) {
                return ResponseEntity.ok(inventarioService.getInventarioByUsuarioId(usuarioId, pageable));
            } else {
                return ResponseEntity.ok(inventarioService.getAllInventarios(pageable));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene un inventario por su id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> getInventarioById(@PathVariable Long id) {
        try {
            return inventarioService.getInventarioById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un inventario
     * @param createDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<InventarioDTO> createInventario(@RequestBody @Valid InventarioCreateDTO createDTO) {
        try {
            return ResponseEntity.ok(inventarioService.createInventario(createDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un inventario
     * @param id
     * @param updateDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> updateAlimento(@PathVariable Long id, @RequestBody @Valid InventarioUpdateDTO updateDTO) {
        try {
            return ResponseEntity.ok(inventarioService.updateInventario(id, updateDTO));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un inventario
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        try {
            inventarioService.deleteInventario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
