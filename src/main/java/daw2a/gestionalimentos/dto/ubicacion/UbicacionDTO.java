package daw2a.gestionalimentos.dto.ubicacion;

import daw2a.gestionalimentos.enums.EnumUbicacion;

import lombok.Data;

@Data
public class UbicacionDTO {
    private Long id;
    private String descripcion;
    private int capacidad;
    private EnumUbicacion enumUbicacion;
}