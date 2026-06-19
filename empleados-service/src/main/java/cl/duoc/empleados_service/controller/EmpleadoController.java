package cl.duoc.empleados_service.controller;

import cl.duoc.empleados_service.dto.EmpleadoDTO;
import cl.duoc.empleados_service.model.Empleado;
import cl.duoc.empleados_service.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Empleado empleado = empleadoService.findById(id);
        if (empleado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Empleado empleado) {
        Empleado empleadoNuevo = empleadoService.save(empleado);
        return new ResponseEntity<>(empleadoNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Empleado empleado) {
        Empleado empleadoActualizado = empleadoService.update(id, empleado);
        if (empleadoActualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO() {
        return ResponseEntity.ok(empleadoService.findDTOList());
    }

    @GetMapping("/listado/{id}")
    public ResponseEntity<?> buscarPorIdDTO(@PathVariable Long id) {
        EmpleadoDTO empleado = empleadoService.findDTO(id);
        if (empleado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empleado);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> empleadoPorEmail( @PathVariable String email){
        return ResponseEntity.ok(empleadoService.findByEmail(email));
    }

    @GetMapping("/salario-mayor/{salario}")
    public ResponseEntity<?> empleadoPorSalarioMayor( @PathVariable Integer salario){
        return ResponseEntity.ok(empleadoService.findBySalarioMayor(salario));
    }

    @GetMapping("/salario-menor/{salario}")
    public ResponseEntity<?> empleadoPorSalarioMenor( @PathVariable Integer salario){
        return ResponseEntity.ok(empleadoService.findBySalarioMenor(salario));
    }

    @GetMapping("/local/{local}")
    public ResponseEntity<?> empleadoPorLocal( @PathVariable Long local){
        return ResponseEntity.ok(empleadoService.findByLocal(local));
    }
}
