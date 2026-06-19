package cl.duoc.locales_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locales")
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(max = 50, message = "Nombre debe tener un maximo de 50 caracteres")
    private String nombre;
    @NotBlank(message = "Comuna no puede estar vacio.")
    @Size(max = 50, message = "Comuna debe tener un maximo de 50 caracteres.")
    private String comuna;
    @NotBlank(message = "Direccion no puede estar vacio.")
    @Size(max = 100, message = "Direccion debe tener un maximo de 100 caracteres.")
    private String direccion;
    @NotNull(message = "Hora de Apertura no puede estar vaio.")
    private LocalTime horaApertura;
    @NotNull(message = "Hora de Cierre no puede estar vacio")
    private LocalTime horaCierre;
    @NotNull(message = "Local no puede estar vacio.")
    @Column(name = "id_gerente")
    private Long gerente;
}
