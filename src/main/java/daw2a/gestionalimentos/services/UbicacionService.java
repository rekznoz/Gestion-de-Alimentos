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

@Service
@Transactional
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    public Page<UbicacionDTO> findAll(Pageable pageable) {
        return ubicacionRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Optional<UbicacionDTO> findById(Long id) {
        return ubicacionRepository.findById(id).map(this::convertToDTO);
    }

    public Page<UbicacionDTO> getUbicacionesByNombreUbicacion(String nombreUbicacion, Pageable pageable) {
        return ubicacionRepository.findByNombreUbicacion(EnumUbicacion.valueOf(nombreUbicacion.toUpperCase()), pageable).map(this::convertToDTO);
    }

    public UbicacionDTO createUbicacion(UbicacionCreateDTO ubicacionCreateDTO) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDescripcion(ubicacionCreateDTO.getDescripcion());
        ubicacion.setCapacidad(ubicacionCreateDTO.getCapacidad());
        ubicacion.setNombreUbicacion(ubicacionCreateDTO.getEnumUbicacion());
        return convertToDTO(ubicacionRepository.save(ubicacion));
    }

    public Optional<UbicacionDTO> updateUbicacion(Long id, UbicacionUpdateDTO ubicacionUpdateDTO) {
        return ubicacionRepository.findById(id).map(ubicacion -> {
            ubicacion.setDescripcion(ubicacionUpdateDTO.getDescripcion());
            ubicacion.setCapacidad(ubicacionUpdateDTO.getCapacidad());
            ubicacion.setNombreUbicacion(ubicacionUpdateDTO.getEnumUbicacion());
            return convertToDTO(ubicacionRepository.save(ubicacion));
        });
    }

    public void deleteUbicacion(Long id) {
        if (!ubicacionRepository.existsById(id)) {
            throw new RuntimeException("Ubicacion no encontrada con id: " + id);
        }
        ubicacionRepository.deleteById(id);
    }

    // Verificar si la ubicacion aun tiene capacidad para almacenar alimentos
    public boolean verificarCapacidadUbicacion(Long idUbicacion) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con id: " + idUbicacion));
        return ubicacion.getCapacidad() > 0;
    }

    private UbicacionDTO convertToDTO(Ubicacion ubicacion) {
        UbicacionDTO ubicacionDTO = new UbicacionDTO();
        ubicacionDTO.setId(ubicacion.getId());
        ubicacionDTO.setDescripcion(ubicacion.getDescripcion());
        ubicacionDTO.setCapacidad(ubicacion.getCapacidad());
        ubicacionDTO.setEnumUbicacion(ubicacion.getNombreUbicacion());
        return ubicacionDTO;
    }
}
