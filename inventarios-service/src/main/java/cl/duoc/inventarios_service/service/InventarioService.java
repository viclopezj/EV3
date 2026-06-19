package cl.duoc.inventarios_service.service;

import cl.duoc.inventarios_service.clients.LocalFeign;
import cl.duoc.inventarios_service.clients.ProveedorFeign;
import cl.duoc.inventarios_service.dto.InventarioDTO;
import cl.duoc.inventarios_service.dto.LocalDTO;
import cl.duoc.inventarios_service.dto.ProveedorDTO;
import cl.duoc.inventarios_service.exception.InsumoDuplicadoEnLocalException;
import cl.duoc.inventarios_service.mapper.InventarioMapper;
import cl.duoc.inventarios_service.model.Inventario;
import cl.duoc.inventarios_service.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private InventarioMapper inventarioMapper;
    @Autowired
    private LocalFeign localFeign;
    @Autowired
    private ProveedorFeign proveedorFeign;

    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }
    public Inventario findById(Long id){
        return inventarioRepository.findById(id).orElse(null);
    }
    public Inventario save(Inventario inventario) {
        // --- VALIDACIONES
        if (inventarioRepository.existsByNombreInsumoIgnoreCaseAndLocal(inventario.getNombreInsumo(), inventario.getLocal())) {
            throw new InsumoDuplicadoEnLocalException("El insumo '" + inventario.getNombreInsumo() + "' ya se encuentra registrado en el local ID: " + inventario.getLocal());
        }
        return inventarioRepository.save(inventario);
    }
    public Inventario update(Long id, Inventario inventario) {
        Inventario inventarioActualizar = inventarioRepository.findById(id).orElse(null);
        if (inventarioActualizar == null) return null;
        // --- VALIDACIONES
        if (!inventarioActualizar.getNombreInsumo().equalsIgnoreCase(inventario.getNombreInsumo()) ||
                !inventarioActualizar.getLocal().equals(inventario.getLocal())) {

            if (inventarioRepository.existsByNombreInsumoIgnoreCaseAndLocal(inventario.getNombreInsumo(), inventario.getLocal())) {
                throw new InsumoDuplicadoEnLocalException("No se puede actualizar. El insumo '" + inventario.getNombreInsumo() + "' ya existe en el local ID: " + inventario.getLocal());
            }
        }
        // Mapear campos aquí
        inventarioActualizar.setNombreInsumo(inventario.getNombreInsumo());
        inventarioActualizar.setCantidadActual(inventario.getCantidadActual());
        inventarioActualizar.setLocal(inventario.getLocal());
        inventarioActualizar.setProveedor(inventario.getProveedor());
        return inventarioRepository.save(inventarioActualizar);
    }
    public void delete(Long id) {
        inventarioRepository.deleteById(id);
    }

    public InventarioDTO findDTO(Long id) {
        Inventario inventario = findById(id);
        InventarioDTO inventarioDTO = inventarioMapper.toDTO(inventario);
        LocalDTO localDTO = localFeign.buscarLocal(inventario.getLocal());
        ProveedorDTO proveedorDTO = proveedorFeign.buscarProveedor(inventario.getProveedor());
        inventarioDTO.setLocal(localDTO);
        inventarioDTO.setProveedor(proveedorDTO);
        return inventarioDTO;
    }

    public List<InventarioDTO> findDTOList(){
        List<Inventario> listado = inventarioRepository.findAll();
        List<InventarioDTO> listadoDTO = new ArrayList<>();

        for (Inventario inventario : listado) {
            InventarioDTO dto = inventarioMapper.toDTO(inventario);
            LocalDTO local = localFeign.buscarLocal(inventario.getLocal());
            ProveedorDTO proveedor = proveedorFeign.buscarProveedor(inventario.getProveedor());
            dto.setLocal(local);
            dto.setProveedor(proveedor);

            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<Inventario> findByLocal(Long local){
        return inventarioRepository.findAllByLocal(local);
    }

    public List<Inventario> findByProveedor(Long proveedor){
        return inventarioRepository.findAllByProveedor(proveedor);
    }

    public List<Inventario> findByCantidadMayor(Integer cantidad){
        return inventarioRepository.findAllByCantidadActualGreaterThanEqual(cantidad);
    }

    public List<Inventario> findByCantidadMenor(Integer cantidad){
        return inventarioRepository.findAllByCantidadActualLessThanEqual(cantidad);
    }

}
