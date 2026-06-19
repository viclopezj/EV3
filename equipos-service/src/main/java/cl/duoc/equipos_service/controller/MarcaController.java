package cl.duoc.equipos_service.controller;

import cl.duoc.equipos_service.model.Marca;
import cl.duoc.equipos_service.service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(marcaService.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPorId(@PathVariable String codigo){
        Marca marca = marcaService.findById(codigo);
        if (marca == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(marca);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Marca marca){
        Marca marcaNueva = marcaService.save(marca);
        return new ResponseEntity<>(marcaNueva, HttpStatus.CREATED);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable String codigo, @Valid @RequestBody Marca marca){
        Marca marcaActualizada = marcaService.update(codigo,marca);
        if (marcaActualizada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(marcaActualizada);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> borrar(@PathVariable String codigo){
        marcaService.delete(codigo);
        return ResponseEntity.noContent().build();
    }
}
