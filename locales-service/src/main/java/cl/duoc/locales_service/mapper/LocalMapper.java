package cl.duoc.locales_service.mapper;

import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.model.Local;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class LocalMapper {

    public LocalDTO toDTO(Local local){
        if (local == null) return null;
        LocalDTO dto = new LocalDTO();
        dto.setId(local.getId());
        dto.setNombre(local.getNombre());
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
        String horarioTexto = local.getHoraApertura().format(formato) + " - " + local.getHoraCierre().format(formato);
        dto.setHorasHabiles(horarioTexto);

        return dto;
    }
}
