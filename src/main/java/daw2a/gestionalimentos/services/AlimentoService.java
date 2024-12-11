package daw2a.gestionalimentos.services;

import daw2a.gestionalimentos.dto.alimento.AlimentoDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoCreateDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoUpdateDTO;
import daw2a.gestionalimentos.entities.Alimento;
import daw2a.gestionalimentos.repositories.AlimentoRepository;
import daw2a.gestionalimentos.repositories.InventarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;

    private final InventarioRepository inventarioUsuarioRepository;

    public AlimentoService(AlimentoRepository alimentoRepository, InventarioRepository inventarioUsuarioRepository) {
        this.alimentoRepository = alimentoRepository;
        this.inventarioUsuarioRepository = inventarioUsuarioRepository;
    }

    public Page<AlimentoDTO> getAllAlimentos(Pageable pageable) {
        return alimentoRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Optional<AlimentoDTO> getAlimentoById(Long id) {
        return alimentoRepository.findById(id).map(this::convertToDTO);
    }

    public AlimentoDTO createAlimento(AlimentoCreateDTO createDTO) {
        Alimento alimento = new Alimento();
        alimento.setNombre(createDTO.getNombre());
        alimento.setFechaCaducidad(createDTO.getFechaCaducidad());
        alimento.setAbierto(createDTO.isAbierto());
        alimento.setPerecedero(createDTO.isPerecedero());
        alimento.setInventario(inventarioUsuarioRepository.findById(createDTO.getInventarioId()).orElseThrow());
        alimentoRepository.save(alimento);
        return convertToDTO(alimento);
    }

    public Optional<AlimentoDTO> updateAlimento(Long id, AlimentoUpdateDTO updateDTO) {
        return alimentoRepository.findById(id).map(alimento -> {
            alimento.setNombre(updateDTO.getNombre());
            alimento.setFechaCaducidad(updateDTO.getFechaCaducidad());
            alimento.setAbierto(updateDTO.isAbierto());
            alimento.setPerecedero(updateDTO.isPerecedero());
            alimento.setInventario(inventarioUsuarioRepository.findById(updateDTO.getInventarioId()).orElseThrow());
            alimentoRepository.save(alimento);
            return convertToDTO(alimento);
        });
    }

    public boolean deleteAlimento(Long id) {
        return alimentoRepository.findById(id).map(alimento -> {
            alimentoRepository.delete(alimento);
            return true;
        }).orElse(false);
    }

    public Page<AlimentoDTO> getAlimentosByNombre(String nombre, Pageable pageable) {
        return alimentoRepository.findByNombreContainingIgnoreCase(nombre, pageable).map(this::convertToDTO);
    }

    public Page<AlimentoDTO> getAlimentosPerecederos(boolean perecedero, Pageable pageable) {
        return alimentoRepository.findAlimentoByPerecedero(perecedero, pageable).map(this::convertToDTO);
    }

    public Page<AlimentoDTO> getAlimentosAbiertos(boolean abierto, Pageable pageable) {
        return alimentoRepository.findAlimentoByAbierto(abierto, pageable).map(this::convertToDTO);
    }

    public Page<AlimentoDTO> getAlimentosOrderByFechaCaducidad(Pageable pageable) {
        return alimentoRepository.findAllByOrderByFechaCaducidad(pageable).map(this::convertToDTO);
    }

    public Page<AlimentoDTO> getAlimentosCaducados(LocalDate fecha, Pageable pageable) {
        return alimentoRepository.findAlimentoByFechaCaducidadBefore(fecha, pageable).map(this::convertToDTO);
    }

    private AlimentoDTO convertToDTO(Alimento alimento) {
        AlimentoDTO alimentoDTO = new AlimentoDTO();
        alimentoDTO.setId(alimento.getId());
        alimentoDTO.setNombre(alimento.getNombre());
        alimentoDTO.setFechaCaducidad(alimento.getFechaCaducidad());
        alimentoDTO.setAbierto(alimento.isAbierto());
        alimentoDTO.setPerecedero(alimento.isPerecedero());
        alimentoDTO.setInventarioId(alimento.getInventario().getId());
        return alimentoDTO;
    }

}