package daw2a.gestionalimentos.dto.inventario;

import lombok.Data;

import java.util.List;

@Data
public class InventarioDTO {
    private Long id;
    private Long usuarioId;
    private List<Long> alimentosIds;
}