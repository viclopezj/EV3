package cl.duoc.gerentes_service.mapper;

import cl.duoc.gerentes_service.dto.GerenteDTO;
import cl.duoc.gerentes_service.model.Gerente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GerenteMapper {

    public GerenteDTO toDTO(Gerente gerente){
        if (gerente == null) return null;
        GerenteDTO dto = new GerenteDTO();

        dto.setId(gerente.getId());
        dto.setNombreCompleto(gerente.getNombre() + " " + gerente.getApellido());
        dto.setNivelMando(gerente.getNivelMando());

        return dto;
    }

    public List<GerenteDTO> toDTOlist(List<Gerente> listado){
        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
}
