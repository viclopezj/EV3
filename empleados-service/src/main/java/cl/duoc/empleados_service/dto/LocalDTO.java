package cl.duoc.empleados_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id","nombre"})
public class LocalDTO {
    private Long id;
    private String nombre;
}
