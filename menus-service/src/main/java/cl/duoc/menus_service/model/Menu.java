package cl.duoc.menus_service.model;

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
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(max = 50, message = "Nombre debe tener un maximo de 50 caracteres.")
    private String nombre;
    @NotNull(message = "Precio no puede estar vacio.")
    @Positive(message = "Precio debe ser un valor positivo mayor a 0.")
    private Integer precio;
    @NotNull(message = "Categoria no puede estar vacio.")
    @ManyToOne
    @JoinColumn(name = "cod_categoria")
    private Categoria categoria;
    @NotNull(message = "Local no puede estar vacio.")
    @Column(name = "id_local")
    private Long local;
}
