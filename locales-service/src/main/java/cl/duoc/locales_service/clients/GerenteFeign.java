package cl.duoc.locales_service.clients;

import cl.duoc.locales_service.dto.GerenteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "gerentes-service", path = "/api/v1/gerentes")
public interface GerenteFeign {

    @GetMapping("/listado")
    List<GerenteDTO> listarGerentes();
    @GetMapping("/listado/{id}")
    GerenteDTO buscarGerente(@PathVariable Long id);
}
