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

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Servicio de la entidad Existencia
 */
@Service
public class ExistenciaService {

    private final ExistenciaRepository existenciaRepository;

    private final AlimentoRepository alimentoRepository;

    private final UbicacionRepository ubicacionRepository;

    public ExistenciaService(ExistenciaRepository existenciaRepository, AlimentoRepository alimentoRepository, UbicacionRepository ubicacionRepository) {
        this.existenciaRepository = existenciaRepository;
        this.alimentoRepository = alimentoRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    /**
     * Obtiene todas las existencias
     *
     * @param pageable
     * @return
     */
    public Page<ExistenciaDTO> getAllExistencias(Pageable pageable) {
        return existenciaRepository.findAll(pageable).map(this::convertToDTO);
    }

    /**
     * Obtiene una existencia por su id
     *
     * @param id
     * @return
     */
    public Optional<ExistenciaDTO> getExistenciasById(Long id) {
        return existenciaRepository.findById(id).map(this::convertToDTO);
    }

    /**
     * Filtra las existencias por alimento y ubicacion
     *
     * @param alimento
     * @param ubicacion
     * @param pageable
     * @return
     */
    public Page<ExistenciaDTO> filterExistencias(Long alimento, Long ubicacion, Pageable pageable) {
        Page<Existencia> existencias;
        if (alimento != null && ubicacion != null) {
            existencias = existenciaRepository.findByAlimentoIdAndUbicacionId(alimento, ubicacion, pageable);
        } else if (alimento != null) {
            existencias = existenciaRepository.findByAlimentoId(alimento, pageable);
        } else if (ubicacion != null) {
            existencias = existenciaRepository.findByUbicacionId(ubicacion, pageable);
        } else {
            existencias = existenciaRepository.findAll(pageable);
        }
        System.out.println("Ubicacion: " + ubicacion);
        return existencias.map(this::convertToDTO);
    }

    /**
     * Obtiene todas las existencias ordenadas por fecha de entrada
     *
     * @param pageable
     * @return
     */
    public Page<ExistenciaDTO> findAllByOrderByFechaEntradaAsc(Pageable pageable) {
        return existenciaRepository.findAllByOrderByFechaEntradaAsc(pageable).map(this::convertToDTO);
    }

    /**
     * Crea una existencia
     *
     * @param createDTO
     * @return
     */
    public ExistenciaDTO createExistencias(ExistenciaCreateDTO createDTO) {
        Existencia existencias = new Existencia();
        existencias.setCantidad_alimento(createDTO.getCantidadAlimento());
        existencias.setFechaEntrada(createDTO.getFechaEntrada());
        existencias.setAlimento(alimentoRepository.findById(createDTO.getAlimentoId()).orElseThrow());
        existencias.setUbicacion(ubicacionRepository.findById(createDTO.getUbicacionId()).orElseThrow());
        return convertToDTO(existenciaRepository.save(existencias));
    }

    /**
     * Actualiza una existencia
     *
     * @param id
     * @param updateDTO
     * @return
     */
    public Optional<ExistenciaDTO> updateExistencias(Long id, ExistenciaUpdateDTO updateDTO) {
        return existenciaRepository.findById(id).map(existencia -> {
            existencia.setCantidad_alimento(updateDTO.getCantidadAlimento());
            existencia.setFechaEntrada(updateDTO.getFechaEntrada());
            existencia.setAlimento(alimentoRepository.findById(updateDTO.getAlimentoId()).orElseThrow());
            existencia.setUbicacion(ubicacionRepository.findById(updateDTO.getUbicacionId()).orElseThrow());
            return convertToDTO(existenciaRepository.save(existencia));
        });
    }

    /**
     * Elimina una existencia
     *
     * @param id
     */
    public void deleteExistencias(Long id) {
        if (!existenciaRepository.existsById(id)) {
            throw new NoSuchElementException("Existencia no encontrada con id: " + id);
        }
        existenciaRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Existencia a su DTO
     *
     * @param existencias
     * @return
     */
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