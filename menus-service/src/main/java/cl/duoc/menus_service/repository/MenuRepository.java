package cl.duoc.menus_service.repository;

import cl.duoc.menus_service.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsByNombreIgnoreCaseAndLocal(String nombre, Long local);
    List<Menu> findAllByCategoria_Codigo(String categoriaCodigo);
    List<Menu> findAllByLocal(Long local);
    List<Menu> findAllByPrecioGreaterThanEqual(Integer precioIsGreaterThan);
    List<Menu> findAllByPrecioLessThanEqual(Integer precioIsLessThan);
}
