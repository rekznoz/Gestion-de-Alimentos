package daw2a.gestionalimentos.dto.usuario;

import daw2a.gestionalimentos.enums.Rol;
import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String username;
    private String password;
    private Rol rol;

}
