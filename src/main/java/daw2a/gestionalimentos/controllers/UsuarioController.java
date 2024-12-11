package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.usuario.UsuarioDTO;
import daw2a.gestionalimentos.dto.usuario.UsuarioCreateDTO;
import daw2a.gestionalimentos.dto.usuario.UsuarioUpdateDTO;
import daw2a.gestionalimentos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> getAllUsuario(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.getAllUsuario(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<UsuarioDTO>> getUsuarioById(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<UsuarioDTO>> getUsuarioByUsername(@RequestParam String username, Pageable pageable) {
        return ResponseEntity.ok(usuarioService.getUsuarioByUsername(username, pageable));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        return ResponseEntity.ok(usuarioService.createUsuario(usuarioCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        return ResponseEntity.of(usuarioService.updateUsuario(id, usuarioUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.deleteUsuario(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
