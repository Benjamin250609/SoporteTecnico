package edutech.SoporteTecnico;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edutech.SoporteTecnico.Model.Categoria;
import edutech.SoporteTecnico.Model.Ticket;
import edutech.SoporteTecnico.Repository.CategoriaRepository;
import edutech.SoporteTecnico.Repository.TicketRepository;
import edutech.SoporteTecnico.Services.TicketServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;


class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TicketServices ticketServices;

    private Ticket ticket;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new Categoria();
        categoria.setId_categoria(1);

        ticket = new Ticket();
        ticket.setId_ticket(100);
        ticket.setTitulo("Problema con PC");
        ticket.setMensaje("No enciende");
        ticket.setEstado("En espera");
        ticket.setRun("12.345.678-9");
        ticket.setCategoria(categoria);
        ticket.setFecha_creacion(LocalDateTime.now());
    }

    @Test
    void testFindAll() {
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket));

        List<Ticket> tickets = ticketServices.findAll();

        assertEquals(1, tickets.size());
        verify(ticketRepository).findAll();
    }

    @Test
    void testFindById() {
        when(ticketRepository.findById(100)).thenReturn(Optional.of(ticket));

        Ticket found = ticketServices.findById(100);

        assertNotNull(found);
        assertEquals("Problema con PC", found.getTitulo());
    }



    @Test
    void testSave() {
        when(categoriaRepository.findCategoriaById(1)).thenReturn(categoria);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));

        Ticket nuevoTicket = new Ticket();
        nuevoTicket.setCategoria(categoria);
        nuevoTicket.setTitulo("Nuevo problema");
        nuevoTicket.setMensaje("Mensaje del problema");
        nuevoTicket.setRun("12.345.678-9");

        Ticket saved = ticketServices.save(nuevoTicket);

        assertNotNull(saved.getFecha_creacion());
        assertEquals("En espera", saved.getEstado());
        verify(categoriaRepository).findCategoriaById(1);
        verify(ticketRepository).save(saved);
    }

    @Test
    void testDelete() {
        doNothing().when(ticketRepository).deleteById(100);

        ticketServices.delete(100);

        verify(ticketRepository).deleteById(100);
    }

    @Test
    void testUpdate() {
        when(ticketRepository.findById(100)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));

        Ticket updates = new Ticket();
        updates.setTitulo("Actualizado");
        updates.setMensaje("Mensaje actualizado");
        updates.setEstado("Resuelto");
        updates.setRun("12.345.678-9");

        Ticket updated = ticketServices.update(100, updates);

        assertEquals("Actualizado", updated.getTitulo());
        assertEquals("Resuelto", updated.getEstado());
    }


    @Test
    void testUsuarioTicket_EstudianteExistenteConTickets() {
        String run = "12.345.678-9";
        String estudianteJson = "{\"run\":\"12.345.678-9\",\"nombre\":\"Juan\"}";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(estudianteJson);
        when(ticketRepository.findAllByRun(run)).thenReturn(Arrays.asList(ticket));

        String resultado = ticketServices.usuarioTicket(run);

        assertTrue(resultado.contains("INFORMACIÓN DEL ESTUDIANTE"));
        assertTrue(resultado.contains("TICKETS DEL ESTUDIANTE"));
        assertTrue(resultado.contains("Problema con PC"));
    }

}
