package edutech.SoporteTecnico.Assembler;

import edutech.SoporteTecnico.Controller.TicketControllerV2;
import edutech.SoporteTecnico.Model.Ticket;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TicketModelAssembler implements RepresentationModelAssembler<Ticket, EntityModel<Ticket>> {

    @Override
    public EntityModel<Ticket> toModel(Ticket ticket) {
        return EntityModel.of(ticket,
                linkTo(methodOn(TicketControllerV2.class).getTicket(ticket.getId_ticket())).withSelfRel(),
                linkTo(methodOn(TicketControllerV2.class).listar()).withRel("todos-los-tickets"),
                linkTo(methodOn(TicketControllerV2.class).delete(ticket.getId_ticket())).withRel("eliminar")
        );
    }
}