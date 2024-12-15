package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.usuario.UsuarioDTO;
import daw2a.gestionalimentos.dto.usuario.UsuarioCreateDTO;
import daw2a.gestionalimentos.dto.usuario.UsuarioUpdateDTO;
import daw2a.gestionalimentos.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de la entidad Usuario
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene todos los usuarios
     *
     * @param pageable
     * @param username
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> getAllUsuario(
            @RequestParam(required = false) String username,
            Pageable pageable
    ) {
        try {
            if (username != null) {
                return ResponseEntity.ok(usuarioService.getUsuarioByUsername(username, pageable));
            } else {
                return ResponseEntity.ok(usuarioService.getAllUsuario(pageable));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene un usuario por su id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.getUsuarioById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un usuario
     *
     * @param usuarioCreateDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        try {
            return ResponseEntity.ok(usuarioService.createUsuario(usuarioCreateDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un usuario
     *
     * @param id
     * @param usuarioUpdateDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO) {
        try {
            return usuarioService.updateUsuario(id, usuarioUpdateDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un usuario
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
