package edutech.SoporteTecnico.Repository;

import edutech.SoporteTecnico.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("SELECT t FROM Ticket t WHERE t.run = ?1")
    public List<Ticket> findAllByRun(String run);
}