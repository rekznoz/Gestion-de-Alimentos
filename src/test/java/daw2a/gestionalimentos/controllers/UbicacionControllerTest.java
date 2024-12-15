package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.ubicacion.UbicacionCreateDTO;
import daw2a.gestionalimentos.dto.ubicacion.UbicacionDTO;
import daw2a.gestionalimentos.dto.ubicacion.UbicacionUpdateDTO;
import daw2a.gestionalimentos.enums.EnumUbicacion;
import daw2a.gestionalimentos.services.UbicacionService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UbicacionControllerTest {

    @Mock
    private UbicacionService ubicacionService;

    @InjectMocks
    private UbicacionController ubicacionController;

    private UbicacionDTO ubicacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ubicacion = new UbicacionDTO();
        ubicacion.setId(1L);
        ubicacion.setDescripcion("Nevera");
        ubicacion.setCapacidad(10);
        ubicacion.setEnumUbicacion(EnumUbicacion.NEVERA);

    }

    @Test
    void listarUbicacion() {
        PageRequest pageable = PageRequest.of(0, 10);
        UbicacionDTO ubicacionDTO = new UbicacionDTO();
        ubicacionDTO.setId(1L);
        ubicacionDTO.setDescripcion("Nevera");
        ubicacionDTO.setCapacidad(10);
        ubicacionDTO.setEnumUbicacion(EnumUbicacion.NEVERA);

        Page<UbicacionDTO> page = new PageImpl<>(List.of(ubicacion));
        when(ubicacionService.findAll(pageable)).thenReturn(page);

        ResponseEntity<Page<UbicacionDTO>> response = ubicacionController.findAll(null, pageable);
        Page<UbicacionDTO> result = response.getBody();

        assert result != null;
        assertEquals(1, result.getTotalElements());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ubicacionService, times(1)).findAll(pageable);
    }

    @Test
    void obtenerUbicacion() {
        when(ubicacionService.findById(1L)).thenReturn(java.util.Optional.of(ubicacion));

        ResponseEntity<UbicacionDTO> response = ubicacionController.findById(1L);
        UbicacionDTO result = response.getBody();

        assert result != null;
        assertEquals(ubicacion, result);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ubicacionService, times(1)).findById(1L);
    }

    @Test
    void crearUbicacion() {
        UbicacionCreateDTO ubicacionCreateDTO = new UbicacionCreateDTO();
        ubicacionCreateDTO.setDescripcion("Nevera");
        ubicacionCreateDTO.setCapacidad(10);
        ubicacionCreateDTO.setEnumUbicacion(EnumUbicacion.NEVERA);

        when(ubicacionService.createUbicacion(ubicacionCreateDTO)).thenReturn(ubicacion);

        ResponseEntity<UbicacionDTO> response = ubicacionController.createUbicacion(ubicacionCreateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ubicacion, response.getBody());
        verify(ubicacionService, times(1)).createUbicacion(ubicacionCreateDTO);
    }

    @Test
    void actualizarUbicacion() {
        UbicacionCreateDTO ubicacionCreateDTO = new UbicacionCreateDTO();
        ubicacionCreateDTO.setDescripcion("Nevera");
        ubicacionCreateDTO.setCapacidad(10);

        when(ubicacionService.createUbicacion(ubicacionCreateDTO)).thenReturn(ubicacion);

        UbicacionUpdateDTO ubicacionUpdateDTO = new UbicacionUpdateDTO();
        ubicacionUpdateDTO.setDescripcion("Nevera");
        ubicacionUpdateDTO.setCapacidad(10);
        ubicacionUpdateDTO.setEnumUbicacion(EnumUbicacion.NEVERA);

        when(ubicacionService.updateUbicacion(1L, ubicacionUpdateDTO)).thenReturn(Optional.of(ubicacion));

        ResponseEntity<UbicacionDTO> response = ubicacionController.updateUbicacion(1L, ubicacionUpdateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ubicacion, response.getBody());
        verify(ubicacionService, times(1)).updateUbicacion(1L, ubicacionUpdateDTO);
    }

    @Test
    void borrarUbicacion() {
        doNothing().when(ubicacionService).deleteUbicacion(1L);

        ResponseEntity<?> response = ubicacionController.deleteUbicacion(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ubicacionService, times(1)).deleteUbicacion(1L);
    }

}
