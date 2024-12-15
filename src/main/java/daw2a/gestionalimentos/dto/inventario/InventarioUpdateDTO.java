package daw2a.gestionalimentos.dto.inventario;

import lombok.Data;

import java.util.List;

@Data
public class InventarioUpdateDTO {
    private Long id;
    private Long usuarioId;
    private List<Long> alimentosIds;
}
