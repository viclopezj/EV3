package cl.duoc.equipos_service.repository;

import cl.duoc.equipos_service.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findAllByMarca_Codigo(String marca);
    List<Equipo> findAllByProveedor(Long proveedor);

    List<Equipo> findAllByFechaCompraBetween(LocalDate inicio, LocalDate fin);
}
    