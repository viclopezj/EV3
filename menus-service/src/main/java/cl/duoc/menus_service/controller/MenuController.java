package cl.duoc.menus_service.controller;

import cl.duoc.menus_service.dto.MenuDTO;
import cl.duoc.menus_service.model.Menu;
import cl.duoc.menus_service.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(menuService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Menu menu = menuService.findById(id);
        if (menu == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(menu);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Menu menu) {
        Menu menuNuevo = menuService.save(menu);
        return new ResponseEntity<>(menuNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Menu menu) {
        Menu menuActualizado = menuService.update(id, menu);
        if (menuActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(menuActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO(){
        return ResponseEntity.ok(menuService.findDTOList());
    }
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id){
        MenuDTO menuDTO = menuService.findDTO(id);
        if (menuDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(menuDTO);
    }

    @GetMapping("/categorias/{codigo}")
    public ResponseEntity<?> menuPorCategoria( @PathVariable String codigo){
        return ResponseEntity.ok(menuService.menusPorCategoria(codigo));
    }

    @GetMapping("/local/{id}")
    public ResponseEntity<?> menuPorLocal( @PathVariable Long id){
        return ResponseEntity.ok(menuService.menusPorLocal(id));
    }

    @GetMapping("/precio-mayor/{precio}")
    public ResponseEntity<?> menuPorPrecioMayor( @PathVariable Integer precio){
        return ResponseEntity.ok(menuService.menuPorPrecioMayor(precio));
    }

    @GetMapping("/precio-menor/{precio}")
    public ResponseEntity<?> menuPorPrecioMenor( @PathVariable Integer precio){
        return ResponseEntity.ok(menuService.menuPorPrecioMenor(precio));
    }
}
