package cl.duoc.equipos_service.service;

import cl.duoc.equipos_service.exception.CodigoMarcaExistenteException;
import cl.duoc.equipos_service.exception.NombreRepetidoException;
import cl.duoc.equipos_service.model.Marca;
import cl.duoc.equipos_service.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> findAll(){
        return marcaRepository.findAll();
    }
    public Marca findById(String codigo){
        return marcaRepository.findById(codigo.toUpperCase()).orElse(null);
    }
    public Marca save(Marca marca){
        // VALIDACIONES
        if (marcaRepository.existsById(marca.getCodigo())) {
            throw new CodigoMarcaExistenteException("El código de marca '" + marca.getCodigo() + "' ya se encuentra registrado.");
        }

        if (marcaRepository.existsByNombre(marca.getNombre())) {
            throw new NombreRepetidoException("El nombre de marca '" + marca.getNombre() + "' ya se encuentra registrado.");
        }
        return marcaRepository.save(marca);
    }
    public Marca update(String codigo, Marca marca){
        Marca marcaActualizar = marcaRepository.findById(codigo).orElse(null);
        if (marcaActualizar == null) return null;
        // VALIDACIONES
        if (!marcaActualizar.getNombre().equalsIgnoreCase(marca.getNombre())) {
            if (marcaRepository.existsByNombre(marca.getNombre())) {
                throw new NombreRepetidoException("El nombre '" + marca.getNombre() + "' ya está siendo usado por otra marca.");
            }
        }
        marcaActualizar.setNombre(marca.getNombre());
        return marcaRepository.save(marca);
    }
    public void delete(String codigo){
        marcaRepository.deleteById(codigo);
    }
}
