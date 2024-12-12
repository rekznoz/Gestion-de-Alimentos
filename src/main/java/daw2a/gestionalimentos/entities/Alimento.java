package daw2a.gestionalimentos.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private LocalDate fechaCaducidad;
    private boolean abierto;
    private boolean perecedero;

    // Relacion con la tabla existencia (1 a muchos)
    @OneToMany(mappedBy = "alimento", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<Existencia> existencia;

    // Relacion con la tabla inventario (1 a muchos)
    @ManyToOne
    private Inventario inventario;

}