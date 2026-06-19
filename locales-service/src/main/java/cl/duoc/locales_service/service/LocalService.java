package cl.duoc.locales_service.service;

import cl.duoc.locales_service.clients.GerenteFeign;
import cl.duoc.locales_service.dto.GerenteDTO;
import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.exception.DireccionLocalExistenteException;
import cl.duoc.locales_service.exception.NombreLocalExistenteException;
import cl.duoc.locales_service.mapper.LocalMapper;
import cl.duoc.locales_service.model.Local;
import cl.duoc.locales_service.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;
    @Autowired
    private LocalMapper localMapper;
    @Autowired
    private GerenteFeign gerenteFeign;

    public List<Local> findAll() {
        return localRepository.findAll();
    }
    public Local findById(Long id) {
        return localRepository.findById(id).orElse(null);
    }
    public Local save(Local local) {
        // VALIDACIONES
        if (localRepository.existsByNombreIgnoreCase(local.getNombre())) {
            throw new NombreLocalExistenteException("El nombre de local '" + local.getNombre() + "' ya se encuentra registrado.");
        }

        if (localRepository.existsByDireccionIgnoreCase(local.getDireccion())) {
            throw new DireccionLocalExistenteException("La dirección '" + local.getDireccion() + "' ya está asignada a otro local.");
        }
        return localRepository.save(local);
    }
    public Local update(Long id, Local local) {
        Local localActualizar = localRepository.findById(id).orElse(null);
        if (localActualizar == null) return null;
        // --- VALIDACIONES
        if (!localActualizar.getNombre().equalsIgnoreCase(local.getNombre())) {
            if (localRepository.existsByNombreIgnoreCase(local.getNombre())) {
                throw new NombreLocalExistenteException("No se puede actualizar. El nombre '" + local.getNombre() + "' ya existe.");
            }
        }

        if (!localActualizar.getDireccion().equalsIgnoreCase(local.getDireccion())) {
            if (localRepository.existsByDireccionIgnoreCase(local.getDireccion())) {
                throw new DireccionLocalExistenteException("No se puede actualizar. La dirección '" + local.getDireccion() + "' ya está en uso.");
            }
        }
        // Mapear campos aquí
        localActualizar.setNombre(local.getNombre());
        localActualizar.setComuna(local.getComuna());
        localActualizar.setDireccion(local.getDireccion());
        localActualizar.setHoraApertura(local.getHoraApertura());
        localActualizar.setHoraCierre(local.getHoraCierre());
        localActualizar.setGerente(local.getGerente());
        return localRepository.save(localActualizar);
    }
    public void delete(Long id) {
        localRepository.deleteById(id);
    }

    public LocalDTO findDTO(Long id) {
        Local local = findById(id);
        LocalDTO localDTO = localMapper.toDTO(local);
        GerenteDTO gerenteDTO = gerenteFeign.buscarGerente(local.getGerente());
        localDTO.setGerente(gerenteDTO);
        return localDTO;
    }

    public List<LocalDTO> findDTOList(){
        List<Local> listado = localRepository.findAll();
        List<LocalDTO> listadoDTO = new ArrayList<>();

        for (Local local : listado){
            LocalDTO dto = localMapper.toDTO(local);
            GerenteDTO gerente = gerenteFeign.buscarGerente(local.getGerente());
            dto.setGerente(gerente);
            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<Local> findByComuna(String comuna){
        return localRepository.findAllByComunaIgnoreCase(comuna);
    }

    public List<LocalDTO> findByIdGerente(Long id){
        List<Local> listado = localRepository.findAllByGerente(id);
        List<LocalDTO> listadoDTO = new ArrayList<>();

        for (Local local : listado){
            LocalDTO dto = localMapper.toDTO(local);
            GerenteDTO gerente = gerenteFeign.buscarGerente(local.getGerente());
            dto.setGerente(gerente);
            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<LocalDTO> findByTipoGerente(String tipo){
        List<Local> listado = localRepository.findAll();
        List<LocalDTO> listadoDTO = new ArrayList<>();

        for (Local local : listado){
            LocalDTO dto = localMapper.toDTO(local);
            GerenteDTO gerente = gerenteFeign.buscarGerente(local.getGerente());
            dto.setGerente(gerente);
            if(gerente != null
                    && gerente.getNivelMando() != null
                    && gerente.getNivelMando().equalsIgnoreCase(tipo)){
                listadoDTO.add(dto);
            }
        }
        return listadoDTO;
    }
}
