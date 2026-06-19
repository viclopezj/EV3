package cl.duoc.ventas_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Local no puede estar vacio.")
    @Column(name = "id_local")
    private Long local;
    @NotNull(message = "Fecha de Reporte no puede estar vacio.")
    @PastOrPresent(message = "Fecha de Reporte no puede ser una fecha futura a la actual")
    private LocalDate fechaReporte;
    @NotNull(message = "Ventas Minimas no puede estar vacio.")
    @PositiveOrZero(message = "Ventas Minimas debe tener un valor positivo mayor a 0.")
    private Integer ventaMinimas;
    @NotNull(message = "Ventas Maximas no puede estar vacio.")
    @PositiveOrZero(message = "Ventas Maximas debe tener un valor positivo mayor a 0.")
    private Integer ventaMaximas;
    @NotNull(message = "Ventas Promedio no puede estar vacio.")
    @PositiveOrZero(message = "Ventas Promedio debe tener un valor positivo mayor a 0.")
    private Integer ventaPromedio;
}
