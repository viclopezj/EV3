package cl.duoc.proveedores_service;

import cl.duoc.proveedores_service.dto.ProveedorDTO;
import cl.duoc.proveedores_service.exception.EmailInvalidoException;
import cl.duoc.proveedores_service.exception.NombreProveedorExistenteException;
import cl.duoc.proveedores_service.exception.TelefonoInvalidoException;
import cl.duoc.proveedores_service.exception.TipoProveedorInvalidoException;
import cl.duoc.proveedores_service.mapper.ProveedorMapper;
import cl.duoc.proveedores_service.model.Proveedor;
import cl.duoc.proveedores_service.repository.ProveedorRepository;
import cl.duoc.proveedores_service.service.ProveedorService;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ProveedorService")
public class ProveedoresServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private ProveedorMapper proveedorMapper;

    @InjectMocks
    private ProveedorService proveedorService;

    private Proveedor proveedorValido;

    @BeforeEach
    public void setUp() {
        proveedorValido = new Proveedor();
        proveedorValido.setId(1L);
        proveedorValido.setNombreEmpresa("Proveedores Distribución Ltda");
        proveedorValido.setEmail("contacto@distribucion.cl");
        proveedorValido.setFono("+56912345678");
        proveedorValido.setRegion("Metropolitana");
        proveedorValido.setTipoProveedor("Alimentos");
    }

    // ==========================================
    //          PRUEBAS CRUD BÁSICO
    // ==========================================

    @Test
    @DisplayName("Debe listar todos los proveedores correctamente")
    public void findAll_deberiaRetornarListaDeProveedores() {
        when(proveedorRepository.findAll()).thenReturn(List.of(proveedorValido));

        List<Proveedor> resultado = proveedorService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(proveedorRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar un proveedor por ID exitosamente")
    public void findById_cuandoExiste_deberiaRetornarProveedor() {
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedorValido));

        Proveedor resultado = proveedorService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Proveedores Distribución Ltda", resultado.getNombreEmpresa());
        verify(proveedorRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe retornar null al buscar por ID un proveedor que no existe")
    public void findById_cuandoNoExiste_deberiaRetornarNull() {
        when(proveedorRepository.findById(99L)).thenReturn(Optional.empty());

        Proveedor resultado = proveedorService.findById(99L);

        assertNull(resultado);
        verify(proveedorRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un proveedor válido correctamente")
    public void save_cuandoEsValido_deberiaGuardarYRetornarProveedor() {
        when(proveedorRepository.existsByNombreEmpresaIgnoreCase(anyString())).thenReturn(false);
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorValido);

        Proveedor resultado = proveedorService.save(proveedorValido);

        assertNotNull(resultado);
        assertEquals("Proveedores Distribución Ltda", resultado.getNombreEmpresa());
        verify(proveedorRepository).save(proveedorValido);
    }

    @Test
    @DisplayName("Debe actualizar un proveedor existente de forma correcta")
    public void update_cuandoExisteYEsValido_deberiaModificarYRetornarProveedor() {
        Proveedor proveedorModificado = new Proveedor();
        proveedorModificado.setNombreEmpresa("Nuevo Nombre S.A.");
        proveedorModificado.setEmail("info@nuevonombre.com");
        proveedorModificado.setFono("+56987654321");
        proveedorModificado.setRegion("Valparaíso");
        proveedorModificado.setTipoProveedor("Limpieza");

        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedorValido));
        when(proveedorRepository.existsByNombreEmpresaIgnoreCase("Nuevo Nombre S.A.")).thenReturn(false);
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorModificado);

        Proveedor resultado = proveedorService.update(1L, proveedorModificado);

        assertNotNull(resultado);
        assertEquals("Nuevo Nombre S.A.", resultado.getNombreEmpresa());
        assertEquals("info@nuevonombre.com", resultado.getEmail());
        verify(proveedorRepository).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Debe eliminar un proveedor por ID de forma correcta")
    public void delete_deberiaEliminarProveedorPorId() {
        proveedorService.delete(1L);

        verify(proveedorRepository).deleteById(1L);
    }

    // ==========================================
    //       PRUEBAS DE VALIDACIONES (EXCEPCIONES)
    // ==========================================

    @Test
    @DisplayName("Debe lanzar NombreProveedorExistenteException si el nombre ya existe al guardar")
    public void save_cuandoNombreExiste_deberiaLanzarExcepcion() {
        when(proveedorRepository.existsByNombreEmpresaIgnoreCase(anyString())).thenReturn(true);

        assertThrows(NombreProveedorExistenteException.class, () -> {
            proveedorService.save(proveedorValido);
        });

        verify(proveedorRepository, never()).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Debe lanzar EmailInvalidoException si el correo no contiene un '@'")
    public void save_cuandoEmailNoContieneArroba_deberiaLanzarExcepcion() {
        proveedorValido.setEmail("correosinarroba.cl");
        when(proveedorRepository.existsByNombreEmpresaIgnoreCase(anyString())).thenReturn(false);

        assertThrows(EmailInvalidoException.class, () -> {
            proveedorService.save(proveedorValido);
        });
    }

    @Test
    @DisplayName("Debe lanzar EmailInvalidoException si la extensión no es .cl, .com o .net")
    public void save_cuandoExtensionEmailInvalida_deberiaLanzarExcepcion() {
        proveedorValido.setEmail("contacto@empresa.org"); // .org no permitida
        when(proveedorRepository.existsByNombreEmpresaIgnoreCase(anyString())).thenReturn(false);

        assertThrows(EmailInvalidoException.class, () -> {
            proveedorService.save(proveedorValido);
        });
    }

    @Test
    @DisplayName("Debe lanzar TelefonoInvalidoException si no inicia con '+569'")
    public void save_cuandoFonoNoIniciaConPrefijoChileno_deberiaLanzarExcepcion() {
        proveedorValido.setFono("912345678"); // Falta +56
        when(proveedorRepository.existsByNombreEmpresaIgnoreCase(anyString())).thenReturn(false);

        assertThrows(TelefonoInvalidoException.class, () -> {
            proveedorService.save(proveedorValido);
        });
    }

    @Test
    @DisplayName("Debe lanzar TipoProveedorInvalidoException si el rubro no coincide con los autorizados")
    public void save_cuandoTipoProveedorEsInvalido_deberiaLanzarExcepcion() {
        proveedorValido.setTipoProveedor("Tecnologia"); // Rubro no válido
        when(proveedorRepository.existsByNombreEmpresaIgnoreCase(anyString())).thenReturn(false);

        assertThrows(TipoProveedorInvalidoException.class, () -> {
            proveedorService.save(proveedorValido);
        });
    }

    // ==========================================
    //       PRUEBAS DE MAPPERS Y BUSQUEDAS EXTRA
    // ==========================================

    @Test
    @DisplayName("Debe buscar y retornar un ProveedorDTO de manera correcta")
    public void findDTO_deberiaRetornarProveedorDTO() {
        ProveedorDTO dto = new ProveedorDTO();
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedorValido));
        when(proveedorMapper.toDTO(proveedorValido)).thenReturn(dto);

        ProveedorDTO resultado = proveedorService.findDTO(1L);

        assertNotNull(resultado);
        verify(proveedorMapper).toDTO(proveedorValido);
    }

    @Test
    @DisplayName("Debe listar los DTOs filtrados correctamente")
    public void findDTOList_deberiaRetornarListaDeDTOs() {
        ProveedorDTO dto = new ProveedorDTO();
        when(proveedorRepository.findAll()).thenReturn(List.of(proveedorValido));
        when(proveedorMapper.toDTOlist(anyList())).thenReturn(List.of(dto));

        List<ProveedorDTO> resultado = proveedorService.findDTOList();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(proveedorMapper).toDTOlist(anyList());
    }

    @Test
    @DisplayName("Debe listar proveedores filtrados por tipo correctamente")
    public void findByTipoProveedor_deberiaRetornarListaFiltrada() {
        when(proveedorRepository.findAllByTipoProveedor("Alimentos")).thenReturn(List.of(proveedorValido));

        List<Proveedor> resultado = proveedorService.findByTipoProveedor("Alimentos");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(proveedorRepository).findAllByTipoProveedor("Alimentos");
    }
}