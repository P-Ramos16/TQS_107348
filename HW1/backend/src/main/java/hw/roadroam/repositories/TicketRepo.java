package hw.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hw.roadroam.models.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

    public Optional<Ticket> findById(Long id);

    public List<Ticket> findAll();

}
