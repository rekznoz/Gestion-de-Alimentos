package daw2a.gestionalimentos.services;

import daw2a.gestionalimentos.dto.ubicacion.UbicacionDTO;
import daw2a.gestionalimentos.dto.ubicacion.UbicacionCreateDTO;
import daw2a.gestionalimentos.dto.ubicacion.UbicacionUpdateDTO;
import daw2a.gestionalimentos.entities.Ubicacion;
import daw2a.gestionalimentos.enums.EnumUbicacion;
import daw2a.gestionalimentos.repositories.UbicacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Servicio de la entidad Ubicacion
 */
@Service
@Transactional
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    /**
     * Obtiene todas las ubicaciones
     * @param pageable
     * @return
     */
    public Page<UbicacionDTO> findAll(Pageable pageable) {
        return ubicacionRepository.findAll(pageable).map(this::convertToDTO);
    }

    /**
     * Obtiene una ubicacion por su id
     * @param id
     * @return
     */
    public Optional<UbicacionDTO> findById(Long id) {
        return ubicacionRepository.findById(id).map(this::convertToDTO);
    }

    /**
     * Obtiene una ubicacion por su nombre
     * @param nombreUbicacion
     * @param pageable
     * @return
     */
    public Page<UbicacionDTO> getUbicacionesByNombreUbicacion(String nombreUbicacion, Pageable pageable) {
        return ubicacionRepository.findByNombreUbicacion(EnumUbicacion.valueOf(nombreUbicacion.toUpperCase()), pageable).map(this::convertToDTO);
    }

    /**
     * Crea una ubicacion
     * @param ubicacionCreateDTO
     * @return
     */
    public UbicacionDTO createUbicacion(UbicacionCreateDTO ubicacionCreateDTO) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDescripcion(ubicacionCreateDTO.getDescripcion());
        ubicacion.setCapacidad(ubicacionCreateDTO.getCapacidad());
        ubicacion.setNombreUbicacion(ubicacionCreateDTO.getEnumUbicacion());
        return convertToDTO(ubicacionRepository.save(ubicacion));
    }

    /**
     * Actualiza una ubicacion
     * @param id
     * @param ubicacionUpdateDTO
     * @return
     */
    public Optional<UbicacionDTO> updateUbicacion(Long id, UbicacionUpdateDTO ubicacionUpdateDTO) {
        return ubicacionRepository.findById(id).map(ubicacion -> {
            ubicacion.setDescripcion(ubicacionUpdateDTO.getDescripcion());
            ubicacion.setCapacidad(ubicacionUpdateDTO.getCapacidad());
            ubicacion.setNombreUbicacion(ubicacionUpdateDTO.getEnumUbicacion());
            return convertToDTO(ubicacionRepository.save(ubicacion));
        });
    }

    /**
     * Elimina una ubicacion
     * @param id
     */
    public void deleteUbicacion(Long id) {
        if (!ubicacionRepository.existsById(id)) {
            throw new RuntimeException("Ubicacion no encontrada con id: " + id);
        }
        ubicacionRepository.deleteById(id);
    }

    /**
     * Verifica si una ubicacion tiene capacidad
     * @param idUbicacion
     * @return
     */
    public boolean verificarCapacidadUbicacion(Long idUbicacion) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con id: " + idUbicacion));
        return ubicacion.getCapacidad() > 0;
    }

    /**
     * Convierte una entidad Ubicacion a su DTO
     * @param ubicacion
     * @return
     */
    private UbicacionDTO convertToDTO(Ubicacion ubicacion) {
        UbicacionDTO ubicacionDTO = new UbicacionDTO();
        ubicacionDTO.setId(ubicacion.getId());
        ubicacionDTO.setDescripcion(ubicacion.getDescripcion());
        ubicacionDTO.setCapacidad(ubicacion.getCapacidad());
        ubicacionDTO.setEnumUbicacion(ubicacion.getNombreUbicacion());
        return ubicacionDTO;
    }
}
