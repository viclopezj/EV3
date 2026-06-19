package cl.duoc.inventarios_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventarios")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nombre de insumo no puede estar vacio.")
    @Size(max = 100, message = "Nombre de insumo debe tener un maximo de 100 caracteres.")
    private String nombreInsumo;
    @NotNull(message = "Cantidad Actual no puede estar vacio.")
    @PositiveOrZero(message = "Cantidad Actual no puede ser un valor negativo.")
    private Integer cantidadActual;
    @NotNull(message = "Local no puede estar vacio.")
    @Column(name = "id_local")
    private Long local;
    @NotNull(message = "Proveedor no puede estar vacio.")
    @Column(name = "id_proveedor")
    private Long proveedor;
}
