package daw2a.gestionalimentos.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import daw2a.gestionalimentos.enums.EnumUbicacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private int capacidad;

    @OneToMany(mappedBy = "ubicacion", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<Existencia> existencias;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_ubicacion")
    private EnumUbicacion nombreUbicacion;

}