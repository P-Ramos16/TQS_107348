package hw1.roadroam.services;

import org.springframework.stereotype.Service;

import hw1.roadroam.models.Ticket;
import hw1.roadroam.repositories.TicketRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    final TicketRepo ticketRepository;

    public CityService(TicketRepo ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket save(Ticket c) {
        return ticketRepository.save(c);
    }

    public List<Ticket> listCurrencies() {
        return ticketRepository.findAll();
    }

    public Ticket getTicket(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            return ticket.get();
        }
        else {
            return null;
        }
    }
}
