package cl.duoc.menus_service.mapper;

import cl.duoc.menus_service.dto.MenuDTO;
import cl.duoc.menus_service.model.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public MenuDTO toDTO(Menu menu){
        if (menu == null) return null;
        MenuDTO dto = new MenuDTO();

        dto.setId(menu.getId());
        dto.setNombre(menu.getNombre());
        dto.setCategoria(menu.getCategoria().getNombre());

        return dto;
    }
}
