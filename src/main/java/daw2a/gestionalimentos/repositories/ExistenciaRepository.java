package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Existencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de la entidad Existencia
 */
@Repository
public interface ExistenciaRepository extends JpaRepository<Existencia,Long> {

    // Consulta para buscar existencias por alimento y ubicacion
    Page<Existencia> findByAlimentoIdAndUbicacionId(Long alimento, Long ubicacion_id, Pageable pageable);

    // Consulta para buscar existencias por alimento
    Page<Existencia> findByAlimentoId(Long alimento_id, Pageable pageable);

    // Consulta para buscar existencias por ubicacion
    Page<Existencia> findAllByOrderByFechaEntradaAsc(Pageable pageable);

    // Consulta para buscar existencias por ubicacion
    Page<Existencia> findByUbicacionId(Long ubicacion_id, Pageable pageable);

}