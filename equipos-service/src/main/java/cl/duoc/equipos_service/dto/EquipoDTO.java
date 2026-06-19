package cl.duoc.equipos_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonPropertyOrder({"id","nombre","marca","proveedor"})
public class EquipoDTO {
    private Long id;
    private String nombre;
    private String marca;
    private ProveedorDTO proveedor;
}
