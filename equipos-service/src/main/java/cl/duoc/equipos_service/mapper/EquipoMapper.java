package cl.duoc.equipos_service.mapper;

import cl.duoc.equipos_service.dto.EquipoDTO;
import cl.duoc.equipos_service.model.Equipo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EquipoMapper {


    public EquipoDTO toDTO(Equipo equipo){
        if (equipo == null) return null;
        EquipoDTO dto = new EquipoDTO();
        dto.setId(equipo.getId());
        dto.setNombre(equipo.getNombre());
        dto.setMarca(equipo.getMarca().getCodigo());
        return dto;
    }

}
