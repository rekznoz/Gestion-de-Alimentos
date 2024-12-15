package daw2a.gestionalimentos.services;

import daw2a.gestionalimentos.dto.alimento.AlimentoDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioCreateDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioUpdateDTO;
import daw2a.gestionalimentos.entities.Alimento;
import daw2a.gestionalimentos.entities.Inventario;
import daw2a.gestionalimentos.entities.Usuario;
import daw2a.gestionalimentos.repositories.InventarioRepository;
import daw2a.gestionalimentos.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<InventarioDTO> getAllInventarios(Pageable pageable) {
        return inventarioRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Optional<InventarioDTO> getInventarioById(Long id) {
        return inventarioRepository.findById(id).map(this::convertToDTO);
    }

    public Page<InventarioDTO> getInventarioByUsuarioId(Long usuarioId, Pageable pageable) {
        return inventarioRepository.findByUsuarioId(usuarioId, pageable).map(this::convertToDTO);
    }

    public InventarioDTO createInventario(InventarioCreateDTO createDTO) {
        Usuario usuario = usuarioRepository.findById(createDTO.getUsuarioId()).orElseThrow();
        Inventario inventario = new Inventario();
        inventario.setUsuario(usuario);
        inventario = inventarioRepository.save(inventario);
        return convertToDTO(inventario);
    }

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