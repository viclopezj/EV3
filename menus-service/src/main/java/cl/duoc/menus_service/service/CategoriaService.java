package cl.duoc.menus_service.service;

import cl.duoc.menus_service.exception.CodigoCategoriaExistenteException;
import cl.duoc.menus_service.exception.NombreCategoriaRepetidoException;
import cl.duoc.menus_service.model.Categoria;
import cl.duoc.menus_service.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }
    public Categoria findById(String codigo){
        return categoriaRepository.findById(codigo.toUpperCase()).orElse(null);
    }
    public Categoria save(Categoria categoria){
        // --- VALIDACIONES
        if (categoriaRepository.existsById(categoria.getCodigo())) {
            throw new CodigoCategoriaExistenteException("El código de categoría '" + categoria.getCodigo() + "' ya se encuentra registrado.");
        }

        if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new NombreCategoriaRepetidoException("El nombre de categoría '" + categoria.getNombre() + "' ya se encuentra registrado.");
        }
        return categoriaRepository.save(categoria);
    }
    public Categoria update(String codigo, Categoria categoria){
        Categoria categoriaActualizar = categoriaRepository.findById(codigo).orElse(null);
        if (categoriaActualizar == null) return null;
        // --- VALIDACIONES
        if (!categoriaActualizar.getNombre().equalsIgnoreCase(categoria.getNombre())) {
            if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
                throw new NombreCategoriaRepetidoException("No se puede actualizar. El nombre '" + categoria.getNombre() + "' ya está asignado a otra categoría.");
            }
        }
        categoriaActualizar.setNombre(categoria.getNombre());
        return categoriaRepository.save(categoria);
    }
    public void delete(String codigo){
        categoriaRepository.deleteById(codigo);
    }
}
