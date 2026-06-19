package cl.duoc.gerentes_service.service;

import cl.duoc.gerentes_service.dto.GerenteDTO;
import cl.duoc.gerentes_service.exception.BonoFueraDeRangoException;
import cl.duoc.gerentes_service.exception.EmailInvalidoException;
import cl.duoc.gerentes_service.exception.NivelMandoInvalidoException;
import cl.duoc.gerentes_service.exception.TelefonoInvalidoException;
import cl.duoc.gerentes_service.mapper.GerenteMapper;
import cl.duoc.gerentes_service.model.Gerente;
import cl.duoc.gerentes_service.repository.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;
    @Autowired
    private GerenteMapper gerenteMapper;

    public List<Gerente> findAll() {
        return gerenteRepository.findAll();
    }
    public Gerente findById(Long id){
        return gerenteRepository.findById(id).orElse(null);
    }
    public Gerente save(Gerente gerente) {
        // VALIDACIONES
        if (gerente.getEmail() == null || !gerente.getEmail().contains("@")) {
            throw new EmailInvalidoException("El correo electrónico del gerente debe contener el carácter '@'.");
        }

        String emailLower = gerente.getEmail().toLowerCase();
        if (!(emailLower.endsWith(".cl") || emailLower.endsWith(".com") || emailLower.endsWith(".net"))) {
            throw new EmailInvalidoException("El correo electrónico debe terminar con una extensión válida (.cl, .com o .net).");
        }

        if (gerente.getFono() == null || !gerente.getFono().startsWith("+569")) {
            throw new TelefonoInvalidoException("El número de teléfono del gerente debe comenzar con el prefijo de Chile '+569'.");
        }

        String nivel = gerente.getNivelMando();
        if (nivel == null || (!nivel.equalsIgnoreCase("Junior") && !nivel.equalsIgnoreCase("Senior") && !nivel.equalsIgnoreCase("Regional"))) {
            throw new NivelMandoInvalidoException("Nivel de mando inválido. Debe ser exactamente: Junior, Senior o Regional.");
        }

        Integer bono = gerente.getBono();
        if (nivel.equalsIgnoreCase("Junior") && (bono < 1 || bono > 10000)) {
            throw new BonoFueraDeRangoException("Para el nivel Junior, el bono asignado debe estar en el rango de 1 a 10000.");
        } else if (nivel.equalsIgnoreCase("Senior") && (bono < 10001 || bono > 50000)) {
            throw new BonoFueraDeRangoException("Para el nivel Senior, el bono asignado debe estar en el rango de 10001 a 50000.");
        } else if (nivel.equalsIgnoreCase("Regional") && (bono < 50001 || bono > 100000)) {
            throw new BonoFueraDeRangoException("Para el nivel Regional, el bono asignado debe estar en el rango de 50001 a 100000.");
        }
        return gerenteRepository.save(gerente);
    }
    public Gerente update(Long id, Gerente gerente) {
        Gerente gerenteActualizar = gerenteRepository.findById(id).orElse(null);
        if (gerenteActualizar == null) return null;
        // VALIDACIONES
        if (gerente.getEmail() == null || !gerente.getEmail().contains("@")) {
            throw new EmailInvalidoException("El correo electrónico del gerente debe contener el carácter '@'.");
        }

        String emailLower = gerente.getEmail().toLowerCase();
        if (!(emailLower.endsWith(".cl") || emailLower.endsWith(".com") || emailLower.endsWith(".net"))) {
            throw new EmailInvalidoException("El correo electrónico debe terminar con una extensión válida (.cl, .com o .net).");
        }

        if (gerente.getFono() == null || !gerente.getFono().startsWith("+569")) {
            throw new TelefonoInvalidoException("El número de teléfono del gerente debe comenzar con el prefijo de Chile '+569'.");
        }

        String nivel = gerente.getNivelMando();
        if (nivel == null || (!nivel.equalsIgnoreCase("Junior") && !nivel.equalsIgnoreCase("Senior") && !nivel.equalsIgnoreCase("Regional"))) {
            throw new NivelMandoInvalidoException("Nivel de mando inválido. Debe ser exactamente: Junior, Senior o Regional.");
        }

        Integer bono = gerente.getBono();
        if (nivel.equalsIgnoreCase("Junior") && (bono < 1 || bono > 10000)) {
            throw new BonoFueraDeRangoException("Para el nivel Junior, el bono asignado debe estar en el rango de 1 a 10000.");
        } else if (nivel.equalsIgnoreCase("Senior") && (bono < 10001 || bono > 50000)) {
            throw new BonoFueraDeRangoException("Para el nivel Senior, el bono asignado debe estar en el rango de 10001 a 50000.");
        } else if (nivel.equalsIgnoreCase("Regional") && (bono < 50001 || bono > 100000)) {
            throw new BonoFueraDeRangoException("Para el nivel Regional, el bono asignado debe estar en el rango de 50001 a 100000.");
        }
        // Mapear campos aquí
        gerenteActualizar.setNombre(gerente.getNombre());
        gerenteActualizar.setApellido(gerente.getApellido());
        gerenteActualizar.setEmail(gerente.getEmail());
        gerenteActualizar.setFono(gerente.getFono());
        gerenteActualizar.setSalario(gerente.getSalario());
        gerenteActualizar.setBono(gerente.getBono());
        gerenteActualizar.setNivelMando(gerente.getNivelMando());
        return gerenteRepository.save(gerenteActualizar);
    }
    public void delete(Long id) {
        gerenteRepository.deleteById(id);
    }

    public GerenteDTO findDTO(Long id){
        return gerenteMapper.toDTO(findById(id));
    }
    public List<GerenteDTO> findDTOList() {
        return gerenteMapper.toDTOlist(findAll());
    }

    public List<Gerente> findByEmail(String email){
        return gerenteRepository.findAllByEmailEndsWithIgnoreCase(email);
    }

    public List<Gerente> findBySalarioGreaterEqual(Integer salario){
        return gerenteRepository.findAllBySalarioGreaterThanEqual(salario);
    }

    public List<Gerente> findBySalarioLessEqual(Integer salario){
        return gerenteRepository.findAllBySalarioLessThanEqual(salario);
    }

    public List<Gerente> findByBonoGreaterEqual(Integer bono){
        return gerenteRepository.findAllByBonoGreaterThanEqual(bono);
    }

    public List<Gerente> findByBonoLessEqual(Integer bono){
        return gerenteRepository.findAllByBonoLessThanEqual(bono);
    }
}

