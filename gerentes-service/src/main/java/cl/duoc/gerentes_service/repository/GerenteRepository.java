package cl.duoc.gerentes_service.repository;

import cl.duoc.gerentes_service.model.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {

    List<Gerente> findAllByEmailEndsWithIgnoreCase (String email);
    List<Gerente> findAllBySalarioGreaterThanEqual(Integer salario);
    List<Gerente> findAllBySalarioLessThanEqual(Integer salario);
    List<Gerente> findAllByBonoGreaterThanEqual(Integer bono);
    List<Gerente> findAllByBonoLessThanEqual(Integer bono);
}
