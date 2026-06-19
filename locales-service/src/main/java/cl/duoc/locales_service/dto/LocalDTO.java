package cl.duoc.locales_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id","nombre","horas_habiles","gerente"})
public class LocalDTO {
    private Long id;
    private String nombre;
    @JsonProperty("horas_habiles")
    private String horasHabiles;
    private GerenteDTO gerente;
}
