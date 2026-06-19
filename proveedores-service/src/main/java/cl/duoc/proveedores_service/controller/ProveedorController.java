package cl.duoc.proveedores_service.controller;

import cl.duoc.proveedores_service.dto.ProveedorDTO;
import cl.duoc.proveedores_service.model.Proveedor;
import cl.duoc.proveedores_service.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(proveedorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        if (proveedor == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(proveedor);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Proveedor proveedor) {
        Proveedor proveedorNuevo = proveedorService.save(proveedor);
        return new ResponseEntity<>(proveedorNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Proveedor proveedor) {
        Proveedor proveedorActualizado = proveedorService.update(id, proveedor);
        if (proveedorActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(proveedorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO(){
        return ResponseEntity.ok(proveedorService.findDTOList());
    }
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id){
        ProveedorDTO proveedor = proveedorService.findDTO(id);
        if (proveedor == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/tipo-proveedor/{tipo}")
    public ResponseEntity<?> proveedorPorTipo( @PathVariable String tipo){
        return ResponseEntity.ok(proveedorService.findByTipoProveedor(tipo));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> proveedorPorEmail( @PathVariable String email){
        return ResponseEntity.ok(proveedorService.findByEmailEnds(email));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<?> proveedorPorRegion( @PathVariable String region){
        return ResponseEntity.ok(proveedorService.findByRegion(region));
    }
}
