package edutech.SoporteTecnico.Services;

import edutech.SoporteTecnico.Model.Categoria;
import edutech.SoporteTecnico.Model.Ticket;
import edutech.SoporteTecnico.Repository.CategoriaRepository;
import edutech.SoporteTecnico.Repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TicketServices {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private RestTemplate restTemplate;


    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Integer id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Ticket save(Ticket ticket) {

        Categoria categoria = categoriaRepository.findCategoriaById(ticket.getCategoria().getId_categoria());
        ticket.setCategoria(categoria);


        ticket.setFecha_creacion(LocalDateTime.now());
        ticket.setEstado("En espera");
        return ticketRepository.save(ticket);


    }


    public void delete(Integer id) {
        ticketRepository.deleteById(id);
    }

    public Ticket update(Integer id_ticket, Ticket ticket) {
        Ticket ticketUpdate = ticketRepository.findById(id_ticket)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        ticketUpdate.setTitulo(ticket.getTitulo());
        ticketUpdate.setMensaje(ticket.getMensaje());
        ticketUpdate.setEstado(ticket.getEstado());
        ticketUpdate.setRun(ticket.getRun());
        
        return ticketRepository.save(ticketUpdate);
    }

    public String usuarioTicket(String run) {
        String estudianteURL = "http://localhost:8081/api/v1/estudiantes/EstudianteDTO/" + run;
        String estudianteData = restTemplate.getForObject(estudianteURL, String.class);
        List<Ticket> tickets = ticketRepository.findAllByRun(run);

        if (estudianteData == null) {
            return "El estudiante no existe.";
        } else {
            StringBuilder resultado = new StringBuilder();
            resultado.append("=== INFORMACIÓN DEL ESTUDIANTE ===\n");
            resultado.append(estudianteData).append("\n\n");
            resultado.append("=== TICKETS DEL ESTUDIANTE ===\n");

        for (Ticket t : tickets) {
            resultado.append("\n- Ticket ID: ").append(t.getId_ticket())
                    .append("\n  Título: ").append(t.getTitulo())
                    .append("\n  Mensaje: ").append(t.getMensaje())
                    .append("\n  Estado: ").append(t.getEstado())
                    .append("\n  Categoría: ").append(t.getCategoria())
                    .append("\n  Fecha: ").append(t.getFecha_creacion()).append("\n");
        }
        
        return resultado.toString();
        }
    }
}