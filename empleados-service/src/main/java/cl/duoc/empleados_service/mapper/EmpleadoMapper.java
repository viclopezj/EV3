package cl.duoc.empleados_service.mapper;

import cl.duoc.empleados_service.dto.EmpleadoDTO;
import cl.duoc.empleados_service.model.Empleado;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmpleadoMapper {

    public EmpleadoDTO toDTO(Empleado empleado){
        if (empleado == null) return null;
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setNombreCompleto(empleado.getNombre() + " " + empleado.getApellido());
        return dto;
    }

}
