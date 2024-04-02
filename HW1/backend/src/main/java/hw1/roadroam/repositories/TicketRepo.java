package hw1.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hw1.roadroam.models.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepo extends JpaRepository<Ticket, Long> {

    public Optional<Ticket> findById(Long id);

    public List<Ticket> findAll();

}