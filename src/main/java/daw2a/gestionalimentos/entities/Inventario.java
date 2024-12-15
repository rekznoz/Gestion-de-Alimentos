package daw2a.gestionalimentos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad Inventario
 */
@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacion con la tabla usuario (1 a 1)
    @OneToOne
    private Usuario usuario;

    // Relacion con la tabla alimento (1 a muchos)
    @OneToMany(mappedBy = "inventario")
    private List<Alimento> alimentos;

}