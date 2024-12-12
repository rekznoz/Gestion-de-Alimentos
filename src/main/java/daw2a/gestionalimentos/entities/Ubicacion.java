package daw2a.gestionalimentos.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import daw2a.gestionalimentos.enums.EnumUbicacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
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