package daw2a.gestionalimentos.entities;

import daw2a.gestionalimentos.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad Usuario
 */
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "rol", nullable = false, length = 255)
    private Rol rol;

}