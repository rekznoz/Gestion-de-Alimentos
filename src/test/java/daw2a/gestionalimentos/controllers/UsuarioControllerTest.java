package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.usuario.UsuarioCreateDTO;
import daw2a.gestionalimentos.dto.usuario.UsuarioDTO;
import daw2a.gestionalimentos.dto.usuario.UsuarioUpdateDTO;
import daw2a.gestionalimentos.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioDTO usuario;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new UsuarioDTO();
        usuario.setId(1L);
        usuario.setUsername("rafita");
    }

    @Test
    void listarUsuario() {
        PageRequest pageable = PageRequest.of(0, 10);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setUsername("rafita");

        Page<UsuarioDTO> page = new PageImpl<>(List.of(usuario));
        when(usuarioService.getAllUsuario(pageable)).thenReturn(page);

        ResponseEntity<Page<UsuarioDTO>> response = usuarioController.getAllUsuario(null, pageable);
        Page<UsuarioDTO> result = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert result != null;
        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals("rafita", result.getContent().get(0).getUsername());
    }

    @Test
    void crearUsuario() {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setUsername("rafita");
        usuarioCreateDTO.setPassword("1234");

        when(usuarioService.createUsuario(usuarioCreateDTO)).thenReturn(usuario);

        ResponseEntity<UsuarioDTO> response = usuarioController.createUsuario(usuarioCreateDTO);

        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("rafita", response.getBody().getUsername());

        verify(usuarioService, times(1)).createUsuario(usuarioCreateDTO);
    }

    @Test
    void obtenerUsuario() {
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario);

        ResponseEntity<UsuarioDTO> response = usuarioController.getUsuarioById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        assertEquals("rafita", response.getBody().getUsername());
    }

    @Test
    void actualizarUsuario() {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setUsername("rafita");
        usuarioCreateDTO.setPassword("1234");

        when(usuarioService.createUsuario(usuarioCreateDTO)).thenReturn(usuario);

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO();
        usuarioUpdateDTO.setUsername("rafita");

        when(usuarioService.updateUsuario(1L, usuarioUpdateDTO)).thenReturn(Optional.of(usuario));

        ResponseEntity<UsuarioDTO> response = usuarioController.updateUsuario(1L, usuarioUpdateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        assertEquals("rafita", response.getBody().getUsername());
        verify(usuarioService, times(1)).updateUsuario(1L, usuarioUpdateDTO);

    }

    @Test
    void eliminarUsuario() {
        when(usuarioService.deleteUsuario(1L)).thenReturn(true);

        ResponseEntity<Void> response = usuarioController.deleteUsuario(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).deleteUsuario(1L);
    }

}
