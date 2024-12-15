package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Inventario;
import daw2a.gestionalimentos.entities.Ubicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Devuelve un inventario por el id del usuario
    Page<Inventario> findByUsuarioId(Long usuarioId, Pageable pageable);

}