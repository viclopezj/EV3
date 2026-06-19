package cl.duoc.empleados_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import java.util.List;

@Data
@JsonPropertyOrder({"id","nombre_completo","local"})
public class EmpleadoDTO {
    private Long id;
    @JsonProperty("nombre_completo")
    private String nombreCompleto;
    private LocalDTO local;
}
