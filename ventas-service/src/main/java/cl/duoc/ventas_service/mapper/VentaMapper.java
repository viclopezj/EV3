package cl.duoc.ventas_service.mapper;

import cl.duoc.ventas_service.dto.VentaDTO;
import cl.duoc.ventas_service.model.Venta;
import org.springframework.stereotype.Component;

@Component
public class VentaMapper {

    public VentaDTO toDTO(Venta venta){
        if (venta == null) return null;
        VentaDTO dto = new VentaDTO();

        dto.setId(venta.getId());
        dto.setFechaReporte(venta.getFechaReporte().toString());
        dto.setVentasPromedio(venta.getVentaPromedio());
        return dto;
    }
}
