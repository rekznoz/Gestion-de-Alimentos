package daw2a.gestionalimentos.services;

import daw2a.gestionalimentos.dto.inventario.InventarioDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioCreateDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioUpdateDTO;
import daw2a.gestionalimentos.entities.Alimento;
import daw2a.gestionalimentos.entities.Inventario;
import daw2a.gestionalimentos.entities.Usuario;
import daw2a.gestionalimentos.repositories.InventarioRepository;
import daw2a.gestionalimentos.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de la entidad Inventario
 */
@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    private final UsuarioRepository usuarioRepository;

    public InventarioService(InventarioRepository inventarioRepository, UsuarioRepository usuarioRepository) {
        this.inventarioRepository = inventarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtiene todos los inventarios
     *
     * @param pageable
     * @return
     */
    public Page<InventarioDTO> getAllInventarios(Pageable pageable) {
        return inventarioRepository.findAll(pageable).map(this::convertToDTO);
    }

    /**
     * Obtiene un inventario por su id
     *
     * @param id
     * @return
     */
    public Optional<InventarioDTO> getInventarioById(Long id) {
        return inventarioRepository.findById(id).map(this::convertToDTO);
    }

    /**
     * Obtiene un inventario por el id de un usuario
     *
     * @param usuarioId
     * @param pageable
     * @return
     */
    public Page<InventarioDTO> getInventarioByUsuarioId(Long usuarioId, Pageable pageable) {
        return inventarioRepository.findByUsuarioId(usuarioId, pageable).map(this::convertToDTO);
    }

    /**
     * Crea un inventario
     *
     * @param createDTO
     * @return
     */
    public InventarioDTO createInventario(InventarioCreateDTO createDTO) {
        Usuario usuario = usuarioRepository.findById(createDTO.getUsuarioId()).orElseThrow();
        Inventario inventario = new Inventario();
        inventario.setUsuario(usuario);
        inventario = inventarioRepository.save(inventario);
        return convertToDTO(inventario);
    }

    /**
     * Actualiza un inventario
     *
     * @param id
     * @param updateDTO
     * @return
     */
    public InventarioDTO updateInventario(Long id, InventarioUpdateDTO updateDTO) {
        Inventario inventario = inventarioRepository.findById(id).orElseThrow();
        Usuario usuario = usuarioRepository.findById(updateDTO.getUsuarioId()).orElseThrow();
        inventario.setUsuario(usuario);
        inventario.setAlimentos(updateDTO.getAlimentosIds().stream().map(alimentoId -> {
            Alimento alimento = new Alimento();
            alimento.setId(alimentoId);
            return alimento;
        }).collect(Collectors.toList()));
        inventario = inventarioRepository.save(inventario);
        return convertToDTO(inventario);
    }

    /**
     * Elimina un inventario
     *
     * @param id
     * @return
     */
    public boolean deleteInventario(Long id) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventarioRepository.delete(inventario);
            return true;
        }).orElse(false);
    }

    /**
     * Convierte un inventario a DTO
     *
     * @param inventario
     * @return
     */
    private InventarioDTO convertToDTO(Inventario inventario) {
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setId(inventario.getId());
        inventarioDTO.setUsuarioId(inventario.getUsuario().getId());
        inventarioDTO.setAlimentosIds(inventario.getAlimentos().stream().map(Alimento::getId).collect(Collectors.toList()));
        return inventarioDTO;
    }

}