package cl.duoc.locales_service.controller;

import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.exception.ErrorResponse;
import cl.duoc.locales_service.exception.NotFoundException;
import cl.duoc.locales_service.model.Local;
import cl.duoc.locales_service.service.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Locales",
        description = "Operaciones disponibles para la gestión de locales"
)
@RestController
@RequestMapping("api/v1/locales")
public class LocalController {
    @Autowired
    private LocalService localService;

    //1
    @Operation(
            summary = "Listar locales",
            description = "Método que lista todos los locales del sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Locales encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Local.class)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(localService.findAll());
    }

    //2
    @Operation(
            summary = "Buscar locales por ID",
            description = "Método que busca un local en el sistema a través del ID. Este id es de tipo numerico (LONG)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Local encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Local.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Local no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(
            @Parameter(example = "1", description = "ID del local a buscar")
            @PathVariable Long id) {
        Local local = localService.findById(id);
        if (local == null) throw new NotFoundException("Local no encontrado");
        return ResponseEntity.ok(local);
    }

    //3
    @Operation(
            summary = "Registra un local",
            description = "Método que registra un local en el sistema. El campo id es automático.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Local.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Datos para crear",
                                    value = "{\"nombre\": \"Local Central\", \"comuna\": \"Santiago\", \"direccion\": \"Av. Libertador 1234\", \"horaApertura\": \"09:00:00\", \"horaCierre\": \"21:00:00\", \"gerente\": 1}"
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "201",
            description = "Local registrado exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Local.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Error en los datos de entrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Local local) {
        Local localNueva = localService.save(local);
        return new ResponseEntity<>(localNueva, HttpStatus.CREATED);
    }

    //4
    @Operation(
            summary = "Actualiza un local por ID",
            description = "Método que actualiza un local en el sistema a través del ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Local.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Datos para actualizar",
                                    value = "{\"nombre\": \"Local Central Modificado\", \"comuna\": \"Providencia\", \"direccion\": \"Nueva Providencia 456\", \"horaApertura\": \"08:30:00\", \"horaCierre\": \"22:00:00\", \"gerente\": 1}"
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Local actualizado exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Local.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Error en los datos de entrada",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Local no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(example = "1", description = "ID del local a actualizar")
            @PathVariable Long id, @Valid @RequestBody Local local) {
        Local localActualizado = localService.update(id, local);
        if (localActualizado == null) throw new NotFoundException("Local no encontrado");
        return ResponseEntity.ok(localActualizado);
    }

    //5
    @Operation(
            summary = "Elimina un local por ID",
            description = "Método que elimina un local en el sistema a través del ID"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Local eliminado exitosamente",
            content = @Content()
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(
            @Parameter(example = "1", description = "ID del local a eliminar")
            @PathVariable Long id) {
        localService.delete(id);
        return new ResponseEntity<>("Gerente eliminado exitosamente", HttpStatus.NO_CONTENT);
    }

    //6
    @Operation(
            summary = "Listar locales ocultando informacion sensible",
            description = "Método que lista todos los locales del sistema de forma resumida"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Locales encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = LocalDTO.class)
                    )
            )
    )
    @GetMapping("/listado")
    public ResponseEntity<?> listarDTO(){
        return ResponseEntity.ok(localService.findDTOList());
    }

    //7
    @Operation(
            summary = "Buscar locales por ID ocultando informacion sensible",
            description = "Método que busca un local en el sistema a través del ID de forma resumida. Este id es de tipo numerico (LONG)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Local encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LocalDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Local no encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @GetMapping("/listado/{id}")
    public ResponseEntity<?> buscarPorIdDTO(
            @Parameter(example = "1", description = "ID del local a buscar")
            @PathVariable Long id) {
        LocalDTO local = localService.findDTO(id);
        if (local == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(local);
    }

    //8
    @Operation(
            summary = "Listar locales por comuna",
            description = "Método que lista todos los locales del sistema a traves de la comuna ingresada."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Locales encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Local.class)
                    )
            )
    )
    @GetMapping("/comuna/{comuna}")
    public ResponseEntity<?> localPorComuna(
            @Parameter(example = "Santiago", description = "Comuna de locales")
            @PathVariable String comuna){
        return ResponseEntity.ok(localService.findByComuna(comuna));
    }

    //9
    @Operation(
            summary = "Listar locales por ID de Gerente",
            description = "Método que lista todos los locales del sistema a traves del ID de Gerente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Locales encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = LocalDTO.class)
                    )
            )
    )
    @GetMapping("/gerente-id/{id}")
    public ResponseEntity<?> localPorGerenteId(
            @Parameter(example = "1", description = "ID del gerente encargado de locales")
            @PathVariable Long id){
        return ResponseEntity.ok(localService.findByIdGerente(id));
    }

    //10
    @Operation(
            summary = "Listar locales por tipo de Gerente",
            description = "Método que lista todos los locales del sistema a traves del tipo de Gerente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Locales encontrados",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = LocalDTO.class)
                    )
            )
    )
    @GetMapping("/gerente-tipo/{gerente}")
    public ResponseEntity<?> localPorGerenteTipo(
            @Parameter(example = "Senior", description = "Tipo de gerente encargado de locales")
            @PathVariable String gerente){
        return ResponseEntity.ok(localService.findByTipoGerente(gerente));
    }
}