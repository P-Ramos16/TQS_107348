package hw1.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hw1.roadroam.models.Ticket;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends JpaRepository<Ticket, Long> {

    public Optional<Ticket> findById(Long id);

    public Ticket findByModel(String model);

    public List<Ticket> findAll();

}
