package cl.duoc.proveedores_service.mapper;

import cl.duoc.proveedores_service.dto.ProveedorDTO;
import cl.duoc.proveedores_service.model.Proveedor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProveedorMapper {

    public ProveedorDTO toDTO(Proveedor proveedor){
        if (proveedor == null) return null;
        ProveedorDTO dto = new ProveedorDTO();

        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombreEmpresa());
        dto.setTipo(proveedor.getTipoProveedor());

        return dto;
    }

    public List<ProveedorDTO> toDTOlist(List<Proveedor> listado){
        return listado.stream()
                .map(this::toDTO)
                .toList();
    }
}