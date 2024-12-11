package daw2a.gestionalimentos.services;

import daw2a.gestionalimentos.dto.existencia.ExistenciaDTO;
import daw2a.gestionalimentos.dto.existencia.ExistenciaCreateDTO;
import daw2a.gestionalimentos.dto.existencia.ExistenciaUpdateDTO;
import daw2a.gestionalimentos.entities.Existencia;
import daw2a.gestionalimentos.repositories.ExistenciaRepository;
import daw2a.gestionalimentos.repositories.AlimentoRepository;
import daw2a.gestionalimentos.repositories.UbicacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExistenciaService {

    private final ExistenciaRepository existenciaRepository;

    private final AlimentoRepository alimentoRepository;

    private final UbicacionRepository ubicacionRepository;

    public ExistenciaService(ExistenciaRepository existenciaRepository, AlimentoRepository alimentoRepository, UbicacionRepository ubicacionRepository) {
        this.existenciaRepository = existenciaRepository;
        this.alimentoRepository = alimentoRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    public Page<ExistenciaDTO> getAllExistencias(Pageable pageable) {
        return existenciaRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Optional<ExistenciaDTO> getExistenciasById(Long id) {
        return existenciaRepository.findById(id).map(this::convertToDTO);
    }

    public ExistenciaDTO createExistencias(ExistenciaCreateDTO createDTO) {
        Existencia existencias = new Existencia();
        existencias.setCantidad_alimento(createDTO.getCantidadAlimento());
        existencias.setFechaEntrada(createDTO.getFechaEntrada());
        existencias.setAlimento(alimentoRepository.findById(createDTO.getAlimentoId()).orElseThrow());
        existencias.setUbicacion(ubicacionRepository.findById(createDTO.getUbicacionId()).orElseThrow());
        return convertToDTO(existenciaRepository.save(existencias));
    }

    public Optional<ExistenciaDTO> updateExistencias(Long id, ExistenciaUpdateDTO updateDTO) {
        return existenciaRepository.findById(id).map(existencia -> {
            existencia.setCantidad_alimento(updateDTO.getCantidadAlimento());
            existencia.setFechaEntrada(updateDTO.getFechaEntrada());
            existencia.setAlimento(alimentoRepository.findById(updateDTO.getAlimentoId()).orElseThrow());
            existencia.setUbicacion(ubicacionRepository.findById(updateDTO.getUbicacionId()).orElseThrow());
            return convertToDTO(existenciaRepository.save(existencia));
        });
    }

    public boolean deleteExistencias(Long id) {
        return existenciaRepository.findById(id).map(existencia -> {
            existenciaRepository.delete(existencia);
            return true;
        }).orElse(false);
    }

    public List<ExistenciaDTO> findByAlimentoId(Long alimentoId) {
        return existenciaRepository.findByAlimentoId(alimentoId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Page<ExistenciaDTO> findAllByOrderByFechaEntradaAsc(Pageable pageable) {
        return existenciaRepository.findAllByOrderByFechaEntradaAsc(pageable).map(this::convertToDTO);
    }

    public List<ExistenciaDTO> findByUbicacionId(Long ubicacionId) {
        return existenciaRepository.findByUbicacionId(ubicacionId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ExistenciaDTO> findByAlimentoIdAndUbicacionId(Long alimentoId, Long ubicacionId) {
        return existenciaRepository.findByAlimentoIdAndUbicacionId(alimentoId, ubicacionId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ExistenciaDTO> findByAlimentoIdAndUbicacionIdOrderByFechaEntradaAsc(Long alimentoId, Long ubicacionId) {
        return existenciaRepository.findByAlimentoIdAndUbicacionIdOrderByFechaEntradaAsc(alimentoId, ubicacionId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ExistenciaDTO convertToDTO(Existencia existencias) {
        ExistenciaDTO existenciasDTO = new ExistenciaDTO();
        existenciasDTO.setId(existencias.getId());
        existenciasDTO.setCantidadAlimento(existencias.getCantidad_alimento());
        existenciasDTO.setFechaEntrada(existencias.getFechaEntrada());
        existenciasDTO.setAlimentoId(existencias.getAlimento().getId());
        existenciasDTO.setUbicacionId(existencias.getUbicacion().getId());
        return existenciasDTO;
    }
}