package hw1.roadroam.services;

import org.springframework.stereotype.Service;

import hw1.roadroam.models.Ticket;
import hw1.roadroam.repositories.TicketRepo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepo ticketRepository;
    
    public Ticket save(Ticket c) {
        return ticketRepository.save(c);
    }

    public List<Ticket> listTickets() {
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
