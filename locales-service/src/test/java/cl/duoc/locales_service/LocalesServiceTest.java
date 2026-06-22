package cl.duoc.locales_service;

import cl.duoc.locales_service.clients.GerenteFeign;
import cl.duoc.locales_service.dto.GerenteDTO;
import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.exception.DireccionLocalExistenteException;
import cl.duoc.locales_service.exception.NombreLocalExistenteException;
import cl.duoc.locales_service.mapper.LocalMapper;
import cl.duoc.locales_service.model.Local;
import cl.duoc.locales_service.repository.LocalRepository;
import cl.duoc.locales_service.service.LocalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - LocalService")
class LocalesServiceTest {

    @Mock
    private LocalRepository localRepository;

    @Mock
    private LocalMapper localMapper;

    @Mock
    private GerenteFeign gerenteFeign;

    @InjectMocks
    private LocalService localService;

    private Local local;
    private LocalDTO localDTO;
    private GerenteDTO gerenteDTO;

    @BeforeEach
    void setUp() {
        local = new Local();
        local.setId(1L);
        local.setNombre("Local Providencia");
        local.setComuna("Providencia");
        local.setDireccion("Av. Providencia 1234");
        local.setHoraApertura(LocalTime.of(9, 0));
        local.setHoraCierre(LocalTime.of(21, 0));
        local.setGerente(5L);

        gerenteDTO = new GerenteDTO();
        gerenteDTO.setId(5L);
        gerenteDTO.setNombreCompleto("Juan Pérez");
        gerenteDTO.setNivelMando("Senior");

        localDTO = new LocalDTO();
        localDTO.setId(1L);
        localDTO.setNombre("Local Providencia");
        localDTO.setHorasHabiles("09:00:00 - 21:00:00");
    }

    @Test
    @DisplayName("findAll - Debería retornar lista completa de locales")
    void testFindAll() {
        when(localRepository.findAll()).thenReturn(List.of(local));
        List<Local> resultado = localService.findAll();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("save - Debería guardar exitosamente si no hay duplicados")
    void testSave_Exitoso() {
        when(localRepository.existsByNombreIgnoreCase(local.getNombre())).thenReturn(false);
        when(localRepository.existsByDireccionIgnoreCase(local.getDireccion())).thenReturn(false);
        when(localRepository.save(any(Local.class))).thenReturn(local);

        Local guardado = localService.save(local);
        assertNotNull(guardado);
        assertEquals("Local Providencia", guardado.getNombre());
    }

    @Test
    @DisplayName("save - Debería lanzar NombreLocalExistenteException si el nombre ya existe")
    void testSave_NombreDuplicado() {
        when(localRepository.existsByNombreIgnoreCase(local.getNombre())).thenReturn(true);

        assertThrows(NombreLocalExistenteException.class, () -> localService.save(local));
        verify(localRepository, never()).save(any(Local.class));
    }

    @Test
    @DisplayName("save - Debería lanzar DireccionLocalExistenteException si la dirección ya existe")
    void testSave_DireccionDuplicada() {
        when(localRepository.existsByNombreIgnoreCase(local.getNombre())).thenReturn(false);
        when(localRepository.existsByDireccionIgnoreCase(local.getDireccion())).thenReturn(true);

        assertThrows(DireccionLocalExistenteException.class, () -> localService.save(local));
        verify(localRepository, never()).save(any(Local.class));
    }

    @Test
    @DisplayName("findDTO - Debería retornar el DTO armado correctamente uniendo Feign")
    void testFindDTO() {
        when(localRepository.findById(1L)).thenReturn(Optional.of(local));
        when(localMapper.toDTO(local)).thenReturn(localDTO);
        when(gerenteFeign.buscarGerente(5L)).thenReturn(gerenteDTO);

        LocalDTO resultado = localService.findDTO(1L);
        assertNotNull(resultado);
        assertNotNull(resultado.getGerente());
        assertEquals("Juan Pérez", resultado.getGerente().getNombreCompleto());
    }

    @Test
    @DisplayName("findByTipoGerente - Debería filtrar locales por nivel de mando del gerente")
    void testFindByTipoGerente() {
        when(localRepository.findAll()).thenReturn(List.of(local));
        when(localMapper.toDTO(local)).thenReturn(localDTO);
        when(gerenteFeign.buscarGerente(5L)).thenReturn(gerenteDTO);

        List<LocalDTO> filtrados = localService.findByTipoGerente("Senior");
        assertEquals(1, filtrados.size());

        List<LocalDTO> vacio = localService.findByTipoGerente("Junior");
        assertTrue(vacio.isEmpty());
    }
}