package cl.duoc.gerentes_service;

import cl.duoc.gerentes_service.dto.GerenteDTO;
import cl.duoc.gerentes_service.exception.BonoFueraDeRangoException;
import cl.duoc.gerentes_service.exception.EmailInvalidoException;
import cl.duoc.gerentes_service.exception.NivelMandoInvalidoException;
import cl.duoc.gerentes_service.exception.TelefonoInvalidoException;
import cl.duoc.gerentes_service.mapper.GerenteMapper;
import cl.duoc.gerentes_service.model.Gerente;
import cl.duoc.gerentes_service.repository.GerenteRepository;
import cl.duoc.gerentes_service.service.GerenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - GerenteService")
class GerentesServiceTest {

    @Mock
    private GerenteRepository gerenteRepository;

    @Mock
    private GerenteMapper gerenteMapper;

    @InjectMocks
    private GerenteService gerenteService;

    private Gerente gerenteValido;

    @BeforeEach
    void setUp() {
        gerenteValido = new Gerente();
        gerenteValido.setId(1L);
        gerenteValido.setNombre("Juan");
        gerenteValido.setApellido("Pérez");
        gerenteValido.setEmail("juan.perez@empresa.cl");
        gerenteValido.setFono("+56912345678");
        gerenteValido.setSalario(1500000);
        gerenteValido.setBono(25000); // Rango correcto para Senior (10001 - 50000)
        gerenteValido.setNivelMando("Senior");
    }

    @Test
    @DisplayName("findAll - Debería retornar lista de gerentes")
    void testFindAll() {
        when(gerenteRepository.findAll()).thenReturn(List.of(gerenteValido));
        List<Gerente> listado = gerenteService.findAll();
        assertEquals(1, listado.size());
    }

    @Test
    @DisplayName("save - Debería guardar con éxito si cumple todas las reglas")
    void testSave_Exitoso() {
        when(gerenteRepository.save(any(Gerente.class))).thenReturn(gerenteValido);
        Gerente guardado = gerenteService.save(gerenteValido);
        assertNotNull(guardado);
        assertEquals("Juan", guardado.getNombre());
    }

    @Test
    @DisplayName("save - Debería lanzar EmailInvalidoException si falta '@' o extensión incorrecta")
    void testSave_EmailInvalido() {
        gerenteValido.setEmail("juan.perezempresa.org");
        assertThrows(EmailInvalidoException.class, () -> gerenteService.save(gerenteValido));
        verify(gerenteRepository, never()).save(any(Gerente.class));
    }

    @Test
    @DisplayName("save - Debería lanzar TelefonoInvalidoException si no inicia con +569")
    void testSave_TelefonoInvalido() {
        gerenteValido.setFono("912345678");
        assertThrows(TelefonoInvalidoException.class, () -> gerenteService.save(gerenteValido));
    }

    @Test
    @DisplayName("save - Debería lanzar NivelMandoInvalidoException si el nivel no existe")
    void testSave_NivelInvalido() {
        gerenteValido.setNivelMando("Gerente General");
        assertThrows(NivelMandoInvalidoException.class, () -> gerenteService.save(gerenteValido));
    }

    @Test
    @DisplayName("save - Debería lanzar BonoFueraDeRangoException si el bono no corresponde al nivel")
    void testSave_BonoInvalidoParaNivel() {
        gerenteValido.setNivelMando("Junior");
        gerenteValido.setBono(30000); // Junior es de 1 a 10000
        assertThrows(BonoFueraDeRangoException.class, () -> gerenteService.save(gerenteValido));
    }

    @Test
    @DisplayName("findDTO - Debería retornar el DTO transformado")
    void testFindDTO() {
        GerenteDTO dto = new GerenteDTO();
        dto.setId(1L);
        dto.setNombreCompleto("Juan Pérez");
        dto.setNivelMando("Senior");

        when(gerenteRepository.findById(1L)).thenReturn(Optional.of(gerenteValido));
        when(gerenteMapper.toDTO(gerenteValido)).thenReturn(dto);

        GerenteDTO resultado = gerenteService.findDTO(1L);
        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
    }
}