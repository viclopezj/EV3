package cl.duoc.ventas_service.service;

import cl.duoc.ventas_service.clients.LocalFeign;
import cl.duoc.ventas_service.dto.LocalDTO;
import cl.duoc.ventas_service.dto.VentaDTO;
import cl.duoc.ventas_service.exception.FechaReporteDuplicadaException;
import cl.duoc.ventas_service.mapper.VentaMapper;
import cl.duoc.ventas_service.model.Venta;
import cl.duoc.ventas_service.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private VentaMapper ventaMapper;
    @Autowired
    private LocalFeign localFeign;

    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }
    public Venta findById(Long id){
        return ventaRepository.findById(id).orElse(null);
    }
    public Venta save(Venta venta) {
        // VALIDACIONES
        if (ventaRepository.existsByFechaReporteAndLocal(venta.getFechaReporte(), venta.getLocal())) {
            throw new FechaReporteDuplicadaException("Ya existe un reporte de ventas registrado para la fecha " +
                    venta.getFechaReporte() + " en el local ID: " + venta.getLocal());
        }
        return ventaRepository.save(venta);
    }
    public Venta update(Long id, Venta venta) {
        Venta ventaActualizar = ventaRepository.findById(id).orElse(null);
        if (ventaActualizar == null) return null;
        // VALIDACIONES
        if (!ventaActualizar.getFechaReporte().equals(venta.getFechaReporte()) ||
                !ventaActualizar.getLocal().equals(venta.getLocal())) {
            if (ventaRepository.existsByFechaReporteAndLocal(venta.getFechaReporte(), venta.getLocal())) {
                throw new FechaReporteDuplicadaException("No se puede actualizar. Ya existe un reporte para la fecha " +
                        venta.getFechaReporte() + " en el local ID: " + venta.getLocal());
            }
        }
        // Mapear campos aquí
        ventaActualizar.setLocal(venta.getLocal());
        ventaActualizar.setFechaReporte(venta.getFechaReporte());
        ventaActualizar.setVentaMinimas(venta.getVentaMinimas());
        ventaActualizar.setVentaMaximas(venta.getVentaMaximas());
        ventaActualizar.setVentaPromedio(venta.getVentaPromedio());

        return ventaRepository.save(ventaActualizar);
    }

    public void delete(Long id) {
        ventaRepository.deleteById(id);
    }

    public VentaDTO findDTO(Long id) {
        Venta venta = ventaRepository.findById(id).orElse(null);
        VentaDTO ventaDTO = ventaMapper.toDTO(venta);
        LocalDTO localDTO = localFeign.buscarLocal(venta.getLocal());
        ventaDTO.setLocal(localDTO);
        return ventaDTO;
    }

    public List<VentaDTO> findDTOList(){
        List<Venta> listado = ventaRepository.findAll();
        List<VentaDTO> listadoDTO = new ArrayList<>();

        for (Venta venta : listado) {
            VentaDTO dto = ventaMapper.toDTO(venta);
            LocalDTO local = localFeign.buscarLocal(venta.getLocal());
            dto.setLocal(local);
            listadoDTO.add(dto);
        }
        return listadoDTO;
    }

    public List<Venta> findByLocal (Long id){
        return ventaRepository.findAllByLocal(id);
    }

    public List<Venta> findByMaximaLess(Integer venta){
        return ventaRepository.findAllByVentaMaximasLessThanEqual(venta);
    }

    public List<Venta> findByMinimasGreater(Integer venta){
        return ventaRepository.findAllByVentaMinimasGreaterThanEqual(venta);
    }

    public List<Venta> findByPromedioGreater(Integer venta){
        return ventaRepository.findAllByVentaPromedioGreaterThanEqual(venta);
    }

}
