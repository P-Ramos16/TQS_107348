package hw.roadroam.repositoryTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hw.roadroam.models.Ticket;
import hw.roadroam.repositories.TicketRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepo ticketRepository;

    private Ticket ticket0 = new Ticket();

    @BeforeEach
    public void setUp() throws Exception {

        //  Create a ticket
        ticket0.setId(1L);
        ticket0.setFirstname("Josefino");
        ticket0.setLastname("Calças");
        ticket0.setEmail("jose@fino.com");
        ticket0.setPhone("919767838");
        ticket0.setCreditCard("257626323");
        ticket0.setNumberOfPeople(1);
        ticket0.setFinalPrice(25.82);
        ticket0.setCurrency("AMD");
        ticket0.setSeatNumber(5);
        ticket0.setTrip(null);

        // arrange a new ticket and insert into db

    }

    @Test
    void whenFindTicketById_thenReturnTicket() {
        //ensure data is persisted at this point
        ticketRepository.delete(ticket0);
        ticketRepository.saveAndFlush(ticket0);

        // test the query method of interest
        Ticket found = ticketRepository.findById(ticket0.getId()).get();
        
        assertThat(found).isEqualTo(ticket0);
    }

    @Test
    void whenInvalidTicketId_thenReturnNull() {
        Ticket found = ticketRepository.findById(123L).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void whenTicketIsDeleted_thenReturnNull() {
        //ensure data is persisted at this point
        ticketRepository.saveAndFlush(ticket0);

        ticketRepository.delete(ticket0);

        Ticket found = ticketRepository.findById(ticket0.getId()).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void givenSetOfTickets_whenFindAll_thenReturnAllTickets() {
        //ensure data is persisted at this point
        ticketRepository.saveAndFlush(ticket0);

        Ticket ticket1 = new Ticket();

        //  Create a ticket
        ticket1.setId(2L);
        ticket1.setFirstname("Joana");
        ticket1.setLastname("Saia");
        ticket1.setEmail("jo@ana.com");
        ticket1.setPhone("919111222");
        ticket1.setCreditCard("123765987");
        ticket1.setNumberOfPeople(3);
        ticket1.setFinalPrice(37.21);
        ticket1.setCurrency("EUR");
        ticket1.setSeatNumber(2);
        ticket1.setTrip(null);

        ticketRepository.saveAndFlush(ticket1);

        List<Ticket> allTickets = ticketRepository.findAll();

        assertThat(allTickets).hasSize(2).extracting(Ticket::getFirstname).containsOnly(ticket0.getFirstname(), ticket1.getFirstname());
    }

}