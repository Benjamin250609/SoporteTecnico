package edutech.SoporteTecnico.Controller;

import edutech.SoporteTecnico.Model.Ticket;
import edutech.SoporteTecnico.Services.TicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    @Autowired
    private TicketServices ticketServices;


    @GetMapping
    public ResponseEntity<List<Ticket>> listar() {
        List<Ticket> tickets = ticketServices.findAll();
        if (tickets.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketServices.findById(id));
    }


    @PostMapping("/crear")
    public ResponseEntity<Ticket> save(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketServices.save(ticket));
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Ticket> update(@PathVariable Integer id, @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketServices.update(id, ticket));
    }


    @DeleteMapping("/eliminar/{id}")
    public void delete(@PathVariable Integer id) {
        ticketServices.delete(id);
    }
}