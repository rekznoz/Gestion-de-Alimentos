package daw2a.gestionalimentos.services;

import daw2a.gestionalimentos.dto.alimento.AlimentoDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoCreateDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoUpdateDTO;
import daw2a.gestionalimentos.entities.Alimento;
import daw2a.gestionalimentos.repositories.AlimentoRepository;
import daw2a.gestionalimentos.repositories.InventarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de la entidad Alimento
 */
@Service
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;

    private final InventarioRepository inventarioRepository;

    public AlimentoService(AlimentoRepository alimentoRepository, InventarioRepository inventarioUsuarioRepository) {
        this.alimentoRepository = alimentoRepository;
        this.inventarioRepository = inventarioUsuarioRepository;
    }

    /**
     * Obtiene todos los alimentos
     *
     * @param pageable
     * @return
     */
    public Page<AlimentoDTO> getAllAlimentos(Pageable pageable) {
        return alimentoRepository.findAll(pageable).map(this::convertToDTO);
    }

    /**
     * Obtiene un alimento por su id
     *
     * @param id
     * @return
     */
    public Optional<AlimentoDTO> getAlimentoById(Long id) {
        return alimentoRepository.findById(id).map(this::convertToDTO);
    }

    /**
     * Filtra los alimentos por nombre, abierto, perecedero y caducidad
     *
     * @param nombre
     * @param abierto
     * @param perecedero
     * @param caducidad
     * @param pageable
     * @return
     */
    public Page<AlimentoDTO> filtrarAlimentos(
            String nombre,
            Boolean abierto,
            Boolean perecedero,
            LocalDate caducidad,
            Pageable pageable
    ) {

        Page<Alimento> alimentos;

        if (nombre != null && caducidad != null) {
            alimentos = alimentoRepository.findByNombreContainingIgnoreCaseAndFechaCaducidadAndAbiertoAndPerecederoAndFechaCaducidad(nombre, caducidad, abierto, perecedero, caducidad, pageable);
        } else if (nombre != null) {
            alimentos = alimentoRepository.findByNombreContainingIgnoreCaseAndAbiertoAndPerecedero(nombre, abierto, perecedero, pageable);
        } else {
            alimentos = alimentoRepository.findAllByAbiertoAndPerecedero(abierto, perecedero, pageable);
        }

        return alimentos.map(this::convertToDTO);
    }

    /**
     * Crea un alimento
     *
     * @param createDTO
     * @return
     */
    public AlimentoDTO createAlimento(AlimentoCreateDTO createDTO) {
        Alimento alimento = new Alimento();

        String nombre = createDTO.getNombre();
        LocalDate fechaCaducidad = createDTO.getFechaCaducidad();
        boolean abierto = createDTO.isAbierto();
        boolean perecedero = createDTO.isPerecedero();

        alimento.setNombre(nombre);
        alimento.setFechaCaducidad(fechaCaducidad);
        alimento.setAbierto(abierto);
        alimento.setPerecedero(perecedero);
        alimento.setInventario(inventarioRepository.findById(createDTO.getInventarioId()).orElseThrow());
        alimentoRepository.save(alimento);
        return convertToDTO(alimento);
    }

    /**
     * Actualiza un alimento
     *
     * @param id
     * @param updateDTO
     * @return
     */
    public Optional<AlimentoDTO> updateAlimento(Long id, AlimentoUpdateDTO updateDTO) {
        return alimentoRepository.findById(id).map(alimento -> {
            alimento.setNombre(updateDTO.getNombre());
            alimento.setFechaCaducidad(updateDTO.getFechaCaducidad());
            alimento.setAbierto(updateDTO.isAbierto());
            alimento.setPerecedero(updateDTO.isPerecedero());
            alimento.setInventario(inventarioRepository.findById(updateDTO.getInventarioId()).orElseThrow());
            alimentoRepository.save(alimento);
            return convertToDTO(alimento);
        });
    }

    /**
     * Elimina un alimento
     *
     * @param id
     */
    public void deleteAlimento(Long id) {
        if (!alimentoRepository.existsById(id)) {
            throw new RuntimeException("Alimento no encontrado con id " + id);
        }
        alimentoRepository.deleteById(id);
    }

    /**
     * Convierte un Alimento a AlimentoDTO
     *
     * @param alimento
     * @return
     */
    private AlimentoDTO convertToDTO(Alimento alimento) {
        AlimentoDTO alimentoDTO = new AlimentoDTO();
        alimentoDTO.setId(alimento.getId());
        alimentoDTO.setNombre(alimento.getNombre());
        alimentoDTO.setFechaCaducidad(alimento.getFechaCaducidad());
        alimentoDTO.setAbierto(alimento.isAbierto());
        alimentoDTO.setPerecedero(alimento.isPerecedero());
        alimentoDTO.setInventarioId(alimento.getInventario().getId());
        alimentoDTO.setNumeroUsos(alimento.getNumeroUsos());
        return alimentoDTO;
    }

    // Logica de negocio

    /**
     * Mueve un alimento a la nevera
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean moverAlimentoToCongelador(Long id) {
        return alimentoRepository.findById(id).map(alimento -> {
            alimento.setInventario(inventarioRepository.findById(1L).orElseThrow());
            alimentoRepository.save(alimento);
            return true;
        }).orElse(false);
    }

    /**
     * Mueve un alimento al congelador
     *
     * @return
     */
    @Transactional
    public void rotarProductos() {
        List<Alimento> alimentos = alimentoRepository.findAll();
        alimentos.forEach(alimento -> {
            alimento.setAbierto(false);
            alimentoRepository.save(alimento);
            if (alimento.isPerecedero() && alimento.getFechaCaducidad().isBefore(LocalDate.now())) {
                alimentoRepository.delete(alimento);
            }
        });
    }

    /**
     * Obtiene los alimentos proximos a caducar
     *
     * @param pageable
     * @return
     */
    public Page<AlimentoDTO> getAlimentosProximosACaducar(Pageable pageable) {
        LocalDate fecha = LocalDate.now().plusDays(7);
        return alimentoRepository.findAlimentoByFechaCaducidadBefore(fecha, pageable).map(this::convertToDTO);
    }

    /**
     * Obtiene los alimentos caducados
     *
     * @param pageable
     * @return
     */
    public Page<AlimentoDTO> getAlimentosCaducados(Pageable pageable) {
        return alimentoRepository.findAlimentoByFechaCaducidadBefore(LocalDate.now(), pageable).map(this::convertToDTO);
    }

    /**
     * Suma un uso a un alimento
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean sumarUso(Long id) {
        return alimentoRepository.findById(id).map(alimento -> {
            alimento.setNumeroUsos(alimento.getNumeroUsos() + 1);
            alimentoRepository.save(alimento);
            return true;
        }).orElse(false);
    }

    /**
     * Obtiene los alimentos mas usados con order ascendente
     *
     * @param pageable
     * @return
     */
    public Page<AlimentoDTO> getAlimentosMasUsados(Pageable pageable) {
        return alimentoRepository.findTop10ByOrderByNumeroUsosDesc(pageable).map(this::convertToDTO);
    }

    /**
     * Obtiene los alimentos menos usados
     *
     * @param pageable
     * @return
     */
    public Page<AlimentoDTO> getAlimentosMenosUsados(Pageable pageable) {
        return alimentoRepository.findTop10ByOrderByNumeroUsosAsc(pageable).map(this::convertToDTO);
    }

    /**
     * Obtiene los alimentos proximos a caducar por inventario
     *
     * @param id
     * @param pageable
     * @return
     */
    public Page<AlimentoDTO> getAlimentosProximosACaducarByUbicacion(Long id, Pageable pageable) {
        LocalDate fecha = LocalDate.now().plusDays(7);
        return alimentoRepository.findAlimentoByFechaCaducidadBeforeAndInventarioId(fecha, id, pageable).map(this::convertToDTO);
    }
}