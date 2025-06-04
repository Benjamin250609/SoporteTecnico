package edutech.SoporteTecnico.Controller;

import edutech.SoporteTecnico.Model.Comentario;
import edutech.SoporteTecnico.Services.ComentarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.hibernate.Hibernate;


@RestController
@RequestMapping("/api/v1/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioServices comentarioServices;


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

    @GetMapping("/{id}")
    public ResponseEntity<?> getComentario(@PathVariable Integer id) {
        return ResponseEntity.ok(comentarioServices.findById(id));
    }


    @PostMapping("/crear")
    public ResponseEntity<Comentario> save(@RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioServices.save(comentario));
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Comentario> update(@PathVariable Integer id, @RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioServices.update(id, comentario));
    }


    @DeleteMapping("/eliminar/{id}")
    public void delete(@PathVariable Integer id) {
        comentarioServices.delete(id);
    }
}