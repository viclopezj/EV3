package cl.duoc.inventarios_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id","nombre","cantidad_actual","local","proveedor"})
public class InventarioDTO {
    private Long id;
    private String nombre;
    @JsonProperty("cantidad_actual")
    private Integer cantidadActual;
    private LocalDTO local;
    private ProveedorDTO proveedor;
}
