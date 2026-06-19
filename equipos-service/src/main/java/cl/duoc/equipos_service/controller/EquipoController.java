package cl.duoc.equipos_service.controller;

import cl.duoc.equipos_service.dto.EquipoDTO;
import cl.duoc.equipos_service.model.Equipo;
import cl.duoc.equipos_service.service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/equipos")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(equipoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Equipo equipo = equipoService.findById(id);
        if (equipo == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(equipo);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Equipo equipo) {
        Equipo equipoNuevo = equipoService.save(equipo);
        return new ResponseEntity<>(equipoNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Equipo equipo) {
        Equipo equipoActualizado = equipoService.update(id, equipo);
        if (equipoActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(equipoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO() {
        return ResponseEntity.ok(equipoService.findDTOList());
    }

    @GetMapping("/listado/{id}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id) {
        EquipoDTO equipoDTO = equipoService.findDTO(id);
        if (equipoDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(equipoDTO);
    }

    @GetMapping("/marcas/{codigo}")
    public ResponseEntity<?> equiposPorMarca( @PathVariable String codigo){
        return ResponseEntity.ok(equipoService.equiposPorMarca(codigo));
    }

    @GetMapping("/proveedor/{proveedor}")
    public ResponseEntity<?> equiposPorProveedor( @PathVariable Long proveedor){
        return ResponseEntity.ok(equipoService.equipoPorProveedor(proveedor));
    }

    @GetMapping("/año-de-compra/{anio}")
    public ResponseEntity<?> equiposPorAñoCompra( @PathVariable int anio){
        return ResponseEntity.ok(equipoService.equipoPorAnioCompra(anio));
    }
}
