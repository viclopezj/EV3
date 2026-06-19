package cl.duoc.empleados_service.clients;

import cl.duoc.empleados_service.dto.LocalDTO;
import org.springframework.cglib.core.Local;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "locales-service", path = "api/v1/locales")
public interface LocalFeign {

    @GetMapping
    List<LocalDTO> listarLocales();
    @GetMapping("/{id}")
    LocalDTO buscarLocal(@PathVariable Long id);

}
