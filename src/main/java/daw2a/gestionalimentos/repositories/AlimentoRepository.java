package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Alimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Repositorio de la entidad Alimento
 */
@Repository
public interface AlimentoRepository  extends JpaRepository<Alimento,Long> {

    // Consulta para buscar alimentos por fecha de caducidad
    Page<Alimento> findAlimentoByFechaCaducidadBefore(LocalDate fecha, Pageable pageable);

    // Consulta alimentos por numero de usos
    Page<Alimento> findAlimentoByNumeroUsosGreaterThanEqual(int numeroUsos, Pageable pageable);

    // Consulta alimentos por numero de usos
    Page<Alimento> findAlimentoByNumeroUsosLessThanEqual(int numeroUsos, Pageable pageable);

    // Consulta alimentos por numero de usos
    Page<Alimento> findAlimentoByNumeroUsosBetween(int numeroUsos1, int numeroUsos2, Pageable pageable);

    // Consulta alimentos por nombre, fecha de caducidad, abierto, perecedero y fecha de caducidad
    Page<Alimento> findByNombreContainingIgnoreCaseAndFechaCaducidadAndAbiertoAndPerecederoAndFechaCaducidad(String nombre, LocalDate fecha, boolean abierto, boolean perecedero, LocalDate caducidad, Pageable pageable);

    // Consulta alimentos por nombre, abierto y perecedero
    Page<Alimento> findByNombreContainingIgnoreCaseAndAbiertoAndPerecedero(String nombre, Boolean abierto, Boolean perecedero, Pageable pageable);

    // Consulta alimentos por abierto y perecedero
    Page<Alimento> findAllByAbiertoAndPerecedero(Boolean abierto, Boolean perecedero, Pageable pageable);

}