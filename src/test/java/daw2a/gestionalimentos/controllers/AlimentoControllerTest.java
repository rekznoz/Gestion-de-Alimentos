package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.alimento.AlimentoCreateDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoDTO;
import daw2a.gestionalimentos.dto.alimento.AlimentoUpdateDTO;
import daw2a.gestionalimentos.services.AlimentoService;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AlimentoControllerTest {

    @Mock
    private AlimentoService alimentoService;

    @InjectMocks
    private AlimentoController alimentoController;

    private AlimentoDTO alimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alimento = new AlimentoDTO();
        alimento.setId(1L);
        alimento.setNombre("Leche");
        alimento.setFechaCaducidad(LocalDate.now());
        alimento.setAbierto(false);
        alimento.setPerecedero(true);
        alimento.setNumeroUsos(0);
    }

    @Test
    void listarAlimento() {
        PageRequest pageable = PageRequest.of(0, 10);
        AlimentoDTO alimentoDTO = new AlimentoDTO();
        alimentoDTO.setId(1L);
        alimentoDTO.setNombre("Leche");
        alimentoDTO.setFechaCaducidad(LocalDate.now());
        alimentoDTO.setAbierto(false);
        alimentoDTO.setPerecedero(true);
        alimentoDTO.setNumeroUsos(0);

        Page<AlimentoDTO> page = new PageImpl<>(List.of(alimento));
        when(alimentoService.getAllAlimentos(pageable)).thenReturn(page);

        ResponseEntity<Page<AlimentoDTO>> response = alimentoController.getAllAlimentos(pageable, null, null, false, true, null);
        Page<AlimentoDTO> result = response.getBody();

        assert result != null;
        assertEquals(1, result.getTotalElements());
        assertEquals(alimento.getNombre(), result.getContent().get(0).getNombre());
        verify(alimentoService, times(1)).getAllAlimentos(pageable);
    }

    @Test
    void obtenerAlimento() {
        when(alimentoService.getAlimentoById(1L)).thenReturn(Optional.ofNullable(alimento));

        ResponseEntity<AlimentoDTO> response = alimentoController.getAlimentoById(1L);

        assertEquals(ResponseEntity.ok(alimento), response);
        assertEquals(alimento, response.getBody());
        verify(alimentoService, times(1)).getAlimentoById(1L);
    }

    @Test
    void crearAlimento() {
        AlimentoCreateDTO alimentoCreateDTO = new AlimentoCreateDTO();
        alimentoCreateDTO.setNombre("Leche");
        alimentoCreateDTO.setFechaCaducidad(LocalDate.now());
        alimentoCreateDTO.setAbierto(false);
        alimentoCreateDTO.setPerecedero(true);
        alimentoCreateDTO.setInventarioId(1L);
        alimentoCreateDTO.setNumeroUsos(0);

        when(alimentoService.createAlimento(alimentoCreateDTO)).thenReturn(alimento);

        ResponseEntity<AlimentoDTO> response = alimentoController.createAlimento(alimentoCreateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(alimento, response.getBody());
        verify(alimentoService, times(1)).createAlimento(alimentoCreateDTO);
    }

    @Test
    void actualizarAlimento() {
        AlimentoCreateDTO alimentoCreateDTO = new AlimentoCreateDTO();
        alimentoCreateDTO.setNombre("Leche");
        alimentoCreateDTO.setFechaCaducidad(LocalDate.now());
        alimentoCreateDTO.setAbierto(false);
        alimentoCreateDTO.setPerecedero(true);
        alimentoCreateDTO.setInventarioId(1L);
        alimentoCreateDTO.setNumeroUsos(0);

        when(alimentoService.createAlimento(alimentoCreateDTO)).thenReturn(alimento);

        AlimentoUpdateDTO alimentoUpdateDTO = new AlimentoUpdateDTO();
        alimentoUpdateDTO.setNombre("Leche");
        alimentoUpdateDTO.setFechaCaducidad(LocalDate.now());
        alimentoUpdateDTO.setAbierto(false);
        alimentoUpdateDTO.setPerecedero(true);
        alimentoUpdateDTO.setInventarioId(1L);
        alimentoUpdateDTO.setNumeroUsos(0);

        when(alimentoService.updateAlimento(1L, alimentoUpdateDTO)).thenReturn(Optional.ofNullable(alimento));

        ResponseEntity<AlimentoDTO> response = alimentoController.updateAlimento(1L, alimentoUpdateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(alimento, response.getBody());
        verify(alimentoService, times(1)).updateAlimento(1L, alimentoUpdateDTO);

    }

    @Test
    void eliminarAlimento() {
        doNothing().when(alimentoService).deleteAlimento(1L);

        ResponseEntity<Void> response = alimentoController.deleteAlimento(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(alimentoService, times(1)).deleteAlimento(1L);
    }

}
