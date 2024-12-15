package daw2a.gestionalimentos.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import daw2a.gestionalimentos.enums.EnumUbicacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad Ubicacion
 */
@Entity
@Table(name = "ubicacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "capacidad", nullable = false)
    private int capacidad;

    // Relacion con la tabla existencia (1 a muchos)
    @OneToMany(mappedBy = "ubicacion", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<Existencia> existencias;

    // Relacion con la tabla usuario (1 a muchos)
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_ubicacion")
    private EnumUbicacion nombreUbicacion;

}