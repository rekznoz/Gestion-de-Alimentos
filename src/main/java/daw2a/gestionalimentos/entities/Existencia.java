package daw2a.gestionalimentos.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "existencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Existencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad_alimento", nullable = false)
    private int cantidad_alimento;

    @Column(name = "fecha_entrada", nullable = false)
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