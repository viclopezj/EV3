package cl.duoc.locales_service.repository;

import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByDireccionIgnoreCase(String direccion);
    List<Local> findAllByComunaIgnoreCase (String comuna);
    List<Local> findAllByGerente(Long id);
}
