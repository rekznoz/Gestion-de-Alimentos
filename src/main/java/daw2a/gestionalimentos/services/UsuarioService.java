package daw2a.gestionalimentos.services;

import daw2a.gestionalimentos.dto.usuario.UsuarioDTO;
import daw2a.gestionalimentos.dto.usuario.UsuarioCreateDTO;
import daw2a.gestionalimentos.dto.usuario.UsuarioUpdateDTO;
import daw2a.gestionalimentos.entities.Usuario;
import daw2a.gestionalimentos.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Servicio de la entidad Usuario
 */
@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene todos los usuarios
     * @param pageable
     * @return
     */
    public Page<UsuarioDTO> getAllUsuario(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(this::convertToDTO);
    }

    /**
     * Obtiene un usuario por su id
     * @param id
     * @return
     */
    public UsuarioDTO getUsuarioById(Long id) {
        return usuarioRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    /**
     * Filtra los usuarios por username
     * @param username
     * @param pageable
     * @return
     */
    public Page<UsuarioDTO> getUsuarioByUsername(String username, Pageable pageable) {
        return usuarioRepository.findByUsernameContainingIgnoreCase(username, pageable).map(this::convertToDTO);
    }

    /**
     * Crea un usuario
     * @param usuarioCreateDTO
     * @return
     */
    public UsuarioDTO createUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioCreateDTO.getUsername());
        usuario.setPassword(usuarioCreateDTO.getPassword());
        usuarioRepository.save(usuario);
        return convertToDTO(usuario);
    }

    /**
     * Actualiza un usuario
     * @param id
     * @param usuarioUpdateDTO
     * @return
     */
    public Optional<UsuarioDTO> updateUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setUsername(usuarioUpdateDTO.getUsername());
            usuario.setPassword(usuarioUpdateDTO.getPassword());
            usuarioRepository.save(usuario);
            return Optional.of(convertToDTO(usuario));
        }
        return Optional.empty();
    }

    /**
     * Elimina un usuario
     * @param id
     * @return
     */
    public boolean deleteUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.delete(usuario);
            return true;
        }).orElse(false);
    }

    /**
     * Convierte un usuario en un DTO
     * @param usuario
     * @return
     */
    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        return dto;
    }

    public Usuario registrarUsuario(@Valid UsuarioCreateDTO crearUsuarioDTO) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(crearUsuarioDTO.getUsername());
        nuevoUsuario.setPassword(crearUsuarioDTO.getPassword());
        return usuarioRepository.save(nuevoUsuario);
    }
}
