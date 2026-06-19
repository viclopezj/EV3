package cl.duoc.menus_service.controller;

import cl.duoc.menus_service.model.Categoria;
import cl.duoc.menus_service.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPorId(@PathVariable String codigo){
        Categoria categoria = categoriaService.findById(codigo);
        if (categoria == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Categoria categoria){
        Categoria categoriaNueva = categoriaService.save(categoria);
        return new ResponseEntity<>(categoriaNueva, HttpStatus.CREATED);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable String codigo, @Valid @RequestBody Categoria categoria){
        Categoria categoriaActualizado = categoriaService.update(codigo,categoria);
        if (categoriaActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoriaActualizado);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> borrar(@PathVariable String codigo){
        categoriaService.delete(codigo);
        return ResponseEntity.noContent().build();
    }
}
