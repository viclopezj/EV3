package cl.duoc.ventas_service.repository;

import cl.duoc.ventas_service.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    boolean existsByFechaReporteAndLocal(LocalDate fechaReporte, Long local);
    List<Venta> findAllByLocal(Long local);
    List<Venta> findAllByVentaMaximasLessThanEqual(Integer ventaMaximasIsLessThan);
    List<Venta> findAllByVentaMinimasGreaterThanEqual(Integer ventaMinimasIsGreaterThan);
    List<Venta> findAllByVentaPromedioGreaterThanEqual(Integer ventaPromedioIsGreaterThan);
}
