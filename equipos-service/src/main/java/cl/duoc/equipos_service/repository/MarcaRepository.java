package cl.duoc.equipos_service.repository;

import cl.duoc.equipos_service.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, String> {

    boolean existsByNombre(String nombre);
}
