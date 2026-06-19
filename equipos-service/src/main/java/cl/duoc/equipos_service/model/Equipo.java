package cl.duoc.equipos_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.IMessage;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(message = "Nombre debe tener un maximo de 50 caracteres.")
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "cod_marca")
    @NotNull(message = "Marca no puede estar vacio.")
    private Marca marca;
    @NotNull(message = "La fecha de compra no puede estar vacio.")
    @PastOrPresent(message = "La fecha de compra no puede ser una fecha futura.")
    private LocalDate fechaCompra;
    @NotNull(message = "La ultima mantencion no puede estar vacio.")
    @PastOrPresent(message = "La ultima mantencion no puede ser una fecha futura.")
    private LocalDate ultimaMantencion;
    @NotNull(message = "La proxima mantencion no puede estar vacio")
    @Future(message = "La promixa mantencion debe ser una fecha posterior a hoy.")
    private LocalDate proximaMantencion;
    @NotNull(message = "Proveedor no puede estar vacio.")
    @Column(name = "id_proveedor")
    private Long proveedor;
}
