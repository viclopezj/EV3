package cl.duoc.menus_service.service;

import cl.duoc.menus_service.clients.LocalFeign;
import cl.duoc.menus_service.dto.LocalDTO;
import cl.duoc.menus_service.dto.MenuDTO;
import cl.duoc.menus_service.exception.MenuDuplicadoEnLocalException;
import cl.duoc.menus_service.mapper.MenuMapper;
import cl.duoc.menus_service.model.Menu;
import cl.duoc.menus_service.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private LocalFeign localFeign;

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
    public Menu findById(Long id){
        return menuRepository.findById(id).orElse(null);
    }
    public Menu save(Menu menu) {
        // VALIDACIONES
        if (menuRepository.existsByNombreIgnoreCaseAndLocal(menu.getNombre(), menu.getLocal())) {
            throw new MenuDuplicadoEnLocalException("El menú '" + menu.getNombre() + "' ya se encuentra registrado en el local ID: " + menu.getLocal());
        }
        return menuRepository.save(menu);
    }
    public Menu update(Long id, Menu menu) {
        Menu menuActualizar = menuRepository.findById(id).orElse(null);
        if (menuActualizar == null) return null;
        // VALIDACIONES
        if (!menuActualizar.getNombre().equalsIgnoreCase(menu.getNombre()) ||
                !menuActualizar.getLocal().equals(menu.getLocal())) {

            // Si hubo cambios, comprobamos que la nueva combinación no genere un duplicado en la BD
            if (menuRepository.existsByNombreIgnoreCaseAndLocal(menu.getNombre(), menu.getLocal())) {
                throw new MenuDuplicadoEnLocalException("No se puede actualizar. El menú '" + menu.getNombre() + "' ya existe en el local ID: " + menu.getLocal());
            }
        }
        // Mapear campos aquí
        menuActualizar.setNombre(menu.getNombre());
        menuActualizar.setPrecio(menu.getPrecio());
        menuActualizar.setCategoria(menu.getCategoria());
        menuActualizar.setLocal(menu.getLocal());
        return menuRepository.save(menuActualizar);
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    public MenuDTO findDTO(Long id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        MenuDTO menuDTO = menuMapper.toDTO(menu);
        LocalDTO localDTO = localFeign.buscarLocal(menu.getLocal());
        menuDTO.setLocal(localDTO);
        return menuDTO;
    }

    public List<MenuDTO> findDTOList(){
        List<Menu> listado = menuRepository.findAll();
        List<MenuDTO> listadoDTO = new ArrayList<>();

        for (Menu menu : listado) {
            MenuDTO dto = menuMapper.toDTO(menu);
            LocalDTO local = localFeign.buscarLocal(menu.getLocal());
            dto.setLocal(local);
            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<Menu> menusPorCategoria(String codigo){
        return menuRepository.findAllByCategoria_Codigo(codigo);
    }

    public List<Menu> menusPorLocal(Long local){
        return menuRepository.findAllByLocal(local);
    }

    public List<Menu> menuPorPrecioMayor(Integer precio){
        return menuRepository.findAllByPrecioGreaterThanEqual(precio);
    }

    public List<Menu> menuPorPrecioMenor(Integer precio){
        return menuRepository.findAllByPrecioLessThanEqual(precio);
    }
}
