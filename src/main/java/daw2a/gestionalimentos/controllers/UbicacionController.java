package daw2a.gestionalimentos.controllers;

import daw2a.gestionalimentos.dto.ubicacion.UbicacionDTO;
import daw2a.gestionalimentos.dto.ubicacion.UbicacionCreateDTO;
import daw2a.gestionalimentos.dto.ubicacion.UbicacionUpdateDTO;
import daw2a.gestionalimentos.enums.EnumUbicacion;
import daw2a.gestionalimentos.services.UbicacionService;
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

    @GetMapping
    public ResponseEntity<Page<UbicacionDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(ubicacionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> findById(@PathVariable Long id) {
        return ubicacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombreUbicacion}")
    public ResponseEntity<Page<UbicacionDTO>> findByNombreUbicacion(@PathVariable EnumUbicacion nombreUbicacion, Pageable pageable) {
        return ResponseEntity.ok(ubicacionService.getUbicacionesByNombreUbicacion(nombreUbicacion, pageable));
    }

    @PostMapping
    public ResponseEntity<UbicacionDTO> createUbicacion(@RequestBody UbicacionCreateDTO ubicacionCreateDTO) {
        return ResponseEntity.ok(ubicacionService.createUbicacion(ubicacionCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDTO> updateUbicacion(@PathVariable Long id, @RequestBody UbicacionUpdateDTO ubicacionUpdateDTO) {
        return ubicacionService.updateUbicacion(id, ubicacionUpdateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUbicacion(@PathVariable Long id) {
        return ubicacionService.deleteUbicacion(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
