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

@Service
@Transactional
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;

    private final InventarioRepository inventarioRepository;

    public AlimentoService(AlimentoRepository alimentoRepository, InventarioRepository inventarioUsuarioRepository) {
        this.alimentoRepository = alimentoRepository;
        this.inventarioRepository = inventarioUsuarioRepository;
    }

    public Page<AlimentoDTO> getAllAlimentos(Pageable pageable) {
        return alimentoRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Optional<AlimentoDTO> getAlimentoById(Long id) {
        return alimentoRepository.findById(id).map(this::convertToDTO);
    }

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

    public void deleteAlimento(Long id) {
        if (!alimentoRepository.existsById(id)) {
            throw new RuntimeException("Alimento no encontrado con id " + id);
        }
        alimentoRepository.deleteById(id);
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

    // Logica de negocio

    // Mover Alimentos al congelador
    public boolean moverAlimentoToCongelador(Long id) {
        return alimentoRepository.findById(id).map(alimento -> {
            alimento.setInventario(inventarioRepository.findById(1L).orElseThrow());
            alimentoRepository.save(alimento);
            return true;
        }).orElse(false);
    }

    // Rotacion de Productos
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

    // Alertar de productos proximos a caducar
    public Page<AlimentoDTO> getAlimentosProximosCaducar(LocalDate fecha, Pageable pageable) {
        return alimentoRepository.findAlimentoByFechaCaducidadBefore(fecha, pageable).map(this::convertToDTO);
    }

    // Alertar de productos caducados
    public Page<AlimentoDTO> getAlimentosCaducados(Pageable pageable) {
        return alimentoRepository.findAlimentoByFechaCaducidadBefore(LocalDate.now(), pageable).map(this::convertToDTO);
    }

    // Sumar un uso a un alimento
    public boolean sumarUso(Long id) {
        return alimentoRepository.findById(id).map(alimento -> {
            alimento.setNumeroUsos(alimento.getNumeroUsos() + 1);
            alimentoRepository.save(alimento);
            return true;
        }).orElse(false);
    }

    // Obtener Alimentos mas usados
    public Page<AlimentoDTO> getAlimentosMasUsados(int numeroUsos, Pageable pageable) {
        return alimentoRepository.findAlimentoByNumeroUsosGreaterThanEqual(numeroUsos, pageable).map(this::convertToDTO);
    }

    // Obtener Alimentos menos usados
    public Page<AlimentoDTO> getAlimentosMenosUsados(int numeroUsos, Pageable pageable) {
        return alimentoRepository.findAlimentoByNumeroUsosLessThanEqual(numeroUsos, pageable).map(this::convertToDTO);
    }

    // Obtener Alimentos por rango de usos
    public Page<AlimentoDTO> getAlimentosPorRangoUsos(int numeroUsos1, int numeroUsos2, Pageable pageable) {
        return alimentoRepository.findAlimentoByNumeroUsosBetween(numeroUsos1, numeroUsos2, pageable).map(this::convertToDTO);
    }

}