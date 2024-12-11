package daw2a.gestionalimentos.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Existencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremented
    private Long id;

    private int cantidad_alimento;
    private LocalDate fechaEntrada;

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    @JsonBackReference
    private Alimento alimento;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    @JsonBackReference
    private Ubicacion ubicacion;

}