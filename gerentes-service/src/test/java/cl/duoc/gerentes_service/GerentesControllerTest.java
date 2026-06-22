package cl.duoc.gerentes_service;

import cl.duoc.gerentes_service.controller.GerenteController;
import cl.duoc.gerentes_service.dto.GerenteDTO;
import cl.duoc.gerentes_service.model.Gerente;
import cl.duoc.gerentes_service.service.GerenteService;
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

@WebMvcTest(GerenteController.class)
@DisplayName("Pruebas Integración Capa Web - GerenteController")
class GerentesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GerenteService gerenteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Gerente gerente;
    private GerenteDTO gerenteDTO;

    @BeforeEach
    void setUp() {
        gerente = new Gerente();
        gerente.setId(1L);
        gerente.setNombre("Juan");
        gerente.setApellido("Pérez");
        gerente.setEmail("juan.perez@empresa.cl");
        gerente.setFono("+56912345678");
        gerente.setSalario(1500000);
        gerente.setBono(25000);
        gerente.setNivelMando("Senior");

        gerenteDTO = new GerenteDTO();
        gerenteDTO.setId(1L);
        gerenteDTO.setNombreCompleto("Juan Pérez");
        gerenteDTO.setNivelMando("Senior");
    }

    @Test
    @DisplayName("GET /api/v1/gerentes - Debería retornar status 200 y la lista de gerentes")
    void testListar() throws Exception {
        when(gerenteService.findAll()).thenReturn(List.of(gerente));

        mockMvc.perform(get("/api/v1/gerentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].salario").value(1500000));
    }

    @Test
    @DisplayName("GET /api/v1/gerentes/{id} - Debería retornar 200 si el gerente existe")
    void testBuscarPorId_Existe() throws Exception {
        when(gerenteService.findById(1L)).thenReturn(gerente);

        mockMvc.perform(get("/api/v1/gerentes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.apellido").value("Pérez"));
    }

    @Test
    @DisplayName("POST /api/v1/gerentes - Debería retornar status 201 y el gerente creado")
    void testRegistrar() throws Exception {
        when(gerenteService.save(any(Gerente.class))).thenReturn(gerente);

        mockMvc.perform(post("/api/v1/gerentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gerente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("juan.perez@empresa.cl"));
    }

    @Test
    @DisplayName("PUT /api/v1/gerentes/{id} - Debería retornar 200 al actualizar exitosamente")
    void testActualizar_Existe() throws Exception {
        when(gerenteService.update(eq(1L), any(Gerente.class))).thenReturn(gerente);

        mockMvc.perform(put("/api/v1/gerentes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gerente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/gerentes/{id} - Debería retornar status 204")
    void testBorrar() throws Exception {
        mockMvc.perform(delete("/api/v1/gerentes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/v1/gerentes/listado - Debería retornar status 200 y las propiedades mapeadas por JsonProperty")
    void testListarDTO() throws Exception {
        when(gerenteService.findDTOList()).thenReturn(List.of(gerenteDTO));

        mockMvc.perform(get("/api/v1/gerentes/listado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre_completo").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].nivel_mando").value("Senior"));
    }

    @Test
    @DisplayName("GET /api/v1/gerentes/salario-mayor/{salario} - Debería retornar los gerentes con sueldo superior o igual")
    void testGerentePorSalarioMayor() throws Exception {
        when(gerenteService.findBySalarioGreaterEqual(1000000)).thenReturn(List.of(gerente));

        mockMvc.perform(get("/api/v1/gerentes/salario-mayor/1000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].salario").value(1500000));
    }
}