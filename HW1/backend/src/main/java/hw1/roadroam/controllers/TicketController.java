package hw1.roadroam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hw1.roadroam.models.Ticket;
import hw1.roadroam.services.TicketService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService service) {
        this.ticketService = service;
    }

    @PostMapping("/buy") public ResponseEntity<Ticket> createTicket(@RequestBody Ticket oneTicket) {
        HttpStatus status = HttpStatus.CREATED;
        Ticket saved = ticketService.save(oneTicket);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/list",  produces = "application/json")
    public List<Ticket> getAllTickets() {
        return ticketService.listTickets();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable(value = "id") Long id) {
        Ticket ticket = ticketService.getTicket(id);
        
        if (ticket == null) {
            return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok().body(ticket);
    }

}
