package cl.duoc.empleados_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(max = 50, message = "Nombre debe tener un maximo de 50 caracteres.")
    private String nombre;
    @NotBlank(message = "Apellido no puede estar vacio.")
    @Size(max = 50, message = "Apellido debe tener un maximo de 50 caracteres.")
    private String apellido;
    @NotBlank(message = "Correo no puede estar vacio.")
    @Size(max = 100, message = "Email debe tener un maximo de 100 caracteres.")
    private String email;
    @NotBlank(message = "Telefono no puede estar vacio.")
    @Size(max = 15, message = "Fono debe tener un maximo de 15 caracteres.")
    private String fono;
    @Positive(message = "Salario debe ser mayor a 0.")
    @NotNull(message = "Salario no puede estar vacio.")
    private Integer salario;
    @NotNull(message = "Local no puede estar vacio.")
    @Column(name = "id_local")
    private Long local;
}
