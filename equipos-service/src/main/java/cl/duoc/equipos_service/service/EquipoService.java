package cl.duoc.equipos_service.service;

import cl.duoc.equipos_service.clients.ProveedorFeign;
import cl.duoc.equipos_service.dto.EquipoDTO;
import cl.duoc.equipos_service.dto.ProveedorDTO;
import cl.duoc.equipos_service.mapper.EquipoMapper;
import cl.duoc.equipos_service.model.Equipo;
import cl.duoc.equipos_service.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private EquipoMapper equipoMapper;
    @Autowired
    private ProveedorFeign proveedorFeign;

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }
    public Equipo findById(Long id){
        return equipoRepository.findById(id).orElse(null);
    }
    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }
    public Equipo update(Long id, Equipo equipo) {
        Equipo equipoActualizar = equipoRepository.findById(id).orElse(null);
        if (equipoActualizar == null) return null;
        // Mapear campos aquí
        equipoActualizar.setNombre(equipo.getNombre());
        equipoActualizar.setMarca(equipo.getMarca());
        equipoActualizar.setFechaCompra(equipo.getFechaCompra());
        equipoActualizar.setUltimaMantencion(equipo.getUltimaMantencion());
        equipoActualizar.setProximaMantencion(equipo.getProximaMantencion());
        equipoActualizar.setProveedor(equipo.getProveedor());
        return equipoRepository.save(equipoActualizar);
    }
    public void delete(Long id) {
        equipoRepository.deleteById(id);
    }

    public EquipoDTO findDTO(Long id) {
        Equipo equipo = findById(id);
        EquipoDTO equipoDTO = equipoMapper.toDTO(equipo);
        ProveedorDTO proveedorDTO = proveedorFeign.buscarProveedor(equipo.getProveedor());
        equipoDTO.setProveedor(proveedorDTO);
        return equipoDTO;
    }

    public List<EquipoDTO> findDTOList(){
        List<Equipo> listado = equipoRepository.findAll();
        List<EquipoDTO> listadoDTO = new ArrayList<>();

        for (Equipo equipo : listado) {
            EquipoDTO dto = equipoMapper.toDTO(equipo);
            ProveedorDTO proveedor = proveedorFeign.buscarProveedor(equipo.getProveedor());
            dto.setProveedor(proveedor);
            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<Equipo> equiposPorMarca(String codigo){
        return equipoRepository.findAllByMarca_Codigo(codigo);
    }

    public List<Equipo> equipoPorProveedor(Long proveedor){
        return equipoRepository.findAllByProveedor(proveedor);
    }

    public List<Equipo> equipoPorAnioCompra(int anio){
        LocalDate fechaIncio = LocalDate.of(anio, 1, 1);
        LocalDate fechaFin = LocalDate.of(anio, 12, 31);
        return equipoRepository.findAllByFechaCompraBetween(fechaIncio, fechaFin);
    }
}
