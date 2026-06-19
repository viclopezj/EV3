package cl.duoc.proveedores_service.repository;

import cl.duoc.proveedores_service.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    boolean existsByNombreEmpresaIgnoreCase(String nombreEmpresa);
    List<Proveedor> findAllByTipoProveedor (String tipo);
    List<Proveedor> findAllByEmailEndsWithIgnoreCase(String email);
    List<Proveedor> findAllByRegionIgnoreCase(String region);
}
