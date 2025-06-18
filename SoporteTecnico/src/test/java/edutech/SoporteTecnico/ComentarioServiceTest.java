package edutech.SoporteTecnico;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edutech.SoporteTecnico.Model.Comentario;
import edutech.SoporteTecnico.Model.Ticket;
import edutech.SoporteTecnico.Repository.ComentarioRepository;
import edutech.SoporteTecnico.Repository.TicketRepository;
import edutech.SoporteTecnico.Services.ComentarioServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.*;


class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private ComentarioServices comentarioServices;

    private Comentario comentario;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ticket = new Ticket();
        ticket.setId_ticket(1);
        ticket.setEstado("En espera");

        comentario = new Comentario();
        comentario.setId_comentario(100);
        comentario.setMensaje("Todo está funcionando bien");
        comentario.setTicket(ticket);
    }

    @Test
    void testFindAll() {
        when(comentarioRepository.findAll()).thenReturn(Arrays.asList(comentario));

        List<Comentario> comentarios = comentarioServices.findAll();

        assertEquals(1, comentarios.size());
        verify(comentarioRepository).findAll();
    }

    @Test
    void testFindById() {
        when(comentarioRepository.findById(100)).thenReturn(Optional.of(comentario));

        Comentario found = comentarioServices.findById(100);

        assertNotNull(found);
        assertEquals("Todo está funcionando bien", found.getMensaje());
    }

    @Test
    void testSave() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        when(comentarioRepository.save(any(Comentario.class))).thenAnswer(i -> i.getArgument(0));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setMensaje("Revisado y resuelto");
        nuevoComentario.setTicket(ticket);

        Comentario saved = comentarioServices.save(nuevoComentario);

        assertNotNull(saved.getFecha());
        assertEquals("Resuelto", saved.getTicket().getEstado());
        verify(ticketRepository).findById(1);
        verify(ticketRepository).save(ticket);
        verify(comentarioRepository).save(saved);
    }



    @Test
    void testDelete() {
        doNothing().when(comentarioRepository).deleteById(100);

        comentarioServices.delete(100);

        verify(comentarioRepository).deleteById(100);
    }

    @Test
    void testUpdate() {
        when(comentarioRepository.findById(100)).thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any(Comentario.class))).thenAnswer(i -> i.getArgument(0));

        Comentario actualizacion = new Comentario();
        actualizacion.setMensaje("Mensaje actualizado");

        Comentario updated = comentarioServices.update(100, actualizacion);

        assertEquals("Mensaje actualizado", updated.getMensaje());
        verify(comentarioRepository).save(updated);
    }


}
