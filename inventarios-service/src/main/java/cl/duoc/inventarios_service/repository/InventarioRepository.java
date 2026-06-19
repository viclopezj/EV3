package cl.duoc.inventarios_service.repository;

import cl.duoc.inventarios_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    boolean existsByNombreInsumoIgnoreCaseAndLocal(String nombreInsumo, Long local);
    List<Inventario> findAllByLocal(Long local);
    List<Inventario> findAllByProveedor(Long proveedor);
    List<Inventario> findAllByCantidadActualGreaterThanEqual(Integer cantidad);
    List<Inventario> findAllByCantidadActualLessThanEqual(Integer cantidad);
}
