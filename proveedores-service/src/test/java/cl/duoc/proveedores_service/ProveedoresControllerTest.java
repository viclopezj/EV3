package cl.duoc.proveedores_service;

import cl.duoc.proveedores_service.controller.ProveedorController;
import cl.duoc.proveedores_service.dto.ProveedorDTO;
import cl.duoc.proveedores_service.model.Proveedor;
import cl.duoc.proveedores_service.service.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProveedorController.class)
@DisplayName("Pruebas en la capa Controller de Proveedores")
class ProveedoresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProveedorService proveedorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Proveedor proveedor;
    private ProveedorDTO proveedorDTO;

    @BeforeEach
    void setUp() {
        // Estructura real según el log: nombreEmpresa, email, fono, region, tipoProveedor
        proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombreEmpresa("Distribuidora Central");
        proveedor.setFono("+56912345678");
        proveedor.setTipoProveedor("Alimentos");
        proveedor.setEmail("contacto@central.cl");
        proveedor.setRegion("Metropolitana");

        // Estructura real del DTO según el log: id, nombre, tipo
        proveedorDTO = new ProveedorDTO();
        proveedorDTO.setId(1L);
        proveedorDTO.setNombre("Distribuidora Central");
        proveedorDTO.setTipo("Alimentos");
    }

    // ==========================================
    //          PRUEBAS CRUD BÁSICO
    // ==========================================

    @Test
    @DisplayName("GET /api/v1/proveedores - Debería retornar 200 OK y la lista de proveedores")
    void testEndpointListar() throws Exception {
        when(proveedorService.findAll()).thenReturn(List.of(proveedor));

        mockMvc.perform(get("/api/v1/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreEmpresa").value("Distribuidora Central"))
                .andExpect(jsonPath("$[0].fono").value("+56912345678"));
    }

    @Test
    @DisplayName("GET /api/v1/proveedores/{id} - Debería retornar 200 OK si el proveedor existe")
    void testEndpointBuscarPorId_Existe() throws Exception {
        when(proveedorService.findById(1L)).thenReturn(proveedor);

        mockMvc.perform(get("/api/v1/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreEmpresa").value("Distribuidora Central"));
    }

    @Test
    @DisplayName("GET /api/v1/proveedores/{id} - Debería retornar 404 NOT FOUND si el proveedor no existe")
    void testEndpointBuscarPorId_NoExiste() throws Exception {
        when(proveedorService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/proveedores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/proveedores - Debería retornar 201 CREATED y el proveedor registrado")
    void testEndpointRegistrar() throws Exception {
        when(proveedorService.save(any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(post("/api/v1/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("contacto@central.cl"));
    }

    @Test
    @DisplayName("PUT /api/v1/proveedores/{id} - Debería retornar 200 OK cuando se actualiza correctamente")
    void testEndpointActualizar_Existe() throws Exception {
        when(proveedorService.update(eq(1L), any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(put("/api/v1/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("PUT /api/v1/proveedores/{id} - Debería retornar 404 NOT FOUND al intentar actualizar un ID inexistente")
    void testEndpointActualizar_NoExiste() throws Exception {
        when(proveedorService.update(eq(99L), any(Proveedor.class))).thenReturn(null);

        mockMvc.perform(put("/api/v1/proveedores/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/proveedores/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointBorrar() throws Exception {
        mockMvc.perform(delete("/api/v1/proveedores/1"))
                .andExpect(status().isNoContent());
    }

    // ==========================================
    //          PRUEBAS ENDPOINTS DTO
    // ==========================================

    @Test
    @DisplayName("GET /api/v1/proveedores/listado - Debería retornar 200 OK y la lista de DTOs")
    void testEndpointListarDTO() throws Exception {
        when(proveedorService.findDTOList()).thenReturn(List.of(proveedorDTO));

        mockMvc.perform(get("/api/v1/proveedores/listado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Distribuidora Central"))
                .andExpect(jsonPath("$[0].tipo").value("Alimentos"));
    }

    @Test
    @DisplayName("GET /api/v1/proveedores/listado/{id} - Debería retornar 200 OK si el DTO existe")
    void testEndpointBuscarPorIdDTO_Existe() throws Exception {
        when(proveedorService.findDTO(1L)).thenReturn(proveedorDTO);

        mockMvc.perform(get("/api/v1/proveedores/listado/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Distribuidora Central"))
                .andExpect(jsonPath("$.tipo").value("Alimentos"));
    }

    @Test
    @DisplayName("GET /api/v1/proveedores/listado/{id} - Debería retornar 404 NOT FOUND si el DTO no existe")
    void testEndpointBuscarPorIdDTO_NoExiste() throws Exception {
        when(proveedorService.findDTO(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/proveedores/listado/99"))
                .andExpect(status().isNotFound());
    }

    // ==========================================
    //          PRUEBAS ENDPOINTS DE FILTRO
    // ==========================================

    @Test
    @DisplayName("GET /api/v1/proveedores/tipo-proveedor/{tipo} - Debería retornar proveedores por tipo")
    void testEndpointProveedorPorTipo() throws Exception {
        when(proveedorService.findByTipoProveedor("Alimentos")).thenReturn(List.of(proveedor));

        mockMvc.perform(get("/api/v1/proveedores/tipo-proveedor/Alimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoProveedor").value("Alimentos"));
    }

    @Test
    @DisplayName("GET /api/v1/proveedores/email/{email} - Debería retornar proveedores por sufijo de email")
    void testEndpointProveedorPorEmail() throws Exception {
        when(proveedorService.findByEmailEnds("central.cl")).thenReturn(List.of(proveedor));

        mockMvc.perform(get("/api/v1/proveedores/email/central.cl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("contacto@central.cl"));
    }

    @Test
    @DisplayName("GET /api/v1/proveedores/region/{region} - Debería retornar proveedores por región")
    void testEndpointProveedorPorRegion() throws Exception {
        when(proveedorService.findByRegion("Metropolitana")).thenReturn(List.of(proveedor));

        mockMvc.perform(get("/api/v1/proveedores/region/Metropolitana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].region").value("Metropolitana"));
    }
}