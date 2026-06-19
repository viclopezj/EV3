package cl.duoc.menus_service.repository;

import cl.duoc.menus_service.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String> {

    boolean existsByNombreIgnoreCase(String nombre);
}
