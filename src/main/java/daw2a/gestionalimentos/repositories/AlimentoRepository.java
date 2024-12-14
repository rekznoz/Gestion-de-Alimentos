package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Alimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AlimentoRepository  extends JpaRepository<Alimento,Long> {

    // Consulta para buscar alimentos por nombre
    Page<Alimento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Consulta para buscar alimentos por abierto
    Page<Alimento> findAlimentoByAbierto(boolean abierto, Pageable pageable);

    // Consulta para buscar alimentos por perecedero
    Page<Alimento> findAlimentoByPerecedero(boolean perecedero, Pageable pageable);

    // Consulta para buscar alimentos por fecha de caducidad
    Page<Alimento> findAllByOrderByFechaCaducidad(Pageable pageable);

    // Consulta para buscar alimentos por fecha de caducidad
    Page<Alimento> findAlimentoByFechaCaducidadBefore(LocalDate fecha, Pageable pageable);

    // Consulta alimentos por numero de usos
    Page<Alimento> findAlimentoByNumeroUsosGreaterThanEqual(int numeroUsos, Pageable pageable);

    // Consulta alimentos por numero de usos
    Page<Alimento> findAlimentoByNumeroUsosLessThanEqual(int numeroUsos, Pageable pageable);

    // Consulta alimentos por numero de usos
    Page<Alimento> findAlimentoByNumeroUsosBetween(int numeroUsos1, int numeroUsos2, Pageable pageable);

    Page<Alimento> findAlimentoByFechaCaducidad(LocalDate fecha, Pageable pageable);
}