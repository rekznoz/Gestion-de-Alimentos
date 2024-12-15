package daw2a.gestionalimentos.dto.usuario;

import daw2a.gestionalimentos.enums.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private Rol rol;

}
