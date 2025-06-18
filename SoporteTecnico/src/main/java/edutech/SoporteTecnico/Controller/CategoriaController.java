package edutech.SoporteTecnico.Controller;

import edutech.SoporteTecnico.Model.Categoria;
import edutech.SoporteTecnico.Services.CategoriaServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")
@Tag(name = "Categoria ", description = "API para gestionar categorías de tickets")
public class CategoriaController {

    @Autowired
    CategoriaServices categoriaServices;

    @Operation(summary = "Obtener todas las categorías",
            description = "Retorna una lista de todas las categorías disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías encontrada",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class, type = "array"))),
        @ApiResponse(responseCode = "404", description = "No hay categorías disponibles",
                content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){
        List<Categoria> categorias = categoriaServices.findAll();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Obtener categoría por ID",
            description = "Retorna una categoría específica basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class))),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoria(
            @Parameter(description = "ID de la categoría", required = true, example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(categoriaServices.findById(id));
    }
}