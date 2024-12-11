package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Devuelve un inventario por el id del usuario
    Optional<Inventario> findByUsuarioId(Long usuarioId);

    // Devuelve un inventario por el nombre de usuario
    Optional<Inventario> findByUsuarioUsername(String username);

}