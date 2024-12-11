package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.inventario.InventarioDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioCreateDTO;
import daw2a.gestionalimentos.services.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario-usuario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> getInventarioById(@PathVariable Long id) {
        return inventarioService.getInventarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<InventarioDTO> getInventarioByUsuarioId(@PathVariable Long usuarioId) {
        return inventarioService.getInventarioByUsuarioId(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{username}")
    public ResponseEntity<InventarioDTO> getInventarioByUsuarioUsername(@PathVariable String username) {
        return inventarioService.getInventarioByUsuarioUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> createInventario(@RequestBody InventarioCreateDTO createDTO) {
        return ResponseEntity.ok(inventarioService.createInventario(createDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        return inventarioService.deleteInventario(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
