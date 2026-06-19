package cl.duoc.gerentes_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id","nombre_completo","nivel_mando"})
public class GerenteDTO {
    private Long id;
    @JsonProperty("nombre_completo")
    private String nombreCompleto;
    @JsonProperty("nivel_mando")
    private String nivelMando;
}
