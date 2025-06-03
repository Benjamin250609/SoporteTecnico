package edutech.SoporteTecnico.Controller;


import edutech.SoporteTecnico.Model.Categoria;
import edutech.SoporteTecnico.Services.CategoriaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {

    @Autowired
    CategoriaServices categoriaServices;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){
        List<Categoria>  categorias = categoriaServices.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoria(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaServices.findById(id));
    }

}
