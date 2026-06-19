package cl.duoc.proveedores_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id","nombre","tipo"})
public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String tipo;
}
