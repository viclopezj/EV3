package cl.duoc.equipos_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id","nombre"})
public class ProveedorDTO {
    private Long id;
    private String nombre;
}
