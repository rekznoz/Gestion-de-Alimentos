package daw2a.gestionalimentos.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "alimentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "fecha_caducidad", nullable = false)
    private LocalDate fechaCaducidad;

    @Column(name = "abierto", nullable = false)
    private boolean abierto;

    @Column(name = "perecedero", nullable = false)
    private boolean perecedero;

    @Column(name = "numero_usos", nullable = false)
    private int numeroUsos;

    // Relacion con la tabla existencia (1 a muchos)
    @OneToMany(mappedBy = "alimento", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<Existencia> existencia;

    // Relacion con la tabla inventario (muchos a uno)
    @ManyToOne
    private Inventario inventario;

}
