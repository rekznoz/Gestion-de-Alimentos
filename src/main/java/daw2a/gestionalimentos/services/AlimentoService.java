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

}