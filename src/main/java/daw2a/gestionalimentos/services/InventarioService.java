package daw2a.gestionalimentos.services;

import daw2a.gestionalimentos.dto.inventario.InventarioDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioCreateDTO;
import daw2a.gestionalimentos.entities.Alimento;
import daw2a.gestionalimentos.entities.Inventario;
import daw2a.gestionalimentos.entities.Usuario;
import daw2a.gestionalimentos.repositories.InventarioRepository;
import daw2a.gestionalimentos.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    private final UsuarioRepository usuarioRepository;

    public InventarioService(InventarioRepository inventarioRepository, UsuarioRepository usuarioRepository) {
        this.inventarioRepository = inventarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<InventarioDTO> getInventarioById(Long id) {
        return inventarioRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<InventarioDTO> getInventarioByUsuarioId(Long usuarioId) {
        return inventarioRepository.findByUsuarioId(usuarioId).map(this::convertToDTO);
    }

    public Optional<InventarioDTO> getInventarioByUsuarioUsername(String username) {
        return inventarioRepository.findByUsuarioUsername(username).map(this::convertToDTO);
    }

    public InventarioDTO createInventario(InventarioCreateDTO createDTO) {
        Usuario usuario = usuarioRepository.findById(createDTO.getUsuarioId()).orElseThrow();
        Inventario inventario = new Inventario();
        inventario.setUsuario(usuario);
        inventario = inventarioRepository.save(inventario);
        return convertToDTO(inventario);
    }

    public boolean deleteInventario(Long id) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventarioRepository.delete(inventario);
            return true;
        }).orElse(false);
    }

    private InventarioDTO convertToDTO(Inventario inventario) {
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setId(inventario.getId());
        inventarioDTO.setUsuarioId(inventario.getUsuario().getId());
        inventarioDTO.setAlimentosIds(inventario.getAlimentos().stream().map(Alimento::getId).collect(Collectors.toList()));
        return inventarioDTO;
    }

}