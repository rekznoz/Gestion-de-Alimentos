package daw2a.gestionalimentos.repositories;

import daw2a.gestionalimentos.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Devuelve un usuario por el nombre de usuario
    Page<Usuario> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

}