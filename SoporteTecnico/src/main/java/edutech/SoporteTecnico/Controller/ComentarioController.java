package edutech.SoporteTecnico.Controller;

import edutech.SoporteTecnico.Model.Comentario;
import edutech.SoporteTecnico.Services.ComentarioServices;
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
import org.hibernate.Hibernate;

@RestController
@RequestMapping("/api/v1/comentario")
@Tag(name = "Comentario ", description = "API para gestionar comentarios de tickets")
public class ComentarioController {

    @Autowired
    private ComentarioServices comentarioServices;

    @Operation(summary = "Obtener todos los comentarios",
            description = "Retorna una lista de todos los comentarios disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de comentarios encontrada",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Comentario.class, type = "array"))),
        @ApiResponse(responseCode = "204", description = "No hay comentarios disponibles",
                content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Comentario>> listar() {
        List<Comentario> comentarios = comentarioServices.findAll();
        if (comentarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        comentarios.forEach(comentario -> {
            Hibernate.initialize(comentario.getTicket());
        });

        return ResponseEntity.ok(comentarios);
    }

    @Operation(summary = "Obtener comentario por ID",
            description = "Retorna un comentario específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentario encontrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Comentario.class))),
        @ApiResponse(responseCode = "404", description = "Comentario no encontrado",
                content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getComentario(
            @Parameter(description = "ID del comentario", required = true, example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(comentarioServices.findById(id));
    }

    @Operation(summary = "Crear nuevo comentario",
            description = "Crea un nuevo comentario con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comentario.class))),
            @ApiResponse(responseCode = "400", description = "Datos de comentario inválidos",
                    content = @Content)
    })
    @PostMapping("/crear")
    public ResponseEntity<Comentario> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del comentario a crear",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = """
                {
                    "mensaje": "Se procede a resolver el problema reportado",
                    "ticket": {
                        "id_ticket": 1
                    }
                }
                """)))
            @RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioServices.save(comentario));
    }

    @Operation(summary = "Actualizar comentario existente",
            description = "Actualiza un comentario existente basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Comentario.class))),
            @ApiResponse(responseCode = "404", description = "Comentario no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de comentario inválidos",
                    content = @Content)
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Comentario> update(
            @Parameter(description = "ID del comentario a actualizar", required = true, example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del comentario",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = """
                {
                    "mensaje": "Actualización: Se ha completado la revisión del caso"
                }
                """)))
            @RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioServices.update(id, comentario));
    }

    @Operation(summary = "Eliminar comentario",
            description = "Elimina un comentario existente basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comentario no encontrado",
                content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public void delete(
            @Parameter(description = "ID del comentario a eliminar", required = true, example = "1")
            @PathVariable Integer id) {
        comentarioServices.delete(id);
    }
}