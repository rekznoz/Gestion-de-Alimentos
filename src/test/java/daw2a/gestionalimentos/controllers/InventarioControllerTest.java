package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.inventario.InventarioCreateDTO;
import daw2a.gestionalimentos.dto.inventario.InventarioDTO;
import daw2a.gestionalimentos.services.InventarioService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class InventarioControllerTest {

    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private InventarioController inventarioController;

    private InventarioDTO inventario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventario = new InventarioDTO();
        inventario.setId(1L);
        inventario.setUsuarioId(1L);
        inventario.setAlimentosIds(List.of(1L, 2L, 3L));
    }

    @Test
    void listarInventario() {
        PageRequest pageable = PageRequest.of(0, 10);
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setId(1L);
        inventarioDTO.setUsuarioId(1L);
        inventarioDTO.setAlimentosIds(List.of(1L, 2L, 3L));

        Page<InventarioDTO> page = new PageImpl<>(List.of(inventario));
        when(inventarioService.getAllInventarios(pageable)).thenReturn(page);

        ResponseEntity<Page<InventarioDTO>> response = inventarioController.getAllInventarios(null, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(inventarioService).getAllInventarios(pageable);
    }

    @Test
    void listarInventarioPorUsuario() {
        PageRequest pageable = PageRequest.of(0, 10);
        InventarioDTO inventarioDTO = new InventarioDTO();
        inventarioDTO.setId(1L);
        inventarioDTO.setUsuarioId(1L);
        inventarioDTO.setAlimentosIds(List.of(1L, 2L, 3L));

        Page<InventarioDTO> page = new PageImpl<>(List.of(inventario));
        when(inventarioService.getInventarioByUsuarioId(1L, pageable)).thenReturn(page);

        ResponseEntity<Page<InventarioDTO>> response = inventarioController.getAllInventarios(1L, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(inventarioService).getInventarioByUsuarioId(1L, pageable);
    }

    @Test
    void obtenerInventarioPorId() {
        when(inventarioService.getInventarioById(1L)).thenReturn(java.util.Optional.of(inventario));
        ResponseEntity<InventarioDTO> response = inventarioController.getInventarioById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inventario, response.getBody());
        verify(inventarioService).getInventarioById(1L);
    }

    @Test
    void crearInventario() {
        InventarioCreateDTO inventarioCreateDTO = new InventarioCreateDTO();
        inventarioCreateDTO.setUsuarioId(1L);

        when(inventarioService.createInventario(inventarioCreateDTO)).thenReturn(inventario);
        ResponseEntity<InventarioDTO> response = inventarioController.createInventario(inventarioCreateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inventario, response.getBody());
        verify(inventarioService).createInventario(inventarioCreateDTO);
    }

    @Test
    void borrarInventario() {
        when(inventarioService.deleteInventario(1L)).thenReturn(true);

        ResponseEntity<?> response = inventarioController.deleteInventario(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(inventarioService).deleteInventario(1L);
    }

}
