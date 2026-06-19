package cl.duoc.gerentes_service.model;

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
@Table(name = "gerentes")
public class Gerente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(max = 50, message = "Nombre debe tener un maximo de 50 caracteres.")
    private String nombre;
    @NotBlank(message = "Apellido no puede estar vacio.")
    @Size(max = 50, message = "Apellido debe tener un maximo de 50 caracteres.")
    private String apellido;
    @NotBlank(message = "Email no puede estar vacio.")
    @Size(max = 100, message = "Email debe tener un maximo de 100 caracteres.")
    private String email;
    @NotBlank(message = "Fono no puede estar vacio.")
    @Size(max = 15, message = "Fono debe tener un maximo de 15 caracteres.")
    private String fono;
    @Positive(message = "Salario debe ser mayor a 0.")
    @NotNull(message = "Salario no puede estar vacio.")
    private Integer salario;
    @NotNull(message = "Bono no puede estar vacio.")
    @Positive(message = "Bono debe ser mayor a 0.")
    private Integer bono;
    @NotBlank(message = "Nivel de Mando no puede estar vacio.")
    @Size(max = 50, message = "Nivel de Mando debe tener un maximo de 50 caracteres.")
    private String nivelMando;
}
