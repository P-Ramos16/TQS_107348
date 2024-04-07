package hw1.roadroam.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import hw1.roadroam.controllers.TicketController;
import hw1.roadroam.models.Ticket;
import hw1.roadroam.models.City;
import hw1.roadroam.models.Currency;
import hw1.roadroam.models.Trip;
import hw1.roadroam.models.Route;
import hw1.roadroam.services.CityService;
import hw1.roadroam.services.CurrencyService;
import hw1.roadroam.services.TicketService;
import hw1.roadroam.services.TripService;
import hw1.roadroam.services.RouteService;

import java.util.Arrays;
import java.util.List;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TicketService service;
    @MockBean
    private TripService tripService;
    @MockBean
    private CurrencyService currencyService;
    @MockBean
    private CityService cityService;
    @MockBean
    private RouteService routeService;

    private City c0 = new City();
    private City c1 = new City();
    private Route r0 = new Route();
    private Trip t0 = new Trip();
    private Ticket ticket0 = new Ticket();
    private Currency curr0 = new Currency();

    @BeforeEach
    public void setUp() throws Exception {
        //  Create two cities
        c0.setId(1L);
        c0.setName("Aveiro");
        c1.setId(2L);
        c1.setName("Leiria");

        //  Create a route
        r0.setId(1L);
        r0.setOrigin(c0);
        r0.setDestination(c1);

        //  Create a trip
        t0.setId(1L);
        t0.setBusNumber(16);
        t0.setBasePrice(12.91);
        t0.setNumberOfSeatsTotal(6);
        t0.setNumberOfSeatsAvailable(2);
        t0.setTripLengthKm("120km");
        t0.setTripLengthTime("1h2m");
        t0.setDate(null);
        t0.setFilledSeats(new HashSet<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }});

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

        //  Create a currency
        curr0.setId(1L);
        curr0.setAbreviation("EUR");
        curr0.setExchange_rate(2.0);
        

        when(service.save(Mockito.any())).thenReturn(ticket0);
        when(service.listTickets()).thenReturn(List.of(ticket0));
        when(service.getTicket(1L)).thenReturn(ticket0);

        when(tripService.getTrip(Mockito.any())).thenReturn(t0);
        when(currencyService.getCurrency(Mockito.any())).thenReturn(curr0);

    }

    @Test
    void whenPostValidTicket_thenCreateTicket() throws Exception {
        mvc.perform(
                post("/tickets/buy").contentType(MediaType.APPLICATION_JSON)
                .param("firstname", ticket0.getFirstname())
                .param("lastname", ticket0.getLastname())
                .param("phone", ticket0.getPhone())
                .param("email", ticket0.getEmail())
                .param("creditCard", ticket0.getCreditCard())
                .param("numberOfPeople", ticket0.getNumberOfPeople().toString())
                .param("seatNumber", ticket0.getSeatNumber().toString())
                .param("trip", ticket0.getTrip().getId().toString())
                .param("currency", ticket0.getCurrency()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPrice", is(25.82)));

        verify(service, times(1)).save(Mockito.any());

    }

    @Test
    void givenOneTickets_thenReturnIt() throws Exception {

        mvc.perform(
                get("/tickets/get/" + ticket0.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPrice", is(ticket0.getFinalPrice())));

        verify(service, times(1)).getTicket(Mockito.any());
    }

    @Test
    void givenManyTickets_thenReturnInJsonArray() throws Exception {
        Ticket ticket0 = new Ticket();
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        ticket0.setFirstname("José");
        ticket1.setFirstname("Maria");
        ticket2.setFirstname("Rodrigo");
        ticket0.setFinalPrice(10.72);
        ticket1.setFinalPrice(16.98);
        ticket2.setFinalPrice(23.53);

        List<Ticket> allTickets = Arrays.asList(ticket0, ticket1, ticket2);

        when(service.listTickets()).thenReturn(allTickets);

        mvc.perform(
                get("/tickets/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].finalPrice", is(ticket0.getFinalPrice())))
                .andExpect(jsonPath("$[1].finalPrice", is(ticket1.getFinalPrice())))
                .andExpect(jsonPath("$[2].finalPrice", is(ticket2.getFinalPrice())));

        verify(service, times(1)).listTickets();
    }

    @Test
    void givenOneTickets_thenReturnTheCurrectPrice() throws Exception {

        mvc.perform(
                get("/tickets/getPrice").contentType(MediaType.APPLICATION_JSON)
                .param("trip", ticket0.getTrip().getId().toString())
                .param("numpeople", ticket0.getNumberOfPeople().toString())
                .param("currency", ticket0.getCurrency()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(ticket0.getFinalPrice())));

        verify(tripService, times(1)).getTrip(Mockito.any());
    }
}