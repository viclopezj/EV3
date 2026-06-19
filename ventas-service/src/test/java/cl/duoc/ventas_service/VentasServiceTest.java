package cl.duoc.ventas_service;

import cl.duoc.ventas_service.clients.LocalFeign;
import cl.duoc.ventas_service.dto.LocalDTO;
import cl.duoc.ventas_service.dto.VentaDTO;
import cl.duoc.ventas_service.exception.FechaReporteDuplicadaException;
import cl.duoc.ventas_service.mapper.VentaMapper;
import cl.duoc.ventas_service.model.Venta;
import cl.duoc.ventas_service.repository.VentaRepository;
import cl.duoc.ventas_service.service.VentaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para VentaService")
public class VentasServiceTest{

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private VentaMapper ventaMapper;

    @Mock
    private LocalFeign localFeign;

    @InjectMocks
    private VentaService ventaService;

    private Venta ventaValida;
    private LocalDate fechaPrueba;

    @BeforeEach
    public void setUp() {
        fechaPrueba = LocalDate.of(2026, 6, 19);

        ventaValida = new Venta();
        ventaValida.setId(1L);
        ventaValida.setLocal(100L);
        ventaValida.setFechaReporte(fechaPrueba);
        ventaValida.setVentaMinimas(500000);
        ventaValida.setVentaMaximas(2500000);
        ventaValida.setVentaPromedio(1500000);
    }

    // ==========================================
    //          PRUEBAS CRUD BÁSICO
    // ==========================================

    @Test
    @DisplayName("Debe listar todas las ventas correctamente")
    public void findAll_deberiaRetornarListaDeVentas() {
        when(ventaRepository.findAll()).thenReturn(List.of(ventaValida));

        List<Venta> resultado = ventaService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(ventaRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar una venta por ID exitosamente")
    public void findById_cuandoExiste_deberiaRetornarVenta() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaValida));

        Venta resultado = ventaService.findById(1L);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getLocal());
        verify(ventaRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe retornar null al buscar una venta que no existe")
    public void findById_cuandoNoExiste_deberiaRetornarNull() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        Venta resultado = ventaService.findById(99L);

        assertNull(resultado);
        verify(ventaRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar una venta correctamente si no está duplicada")
    public void save_cuandoNoEstaDuplicada_deberiaGuardarYRetornarVenta() {
        when(ventaRepository.existsByFechaReporteAndLocal(fechaPrueba, 100L)).thenReturn(false);
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaValida);

        Venta resultado = ventaService.save(ventaValida);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(ventaRepository).save(ventaValida);
    }

    @Test
    @DisplayName("Debe lanzar FechaReporteDuplicadaException al guardar un registro existente")
    public void save_cuandoYaExisteReporte_deberiaLanzarExcepcion() {
        when(ventaRepository.existsByFechaReporteAndLocal(fechaPrueba, 100L)).thenReturn(true);

        assertThrows(FechaReporteDuplicadaException.class, () -> {
            ventaService.save(ventaValida);
        });

        verify(ventaRepository, never()).save(any(Venta.class));
    }

    @Test
    @DisplayName("Debe actualizar una venta existente correctamente")
    public void update_cuandoExisteYNoHayConflicto_deberiaActualizarYRetornarVenta() {
        Venta ventaNuevaInfo = new Venta();
        ventaNuevaInfo.setLocal(100L);
        ventaNuevaInfo.setFechaReporte(fechaPrueba); // Misma fecha y local, no debería validar duplicado en BD
        ventaNuevaInfo.setVentaMinimas(600000);
        ventaNuevaInfo.setVentaMaximas(3000000);
        ventaNuevaInfo.setVentaPromedio(1800000);

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaValida));
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaNuevaInfo);

        Venta resultado = ventaService.update(1L, ventaNuevaInfo);

        assertNotNull(resultado);
        assertEquals(600000, resultado.getVentaMinimas());
        verify(ventaRepository).save(any(Venta.class));
    }

    @Test
    @DisplayName("Debe lanzar FechaReporteDuplicadaException al actualizar modificando a una combinación ya existente")
    public void update_cuandoNuevaCombinacionYaExiste_deberiaLanzarExcepcion() {
        LocalDate nuevaFecha = LocalDate.of(2026, 6, 20);
        Venta ventaModificada = new Venta();
        ventaModificada.setLocal(200L); // Cambia local
        ventaModificada.setFechaReporte(nuevaFecha); // Cambia fecha

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaValida));
        // Al cambiar datos del negocio, se gatilla la validación de duplicados y retorna true
        when(ventaRepository.existsByFechaReporteAndLocal(nuevaFecha, 200L)).thenReturn(true);

        assertThrows(FechaReporteDuplicadaException.class, () -> {
            ventaService.update(1L, ventaModificada);
        });

        verify(ventaRepository, never()).save(any(Venta.class));
    }

    @Test
    @DisplayName("Debe eliminar una venta por ID correctamente")
    public void delete_deberiaEliminarVentaPorId() {
        ventaService.delete(1L);

        verify(ventaRepository).deleteById(1L);
    }

    // ==========================================
    //    PRUEBAS DE INTEGRACIÓN DTO Y FEIGN
    // ==========================================

    @Test
    @DisplayName("Debe buscar un DTO enriquecido llamando al cliente Feign de Locales")
    public void findDTO_deberiaRetornarVentaDTOConInformacionDeLocal() {
        VentaDTO ventaDTO = new VentaDTO();
        LocalDTO localDTO = new LocalDTO();
        localDTO.setId(100L);
        localDTO.setNombre("Local Providencia");

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaValida));
        when(ventaMapper.toDTO(ventaValida)).thenReturn(ventaDTO);
        when(localFeign.buscarLocal(100L)).thenReturn(localDTO);

        VentaDTO resultado = ventaService.findDTO(1L);

        assertNotNull(resultado);
        assertNotNull(resultado.getLocal());
        assertEquals(100L, resultado.getLocal().getId());
        verify(localFeign).buscarLocal(100L);
    }

    @Test
    @DisplayName("Debe listar los DTOs procesando el bucle y asignando el cliente Feign a cada uno")
    public void findDTOList_deberiaRetornarListaDeVentasDTOCompletas() {
        VentaDTO ventaDTO = new VentaDTO();
        LocalDTO localDTO = new LocalDTO();

        when(ventaRepository.findAll()).thenReturn(List.of(ventaValida));
        when(ventaMapper.toDTO(ventaValida)).thenReturn(ventaDTO);
        when(localFeign.buscarLocal(100L)).thenReturn(localDTO);

        List<VentaDTO> resultado = ventaService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(localFeign, times(1)).buscarLocal(100L);
    }

    // ==========================================
    //          PRUEBAS DE FILTROS (QUERY)
    // ==========================================

    @Test
    @DisplayName("Debe filtrar ventas por ID de Local")
    public void findByLocal_deberiaRetornarVentasFiltradas() {
        when(ventaRepository.findAllByLocal(100L)).thenReturn(List.of(ventaValida));

        List<Venta> resultado = ventaService.findByLocal(100L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(ventaRepository).findAllByLocal(100L);
    }

    @Test
    @DisplayName("Debe filtrar por promedio mayor o igual a un valor")
    public void findByPromedioGreater_deberiaRetornarVentasFiltradas() {
        when(ventaRepository.findAllByVentaPromedioGreaterThanEqual(1000000)).thenReturn(List.of(ventaValida));

        List<Venta> resultado = ventaService.findByPromedioGreater(1000000);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(ventaRepository).findAllByVentaPromedioGreaterThanEqual(1000000);
    }
}