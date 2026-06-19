package cl.duoc.equipos_service.clients;

import cl.duoc.equipos_service.dto.ProveedorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "proveedores-service", path = "api/v1/proveedores")
public interface ProveedorFeign {
    @GetMapping("listado")
    List<ProveedorDTO> listarProveedores();
    @GetMapping("/listado/{id}")
    ProveedorDTO buscarProveedor(@PathVariable Long id);
}
