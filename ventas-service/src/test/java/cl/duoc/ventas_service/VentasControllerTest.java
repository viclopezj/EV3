package cl.duoc.ventas_service;

import cl.duoc.ventas_service.controller.VentaController;
import cl.duoc.ventas_service.dto.VentaDTO;
import cl.duoc.ventas_service.dto.LocalDTO;
import cl.duoc.ventas_service.model.Venta;
import cl.duoc.ventas_service.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
@DisplayName("Pruebas en la capa Controller de Ventas")
class VentasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VentaService ventaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Venta venta;
    private VentaDTO ventaDTO;

    @BeforeEach
    void setUp() {
        venta = new Venta();
        venta.setId(1L);
        venta.setLocal(100L);
        venta.setFechaReporte(LocalDate.of(2026, 6, 19));
        venta.setVentaMinimas(500000);
        venta.setVentaMaximas(2500000);
        venta.setVentaPromedio(1500000);

        ventaDTO = new VentaDTO();
        ventaDTO.setId(1L);
        ventaDTO.setVentasPromedio(1500000);
        LocalDTO localDTO = new LocalDTO();
        localDTO.setId(100L);
        localDTO.setNombre("Local Providencia");
        ventaDTO.setLocal(localDTO);
    }

    // ==========================================
    //          PRUEBAS CRUD BÁSICO
    // ==========================================

    @Test
    @DisplayName("GET /api/v1/ventas - Debería retornar 200 OK y la lista de ventas")
    void testEndpointListar() throws Exception {
        when(ventaService.findAll()).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/v1/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].local").value(100))
                .andExpect(jsonPath("$[0].ventaMinimas").value(500000))
                .andExpect(jsonPath("$[0].ventaMaximas").value(2500000));
    }

    @Test
    @DisplayName("GET /api/v1/ventas/{id} - Debería retornar 200 OK cuando la venta existe")
    void testEndpointBuscarPorId_Existe() throws Exception {
        when(ventaService.findById(1L)).thenReturn(venta);

        mockMvc.perform(get("/api/v1/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.local").value(100));
    }

    @Test
    @DisplayName("GET /api/v1/ventas/{id} - Debería retornar 404 NOT FOUND cuando la venta no existe")
    void testEndpointBuscarPorId_NoExiste() throws Exception {
        when(ventaService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/ventas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/ventas - Debería retornar 201 CREATED y la venta registrada")
    void testEndpointRegistrar() throws Exception {
        when(ventaService.save(any(Venta.class))).thenReturn(venta);

        mockMvc.perform(post("/api/v1/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ventaPromedio").value(1500000));
    }

    @Test
    @DisplayName("PUT /api/v1/ventas/{id} - Debería retornar 200 OK cuando se actualiza correctamente")
    void testEndpointActualizar_Existe() throws Exception {
        when(ventaService.update(eq(1L), any(Venta.class))).thenReturn(venta);

        mockMvc.perform(put("/api/v1/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("PUT /api/v1/ventas/{id} - Debería retornar 404 NOT FOUND al intentar actualizar un ID inexistente")
    void testEndpointActualizar_NoExiste() throws Exception {
        when(ventaService.update(eq(99L), any(Venta.class))).thenReturn(null);

        mockMvc.perform(put("/api/v1/ventas/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/ventas/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointBorrar() throws Exception {
        mockMvc.perform(delete("/api/v1/ventas/1"))
                .andExpect(status().isNoContent());
    }

    // ==========================================
    //          PRUEBAS ENDPOINTS DTO
    // ==========================================

    @Test
    @DisplayName("GET /api/v1/ventas/listado - Debería retornar 200 OK y la lista de DTOs")
    void testEndpointListarDTO() throws Exception {
        when(ventaService.findDTOList()).thenReturn(List.of(ventaDTO));

        mockMvc.perform(get("/api/v1/ventas/listado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].local.nombre").value("Local Providencia"));
    }

    @Test
    @DisplayName("GET /api/v1/ventas/listado/{id} - Debería retornar 200 OK si el DTO existe")
    void testEndpointBuscarPorIdDTO_Existe() throws Exception {
        when(ventaService.findDTO(1L)).thenReturn(ventaDTO);

        mockMvc.perform(get("/api/v1/ventas/listado/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.local.id").value(100));
    }

    @Test
    @DisplayName("GET /api/v1/ventas/listado/{id} - Debería retornar 404 NOT FOUND si el DTO no existe")
    void testEndpointBuscarPorIdDTO_NoExiste() throws Exception {
        when(ventaService.findDTO(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/ventas/listado/99"))
                .andExpect(status().isNotFound());
    }

    // ==========================================
    //          PRUEBAS ENDPOINTS DE FILTRO
    // ==========================================

    @Test
    @DisplayName("GET /api/v1/ventas/local/{local} - Debería retornar 200 OK y ventas filtradas por local")
    void testEndpointVentaPorLocal() throws Exception {
        when(ventaService.findByLocal(100L)).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/v1/ventas/local/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].local").value(100));
    }

    @Test
    @DisplayName("GET /api/v1/ventas/venta-maxima-menor/{venta} - Debería retornar 200 OK")
    void testEndpointVentaMaximaMenorA() throws Exception {
        when(ventaService.findByMaximaLess(3000000)).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/v1/ventas/venta-maxima-menor/3000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ventaMaximas").value(2500000));
    }

    @Test
    @DisplayName("GET /api/v1/ventas/venta-minimas-mayor/{venta} - Debería retornar 200 OK")
    void testEndpointVentaMinimaMayorA() throws Exception {
        when(ventaService.findByMinimasGreater(400000)).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/v1/ventas/venta-minimas-mayor/400000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ventaMinimas").value(500000));
    }

    @Test
    @DisplayName("GET /api/v1/ventas/venta-promedio-mayor/{venta} - Debería retornar 200 OK")
    void testEndpointVentaPromedioMayorA() throws Exception {
        when(ventaService.findByPromedioGreater(1000000)).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/v1/ventas/venta-promedio-mayor/1000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ventaPromedio").value(1500000));
    }
}