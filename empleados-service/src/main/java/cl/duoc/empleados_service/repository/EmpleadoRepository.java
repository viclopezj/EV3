package cl.duoc.empleados_service.repository;

import cl.duoc.empleados_service.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findAllByEmailEndsWithIgnoreCase(String email);
    List<Empleado> findAllBySalarioGreaterThanEqual(Integer salario);
    List<Empleado> findAllBySalarioLessThanEqual(Integer salario);
    List<Empleado> findAllByLocal(Long local);
}
