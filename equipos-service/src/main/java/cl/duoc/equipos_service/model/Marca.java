package cl.duoc.equipos_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marcas")
public class Marca {
    @Id
    @NotBlank(message = "Codigo no puede estar vacio.")
    @Size(max = 10, message = "Codigo solo puede tener un maximo de 10 caracteres.")
    private String codigo;
    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(max = 20, message = "Nombre solo puede tener un maximo de 20 caracteres.")
    private String nombre;
}
