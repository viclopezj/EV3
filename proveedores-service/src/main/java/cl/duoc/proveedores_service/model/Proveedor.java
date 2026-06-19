package cl.duoc.proveedores_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(max = 100, message = "Nombre debe tener un maximo de 100 caracteres.")
    private String nombreEmpresa;
    @NotBlank(message = "Email no puede estar vacio.")
    @Size(max = 100, message = "Email debe tener un maximo de 100 caracteres.")
    private String email;
    @NotBlank(message = "Fono no puede estar vacio.")
    @Size(max = 15, message = "Fono debe tener un maximo de 15 caracteres.")
    private String fono;
    @NotBlank(message = "La región no puede estar vacía.")
    @Size(max = 50, message = "La región debe tener un máximo de 50 caracteres.")
    private String region;
    @NotBlank(message = "Tipo de Proveedor no puede estar vacio.")
    @Size(max = 50, message = "Tipo de Proveedor debe tener un maximo de 50 caracteres.")
    private String tipoProveedor;
}
