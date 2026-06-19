package cl.duoc.inventarios_service.mapper;

import cl.duoc.inventarios_service.dto.InventarioDTO;
import cl.duoc.inventarios_service.model.Inventario;
import org.springframework.stereotype.Component;

@Component
public class InventarioMapper {

    public InventarioDTO toDTO(Inventario inventario){
        if (inventario == null) return null;
        InventarioDTO dto = new InventarioDTO();

        dto.setId(inventario.getId());
        dto.setNombre(inventario.getNombreInsumo());
        dto.setCantidadActual(inventario.getCantidadActual());

        return dto;
    }
}
