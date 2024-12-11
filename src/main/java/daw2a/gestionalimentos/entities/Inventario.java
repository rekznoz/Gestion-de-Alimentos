package daw2a.gestionalimentos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString
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
    @OneToMany(mappedBy = "inventarioUsuario")
    private List<Alimento> alimentos;

}