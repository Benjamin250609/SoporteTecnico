package edutech.SoporteTecnico.Services;

import edutech.SoporteTecnico.Model.Comentario;
import edutech.SoporteTecnico.Repository.ComentarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import edutech.SoporteTecnico.Model.Ticket;
import edutech.SoporteTecnico.Repository.TicketRepository;

@Service
@Transactional
public class ComentarioServices {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    private TicketRepository ticketRepository;


    public List<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    public Comentario findById(Integer id) {
        return comentarioRepository.findById(id).orElse(null);
    }

public Comentario save(Comentario comentario) {
    Integer ticketId = comentario.getTicket().getId_ticket();
    comentario.setFecha(LocalDateTime.now());
    if (ticketId == null) {
        throw new RuntimeException("El ID del ticket no puede ser nulo");
    }

    Ticket ticketCompleto = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new RuntimeException("El ticket no existe"));

    ticketCompleto.setEstado("Resuelto");
    ticketRepository.save(ticketCompleto);

    comentario.setTicket(ticketCompleto);
    return comentarioRepository.save(comentario);
}

    public void delete(Integer id) {
        comentarioRepository.deleteById(id);
    }

    public Comentario update(Integer id_comentario, Comentario comentario) {
        Comentario comentarioUpdate = comentarioRepository.findById(id_comentario)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        comentarioUpdate.setMensaje(comentario.getMensaje());
        return comentarioRepository.save(comentarioUpdate);
    }





}