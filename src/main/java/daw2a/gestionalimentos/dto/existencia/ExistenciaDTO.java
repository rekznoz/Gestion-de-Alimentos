package daw2a.gestionalimentos.dto.existencia;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExistenciaDTO {
    private Long id;
    private int cantidadAlimento;
    private LocalDate fechaEntrada;
    private Long alimentoId;
    private Long ubicacionId;
}