package cl.duoc.inventarios_service.controller;

import cl.duoc.inventarios_service.dto.InventarioDTO;
import cl.duoc.inventarios_service.model.Inventario;
import cl.duoc.inventarios_service.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/inventarios")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Inventario inventario = inventarioService.findById(id);
        if (inventario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inventario);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Inventario inventario) {
        Inventario inventarioNuevo = inventarioService.save(inventario);
        return new ResponseEntity<>(inventarioNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Inventario inventario) {
        Inventario inventarioActualizado = inventarioService.update(id, inventario);
        if (inventarioActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inventarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO(){
        return ResponseEntity.ok(inventarioService.findDTOList());
    }

    @GetMapping("/listado/{id}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id){
        InventarioDTO inventario = inventarioService.findDTO(id);
        if (inventario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inventario);
    }

    @GetMapping("/local/{local}")
    public ResponseEntity<?> inventarioPorLocal( @PathVariable Long local){
        return ResponseEntity.ok(inventarioService.findByLocal(local));
    }

    @GetMapping("/proveedor/{proveedor}")
    public ResponseEntity<?> inventarioPorProveedor( @PathVariable Long proveedor){
        return ResponseEntity.ok(inventarioService.findByProveedor(proveedor));
    }

    @GetMapping("/cantidad-mayor/{cantidad}")
    public ResponseEntity<?> inventarioPorCantidadMayorA( @PathVariable Integer cantidad){
        return ResponseEntity.ok(inventarioService.findByCantidadMayor(cantidad));
    }

    @GetMapping("/cantidad-menor/{cantidad}")
    public ResponseEntity<?> inventarioPorCantidadMenorA( @PathVariable Integer cantidad){
        return ResponseEntity.ok(inventarioService.findByCantidadMenor(cantidad));
    }
}
