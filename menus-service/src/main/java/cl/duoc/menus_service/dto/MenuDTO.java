package cl.duoc.menus_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id","nombre","categoria","local"})
public class MenuDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private LocalDTO local;
}
