package daw2a.gestionalimentos.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class Existencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad_alimento;
    private LocalDate fechaEntrada;

    // Relacion con la tabla alimento (1 a muchos)
    @ManyToOne
    @JoinColumn(name = "alimento_id")
    @JsonBackReference
    private Alimento alimento;

    // Relacion con la tabla ubicacion (1 a muchos)
    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    @JsonBackReference
    private Ubicacion ubicacion;

}