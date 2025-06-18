package edutech.SoporteTecnico.Controller;

import edutech.SoporteTecnico.Model.Ticket;
import edutech.SoporteTecnico.Services.TicketServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@Tag(name = "Ticket ", description = "API para gestionar tickets de soporte técnico")
public class TicketController {

    @Autowired
    private TicketServices ticketServices;

    @Operation(summary = "Obtener todos los tickets",
            description = "Retorna una lista de todos los tickets disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tickets encontrada",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Ticket.class, type = "array"))),
        @ApiResponse(responseCode = "204", description = "No hay tickets disponibles",
                content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Ticket>> listar() {
        List<Ticket> tickets = ticketServices.findAll();
        if (tickets.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }

    @Operation(summary = "Obtener ticket por ID",
            description = "Retorna un ticket específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket encontrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado",
                content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(
            @Parameter(description = "ID del ticket", required = true, example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(ticketServices.findById(id));
    }

    @Operation(summary = "Buscar tickets por RUN de usuario",
            description = "Retorna información de tickets asociados a un RUN específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Información de tickets encontrada",
                content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tickets para el RUN especificado",
                content = @Content)
    })
    @GetMapping("/usuario/{run}")
    public ResponseEntity<String> usuarioTicket(
            @Parameter(description = "RUN del usuario", required = true, example = "12345678-9")
            @PathVariable String run) {
        return ResponseEntity.ok(ticketServices.usuarioTicket(run));
    }

    @Operation(summary = "Crear nuevo ticket",
            description = "Crea un nuevo ticket con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Datos de ticket inválidos",
                    content = @Content)
    })
    @PostMapping("/crear")
    public ResponseEntity<Ticket> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del ticket a crear",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = """
                {
                    "titulo": "Error en inicio de sesión",
                    "mensaje": "No puedo acceder al sistema con mis credenciales",
                    "estado": "PENDIENTE",
                    "run": "12345678-9",
                    "categoria": {
                        "id_categoria": 1
                    }
                }
                """)))
            @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketServices.save(ticket));
    }

    @Operation(summary = "Actualizar ticket existente",
            description = "Actualiza un ticket existente basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de ticket inválidos",
                    content = @Content)
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Ticket> update(
            @Parameter(description = "ID del ticket a actualizar", required = true, example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del ticket",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = """
                {
                    "titulo": "Error en inicio de sesión - Actualizado",
                    "mensaje": "Problema persiste después de limpiar caché",
                    "estado": "EN_PROCESO",
                    "run": "12345678-9"
                }
                """)))
            @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketServices.update(id, ticket));
    }

    @Operation(summary = "Eliminar ticket",
            description = "Elimina un ticket existente basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado",
                content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public void delete(
            @Parameter(description = "ID del ticket a eliminar", required = true, example = "1")
            @PathVariable Integer id) {
        ticketServices.delete(id);
    }
}