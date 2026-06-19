package cl.duoc.ventas_service.dto;

import lombok.Data;

@Data
public class VentaDTO {
    private Long id;
    private String fechaReporte;
    private LocalDTO local;
    private Integer ventasPromedio;
}
