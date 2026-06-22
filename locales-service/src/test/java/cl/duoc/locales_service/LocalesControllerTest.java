package cl.duoc.locales_service;

import cl.duoc.locales_service.controller.LocalController;
import cl.duoc.locales_service.dto.GerenteDTO;
import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.model.Local;
import cl.duoc.locales_service.service.LocalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocalController.class)
@DisplayName("Pruebas Integración Capa Web - LocalController")
class LocalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocalService localService;

    @Autowired
    private ObjectMapper objectMapper;

    private Local local;
    private LocalDTO localDTO;

    @BeforeEach
    void setUp() {
        local = new Local();
        local.setId(1L);
        local.setNombre("Local Central");
        local.setComuna("Santiago");
        local.setDireccion("Av. Libertador 1234");
        local.setHoraApertura(LocalTime.of(9, 0));
        local.setHoraCierre(LocalTime.of(21, 0));
        local.setGerente(1L);

        GerenteDTO gerenteDTO = new GerenteDTO();
        gerenteDTO.setId(1L);
        gerenteDTO.setNombreCompleto("Carlos Fuentes");
        gerenteDTO.setNivelMando("Senior");

        localDTO = new LocalDTO();
        localDTO.setId(1L);
        localDTO.setNombre("Local Central");
        localDTO.setHorasHabiles("09:00:00 - 21:00:00");
        localDTO.setGerente(gerenteDTO);
    }

    @Test
    @DisplayName("GET /api/v1/locales - Debería retornar 200 y la lista de locales")
    void testListar() throws Exception {
        when(localService.findAll()).thenReturn(List.of(local));

        mockMvc.perform(get("/api/v1/locales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Local Central"));
    }

    @Test
    @DisplayName("GET /api/v1/locales/{id} - Debería retornar 200 si el local existe")
    void testBuscarPorId_Existe() throws Exception {
        when(localService.findById(1L)).thenReturn(local);

        mockMvc.perform(get("/api/v1/locales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comuna").value("Santiago"));
    }

    @Test
    @DisplayName("POST /api/v1/locales - Debería retornar 201 al crear un local")
    void testRegistrar() throws Exception {
        when(localService.save(any(Local.class))).thenReturn(local);

        mockMvc.perform(post("/api/v1/locales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(local)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccion").value("Av. Libertador 1234"));
    }

    @Test
    @DisplayName("PUT /api/v1/locales/{id} - Debería retornar 200 si se actualiza con éxito")
    void testActualizar_Existe() throws Exception {
        when(localService.update(eq(1L), any(Local.class))).thenReturn(local);

        mockMvc.perform(put("/api/v1/locales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(local)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/locales/{id} - Debería retornar 204 No Content")
    void testBorrar() throws Exception {
        mockMvc.perform(delete("/api/v1/locales/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/v1/locales/listado - Debería retornar lista de DTOs")
    void testListarDTO() throws Exception {
        when(localService.findDTOList()).thenReturn(List.of(localDTO));

        mockMvc.perform(get("/api/v1/locales/listado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].horas_habiles").value("09:00:00 - 21:00:00"))
                .andExpect(jsonPath("$[0].gerente.nombre_completo").value("Carlos Fuentes"));
    }

    @Test
    @DisplayName("GET /api/v1/locales/comuna/{comuna} - Debería retornar locales filtrados")
    void testLocalPorComuna() throws Exception {
        when(localService.findByComuna("Santiago")).thenReturn(List.of(local));

        mockMvc.perform(get("/api/v1/locales/comuna/Santiago"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comuna").value("Santiago"));
    }

    @Test
    @DisplayName("GET /api/v1/locales/gerente-tipo/{tipo} - Debería retornar locales por nivel de mando")
    void testLocalPorGerenteTipo() throws Exception {
        when(localService.findByTipoGerente("Senior")).thenReturn(List.of(localDTO));

        mockMvc.perform(get("/api/v1/locales/gerente-tipo/Senior"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gerente.nivel_mando").value("Senior"));
    }
}