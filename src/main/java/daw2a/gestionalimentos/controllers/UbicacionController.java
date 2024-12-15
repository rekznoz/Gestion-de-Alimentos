package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.ubicacion.UbicacionDTO;
import daw2a.gestionalimentos.dto.ubicacion.UbicacionCreateDTO;
import daw2a.gestionalimentos.dto.ubicacion.UbicacionUpdateDTO;
import daw2a.gestionalimentos.enums.EnumUbicacion;
import daw2a.gestionalimentos.services.UbicacionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    /**
     * Obtiene todas las ubicaciones
     * @param pageable
     * @param nombreUbicacion
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<UbicacionDTO>> findAll(
            @RequestParam(required = false) String nombreUbicacion,
            Pageable pageable
    ) {
        try {
            if (nombreUbicacion != null) {
                return ResponseEntity.ok(ubicacionService.getUbicacionesByNombreUbicacion(nombreUbicacion, pageable));
            } else {
                return ResponseEntity.ok(ubicacionService.findAll(pageable));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtiene una ubicacion por su id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> findById(@PathVariable Long id) {
        try {
            return ubicacionService.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea una ubicacion
     * @param ubicacionCreateDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<UbicacionDTO> createUbicacion(@RequestBody @Valid UbicacionCreateDTO ubicacionCreateDTO) {
        try {
            return ResponseEntity.ok(ubicacionService.createUbicacion(ubicacionCreateDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza una ubicacion
     * @param id
     * @param ubicacionUpdateDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDTO> updateUbicacion(@PathVariable Long id, @RequestBody @Valid UbicacionUpdateDTO ubicacionUpdateDTO) {
        try {
            return ubicacionService.updateUbicacion(id, ubicacionUpdateDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una ubicacion
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUbicacion(@PathVariable Long id) {
        try {
            ubicacionService.deleteUbicacion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
