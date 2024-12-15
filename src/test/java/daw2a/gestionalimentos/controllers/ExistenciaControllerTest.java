package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.existencia.ExistenciaCreateDTO;
import daw2a.gestionalimentos.dto.existencia.ExistenciaDTO;
import daw2a.gestionalimentos.dto.existencia.ExistenciaUpdateDTO;
import daw2a.gestionalimentos.services.ExistenciaService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExistenciaControllerTest {

    @Mock
    private ExistenciaService existenciaService;

    @InjectMocks
    private ExistenciaController existenciaController;

    private ExistenciaDTO existencia;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        existencia = new ExistenciaDTO();
        existencia.setId(1L);
        existencia.setCantidadAlimento(10);
        existencia.setFechaEntrada(LocalDate.now());
        existencia.setAlimentoId(1L);
        existencia.setUbicacionId(1L);
    }

    @Test
    void listarExistencia() {
        PageRequest pageable = PageRequest.of(0, 10);
        ExistenciaDTO existenciaDTO = new ExistenciaDTO();
        existenciaDTO.setId(1L);
        existenciaDTO.setCantidadAlimento(10);
        existenciaDTO.setFechaEntrada(LocalDate.now());
        existenciaDTO.setAlimentoId(1L);
        existenciaDTO.setUbicacionId(1L);

        Page<ExistenciaDTO> page = new PageImpl<>(List.of(existencia));
        when(existenciaService.getAllExistencias(pageable)).thenReturn(page);

        ResponseEntity<Page<ExistenciaDTO>> response = existenciaController.getAllExistencias(null, null, pageable);
        Page<ExistenciaDTO> result = response.getBody();

        assert result != null;
        assertEquals(1, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        verify(existenciaService).getAllExistencias(pageable);
    }

    @Test
    void obtenerExistencia() {
        when(existenciaService.getExistenciasById(1L)).thenReturn(java.util.Optional.of(existencia));

        ResponseEntity<ExistenciaDTO> response = existenciaController.getExistenciasById(1L);

        assertEquals(1L, response.getBody().getId());
        verify(existenciaService).getExistenciasById(1L);
    }

    @Test
    void crearExistencia() {
        ExistenciaCreateDTO existenciaCreateDTO = new ExistenciaCreateDTO();
        existenciaCreateDTO.setCantidadAlimento(10);
        existenciaCreateDTO.setFechaEntrada(LocalDate.now());
        existenciaCreateDTO.setAlimentoId(1L);
        existenciaCreateDTO.setUbicacionId(1L);

        when(existenciaService.createExistencias(existenciaCreateDTO)).thenReturn(existencia);

        ResponseEntity<ExistenciaDTO> response = existenciaController.createExistencias(existenciaCreateDTO);

        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        verify(existenciaService).createExistencias(existenciaCreateDTO);
    }

    @Test
    void actualizarExistencia() {
        ExistenciaCreateDTO existenciaCreateDTO = new ExistenciaCreateDTO();
        existenciaCreateDTO.setCantidadAlimento(10);
        existenciaCreateDTO.setFechaEntrada(LocalDate.now());
        existenciaCreateDTO.setAlimentoId(1L);
        existenciaCreateDTO.setUbicacionId(1L);

        when(existenciaService.createExistencias(existenciaCreateDTO)).thenReturn(existencia);

        ExistenciaUpdateDTO existenciaUpdateDTO = new ExistenciaUpdateDTO();
        existenciaUpdateDTO.setCantidadAlimento(10);
        existenciaUpdateDTO.setFechaEntrada(LocalDate.now());
        existenciaUpdateDTO.setAlimentoId(1L);
        existenciaUpdateDTO.setUbicacionId(1L);

        when(existenciaService.updateExistencias(1L, existenciaUpdateDTO)).thenReturn(java.util.Optional.of(existencia));

        ResponseEntity<ExistenciaDTO> response = existenciaController.updateExistencias(1L, existenciaUpdateDTO);

        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
        verify(existenciaService).updateExistencias(1L, existenciaUpdateDTO);
    }

    @Test
    void borrarExistencia() {
        doNothing().when(existenciaService).deleteExistencias(1L);

        ResponseEntity<Void> response = existenciaController.deleteExistencias(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(existenciaService).deleteExistencias(1L);
    }

}
