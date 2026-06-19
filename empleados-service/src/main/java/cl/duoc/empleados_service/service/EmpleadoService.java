package cl.duoc.empleados_service.service;

import cl.duoc.empleados_service.clients.LocalFeign;
import cl.duoc.empleados_service.dto.EmpleadoDTO;
import cl.duoc.empleados_service.exception.EmailInvalidoException;
import cl.duoc.empleados_service.dto.LocalDTO;
import cl.duoc.empleados_service.exception.TelefonoInvalidoException;
import cl.duoc.empleados_service.mapper.EmpleadoMapper;
import cl.duoc.empleados_service.model.Empleado;
import cl.duoc.empleados_service.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private EmpleadoMapper empleadoMapper;
    @Autowired
    private LocalFeign localFeign;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }
    public Empleado findById(Long id){
        return empleadoRepository.findById(id).orElse(null);
    }
    public Empleado save(Empleado empleado) {
        // VALIDACIONES
        if (empleado.getEmail() == null || !empleado.getEmail().contains("@")) {
            throw new EmailInvalidoException("El correo electrónico debe contener el carácter '@'.");
        }

        String emailLower = empleado.getEmail().toLowerCase();
        if (!(emailLower.endsWith(".cl") || emailLower.endsWith(".com") || emailLower.endsWith(".net"))) {
            throw new EmailInvalidoException("El correo electrónico debe terminar con una extensión válida (.cl, .com o .net).");
        }

        if (empleado.getFono() == null || !empleado.getFono().startsWith("+569")) {
            throw new TelefonoInvalidoException("El número de teléfono debe comenzar con el prefijo de Chile '+569'.");
        }
        return empleadoRepository.save(empleado);
    }
    public Empleado update(Long id, Empleado empleado) {
        Empleado empleadoActualizar = empleadoRepository.findById(id).orElse(null);
        if (empleadoActualizar == null) return null;
        // VALIDACIONES
        if (empleado.getEmail() == null || !empleado.getEmail().contains("@")) {
            throw new EmailInvalidoException("El correo electrónico debe contener el carácter '@'.");
        }

        String emailLower = empleado.getEmail().toLowerCase();
        if (!(emailLower.endsWith(".cl") || emailLower.endsWith(".com") || emailLower.endsWith(".net"))) {
            throw new EmailInvalidoException("El correo electrónico debe terminar con una extensión válida (.cl, .com o .net).");
        }

        if (empleado.getFono() == null || !empleado.getFono().startsWith("+569")) {
            throw new TelefonoInvalidoException("El número de teléfono debe comenzar con el prefijo de Chile '+569'.");
        }

        // Mapear campos aquí
        empleadoActualizar.setNombre(empleado.getNombre());
        empleadoActualizar.setApellido(empleado.getApellido());
        empleadoActualizar.setEmail(empleado.getEmail());
        empleadoActualizar.setFono(empleado.getFono());
        empleadoActualizar.setSalario((empleado.getSalario()));
        empleadoActualizar.setLocal(empleado.getLocal());
        return empleadoRepository.save(empleadoActualizar);
    }
    public void delete(Long id) {
        empleadoRepository.deleteById(id);
    }

    public EmpleadoDTO findDTO(Long id){
        Empleado empleado = findById(id);
        EmpleadoDTO empleadoDTO = empleadoMapper.toDTO(empleado);
        LocalDTO localDTO = localFeign.buscarLocal(empleado.getLocal());
        empleadoDTO.setLocal(localDTO);
        return empleadoDTO;
    }
    public List<EmpleadoDTO> findDTOList(){
        List<Empleado> listado = empleadoRepository.findAll();
        List<EmpleadoDTO> listadoDTO = new ArrayList<>();

        for (Empleado empleado : listado){
            EmpleadoDTO dto = empleadoMapper.toDTO(empleado);
            LocalDTO local = localFeign.buscarLocal(empleado.getLocal());
            dto.setLocal(local);
            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<Empleado> findByEmail(String email){
        return empleadoRepository.findAllByEmailEndsWithIgnoreCase(email);
    }
    public List<Empleado> findBySalarioMayor(Integer salario){
        return empleadoRepository.findAllBySalarioGreaterThanEqual(salario);
    }
    public List<Empleado> findBySalarioMenor(Integer salario){
        return empleadoRepository.findAllBySalarioLessThanEqual(salario);
    }
    public List<Empleado> findByLocal(Long id){
        return empleadoRepository.findAllByLocal(id);
    }

}
