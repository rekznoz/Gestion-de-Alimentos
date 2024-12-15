package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Ubicacion;

import daw2a.gestionalimentos.enums.EnumUbicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de la entidad Ubicacion
 */
@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {

    // Devuelve una ubicacion por el tipo de ubicacion
    Page<Ubicacion> findByNombreUbicacion(EnumUbicacion nombreUbicacion, Pageable pageable);

}