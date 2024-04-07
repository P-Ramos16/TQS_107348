package hw1.roadroam.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import hw1.roadroam.models.Ticket;
import hw1.roadroam.models.Trip;
import hw1.roadroam.repositories.TicketRepo;
import hw1.roadroam.services.TicketService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock(lenient = true)
    private TicketRepo ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Trip t0 = new Trip();
    private Trip t1 = new Trip();
    private Ticket ticket0 = new Ticket();
    private Ticket ticket1 = new Ticket();

    @BeforeEach
    public void setUp() {

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
        ticket0.setTrip(t0);

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
        ticket1.setTrip(t1);

        List<Ticket> allTickets = Arrays.asList(ticket0, ticket1);

        Mockito.when(ticketRepository.save(ticket0)).thenReturn(ticket0);
        Mockito.when(ticketRepository.save(ticket1)).thenReturn(ticket1);
        Mockito.when(ticketRepository.findAll()).thenReturn(allTickets);
        Mockito.when(ticketRepository.findById(ticket0.getId())).thenReturn(Optional.of(ticket0));
        Mockito.when(ticketRepository.findById(ticket1.getId())).thenReturn(Optional.of(ticket1));
        Mockito.when(ticketRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
     void whenSaveValidTicket_thenTicketShouldBeReturned() {
        Ticket returned = ticketService.save(ticket0);
        assertThat(returned.getFirstname()).isEqualTo(ticket0.getFirstname());

        returned = ticketService.save(ticket1);
        assertThat(returned.getFirstname()).isEqualTo(ticket1.getFirstname());
    }

    @Test
     void whenSearchValidID_thenTicketshouldBeFound() {
        Ticket found = ticketService.getTicket(ticket0.getId());
        assertThat(found.getPhone()).isEqualTo(ticket0.getPhone());

        found = ticketService.getTicket(ticket1.getId());
        assertThat(found.getPhone()).isEqualTo(ticket1.getPhone());
    }

    @Test
     void whenSearchInvalidID_thenTicketShouldNotBeFound() {
        Ticket fromDb = ticketService.getTicket(-99L);
        assertThat(fromDb).isNull();

        Mockito.verify(ticketRepository, 
                VerificationModeFactory.times(1))
                    .findById(-99L);
    }

    @Test
     void given2Tickets_whengetAll_thenReturn3Records() {

        List<Ticket> allTickets = ticketService.listTickets();

        assertThat(allTickets).hasSize(2).extracting(Ticket::getFirstname).contains(ticket0.getFirstname(), ticket1.getFirstname());

        Mockito.verify(ticketRepository, 
                VerificationModeFactory.times(1))
                    .findAll();
    }
}
