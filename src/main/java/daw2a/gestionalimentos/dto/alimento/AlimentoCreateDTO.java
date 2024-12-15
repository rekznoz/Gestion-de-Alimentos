package daw2a.gestionalimentos.dto.alimento;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlimentoCreateDTO {
    private String nombre;
    private LocalDate fechaCaducidad;
    private boolean abierto;
    private boolean perecedero;
    private Long inventarioId;
    private int numeroUsos;
}