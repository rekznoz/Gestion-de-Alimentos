package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Inventario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de la entidad Inventario
 */
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Devuelve un inventario por el id del usuario
    Page<Inventario> findByUsuarioId(Long usuarioId, Pageable pageable);

}