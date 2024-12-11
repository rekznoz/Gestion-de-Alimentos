package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Existencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExistenciaRepository extends JpaRepository<Existencia,Long> {

    // Consulta para buscar existencias por alimento
    List<Existencia> findByAlimentoId(Long alimento_id);

    // Consulta para buscar existencias por ubicacion
    Page<Existencia> findAllByOrderByFechaEntradaAsc(Pageable pageable);

    // Consulta para buscar existencias por ubicacion
    List<Existencia> findByUbicacionId(Long ubicacion_id);

    // Consulta para buscar existencias por alimento y ubicacion
    List<Existencia> findByAlimentoIdAndUbicacionId(Long alimento_id, Long ubicacion_id);

    // Consulta para buscar existencias por alimento y ubicacion
    List<Existencia> findByAlimentoIdAndUbicacionIdOrderByFechaEntradaAsc(Long alimento_id, Long ubicacion_id);

}