package cl.duoc.proveedores_service.service;

import cl.duoc.proveedores_service.dto.ProveedorDTO;
import cl.duoc.proveedores_service.exception.EmailInvalidoException;
import cl.duoc.proveedores_service.exception.NombreProveedorExistenteException;
import cl.duoc.proveedores_service.exception.TelefonoInvalidoException;
import cl.duoc.proveedores_service.exception.TipoProveedorInvalidoException;
import cl.duoc.proveedores_service.mapper.ProveedorMapper;
import cl.duoc.proveedores_service.model.Proveedor;
import cl.duoc.proveedores_service.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProveedorMapper proveedorMapper;

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }
    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }
    public Proveedor save(Proveedor proveedor) {
        // VALIDACIONES
        if (proveedorRepository.existsByNombreEmpresaIgnoreCase(proveedor.getNombreEmpresa())) {
            throw new NombreProveedorExistenteException("La empresa proveedora '" + proveedor.getNombreEmpresa() + "' ya se encuentra registrada.");
        }

        if (proveedor.getEmail() == null || !proveedor.getEmail().contains("@")) {
            throw new EmailInvalidoException("El correo electrónico del proveedor debe contener el carácter '@'.");
        }

        String emailLower = proveedor.getEmail().toLowerCase();
        if (!(emailLower.endsWith(".cl") || emailLower.endsWith(".com") || emailLower.endsWith(".net"))) {
            throw new EmailInvalidoException("El correo electrónico debe terminar con una extensión válida (.cl, .com o .net).");
        }

        if (proveedor.getFono() == null || !proveedor.getFono().startsWith("+569")) {
            throw new TelefonoInvalidoException("El número de teléfono debe comenzar con el prefijo de Chile '+569'.");
        }

        String tipoLowerSave = proveedor.getTipoProveedor().toLowerCase().trim();
        if (!(tipoLowerSave.equals("alimentos") || tipoLowerSave.equals("limpieza") || tipoLowerSave.equals("maquinaria"))) {
            throw new TipoProveedorInvalidoException("El tipo de proveedor '" + proveedor.getTipoProveedor() + "' es inválido. Los tipos permitidos son: 'Alimentos', 'Limpieza' o 'Maquinaria'.");
        }

        return proveedorRepository.save(proveedor);
    }
    public Proveedor update(Long id, Proveedor proveedor) {
        Proveedor proveedorActualizar = proveedorRepository.findById(id).orElse(null);
        if (proveedorActualizar == null) return null;
        // VALIDACIONES
        if (!proveedorActualizar.getNombreEmpresa().equalsIgnoreCase(proveedor.getNombreEmpresa())) {
            if (proveedorRepository.existsByNombreEmpresaIgnoreCase(proveedor.getNombreEmpresa())) {
                throw new NombreProveedorExistenteException("No se puede actualizar. El nombre '" + proveedor.getNombreEmpresa() + "' ya existe.");
            }
        }

        if (proveedor.getEmail() == null || !proveedor.getEmail().contains("@")) {
            throw new EmailInvalidoException("El correo electrónico del proveedor debe contener el carácter '@'.");
        }

        String emailLower = proveedor.getEmail().toLowerCase();
        if (!(emailLower.endsWith(".cl") || emailLower.endsWith(".com") || emailLower.endsWith(".net"))) {
            throw new EmailInvalidoException("El correo electrónico debe terminar con una extensión válida (.cl, .com o .net).");
        }

        if (proveedor.getFono() == null || !proveedor.getFono().startsWith("+569")) {
            throw new TelefonoInvalidoException("El número de teléfono debe comenzar con el prefijo de Chile '+569'.");
        }

        String tipoLowerUpdate = proveedor.getTipoProveedor().toLowerCase().trim();
        if (!(tipoLowerUpdate.equals("alimentos") || tipoLowerUpdate.equals("limpieza") || tipoLowerUpdate.equals("maquinaria"))) {
            throw new TipoProveedorInvalidoException("El tipo de proveedor '" + proveedor.getTipoProveedor() + "' es inválido. Los tipos permitidos son: 'Alimentos', 'Limpieza' o 'Maquinaria'.");
        }

        // Mapear campos aquí
        proveedorActualizar.setNombreEmpresa(proveedor.getNombreEmpresa());
        proveedorActualizar.setEmail(proveedor.getEmail());
        proveedorActualizar.setFono(proveedor.getFono());
        proveedorActualizar.setRegion(proveedor.getRegion());
        proveedorActualizar.setTipoProveedor(proveedor.getTipoProveedor());
        return proveedorRepository.save(proveedorActualizar);
    }
    public void delete(Long id) {
        proveedorRepository.deleteById(id);
    }

    public ProveedorDTO findDTO(Long id){
        return proveedorMapper.toDTO(findById(id));
    }

    public List<ProveedorDTO> findDTOList() {
        return proveedorMapper.toDTOlist(findAll());
    }

    public List<Proveedor> findByTipoProveedor(String tipo){
        return proveedorRepository.findAllByTipoProveedor(tipo);
    }

    public List<Proveedor> findByEmailEnds(String email){
        return proveedorRepository.findAllByEmailEndsWithIgnoreCase(email);
    }

    public List<Proveedor> findByRegion(String region){
        return proveedorRepository.findAllByRegionIgnoreCase(region);
    }
}
