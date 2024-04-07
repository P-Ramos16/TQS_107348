package hw1.roadroam.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import hw1.roadroam.controllers.TripController;
import hw1.roadroam.models.City;
import hw1.roadroam.models.Route;
import hw1.roadroam.models.Trip;
import hw1.roadroam.models.Ticket;
import hw1.roadroam.models.Currency;
import hw1.roadroam.services.CityService;
import hw1.roadroam.services.CurrencyService;
import hw1.roadroam.services.TripService;
import hw1.roadroam.services.RouteService;
import hw1.roadroam.services.TicketService;

import java.util.List;
import java.util.HashSet;
import java.util.Arrays;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TripController.class)
class TripControllerTest {

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
    private Trip trip0 = new Trip();
    private Trip trip1 = new Trip();
    private Currency cur0 = new Currency();

    @BeforeEach
    public void setUp() throws Exception {
        //  Create one currency
        cur0.setId(1L);
        cur0.setAbreviation("EUR");
        cur0.setExchange_rate(2.0);
        
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
        trip0.setId(1L);
        trip0.setBusNumber(16);
        trip0.setBasePrice(12.91);
        trip0.setNumberOfSeatsTotal(6);
        trip0.setNumberOfSeatsAvailable(2);
        trip0.setTripLengthKm("120km");
        trip0.setTripLengthTime("1h2m");
        trip0.setDate(null);
        trip0.setFilledSeats(new HashSet<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }});
        trip0.setRoute(r0);

        //  Create a trip
        trip1.setId(2L);
        trip1.setBusNumber(18);
        trip1.setBasePrice(8.12);
        trip1.setNumberOfSeatsTotal(8);
        trip1.setNumberOfSeatsAvailable(6);
        trip1.setTripLengthKm("25km");
        trip1.setTripLengthTime("47m");
        trip1.setDate(null);
        trip1.setFilledSeats(new HashSet<Integer>() {{
            add(2);
            add(5);
        }});
        trip1.setRoute(r0);

        when(tripService.save(Mockito.any())).thenReturn(trip0);
        when(tripService.getTrip(trip0.getId())).thenReturn(trip0);
        when(tripService.getTrip(trip1.getId())).thenReturn(trip1);
        when(tripService.listTrips()).thenReturn(List.of(trip0, trip1));
        when(tripService.listTripsByRouteID(1L)).thenReturn(List.of(trip0, trip1));

        when(routeService.getRoute(1L)).thenReturn(r0);
        when(currencyService.getCurrency("EUR")).thenReturn(cur0);
    }

    @Test
    void whenPostValidTrip_thenCreateTrip() throws Exception {
        mvc.perform(
                post("/trips/add").contentType(MediaType.APPLICATION_JSON)
                .param("numberOfSeatsAvailable", trip0.getNumberOfSeatsAvailable().toString())
                .param("numberOfSeatsTotal", trip0.getNumberOfSeatsTotal().toString())
                .param("tripLengthTime", trip0.getTripLengthTime())
                .param("tripLengthKm", trip0.getTripLengthKm())
                .param("date", "2-4-2024")
                .param("time", "19:22:59")
                .param("busNumber", trip0.getBusNumber().toString())
                .param("basePrice", trip0.getBasePrice().toString())
                .param("route", trip0.getRoute().getId().toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tripLengthTime", is(trip0.getTripLengthTime())));

        verify(tripService, times(1)).save(Mockito.any());

    }

    @Test
    void givenOneTrips_thenReturnIt() throws Exception {

        mvc.perform(
                get("/trips/get/" + trip0.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basePrice", is(trip0.getBasePrice())));

        verify(tripService, times(1)).getTrip(Mockito.any());
    }

    @Test
    void givenManyTrips_thenReturnInJsonArray() throws Exception {
        Trip trip0 = new Trip();
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();

        trip0.setTripLengthKm("11km");
        trip1.setTripLengthKm("26km");
        trip2.setTripLengthKm("38km");
        trip0.setTripLengthTime("10m");
        trip1.setTripLengthTime("1h");
        trip2.setTripLengthTime("1h2m");

        List<Trip> allTrips = Arrays.asList(trip0, trip1, trip2);

        when(tripService.listTrips()).thenReturn(allTrips);

        mvc.perform(
                get("/trips/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].tripLengthKm", is(trip0.getTripLengthKm())))
                .andExpect(jsonPath("$[1].tripLengthKm", is(trip1.getTripLengthKm())))
                .andExpect(jsonPath("$[2].tripLengthKm", is(trip2.getTripLengthKm())))
                .andExpect(jsonPath("$[0].tripLengthTime", is(trip0.getTripLengthTime())))
                .andExpect(jsonPath("$[1].tripLengthTime", is(trip1.getTripLengthTime())))
                .andExpect(jsonPath("$[2].tripLengthTime", is(trip2.getTripLengthTime())));

        verify(tripService, times(1)).listTrips();
    }

    @Test
    void givenSomeTrips_thenReturnTheTripsByRouteAndSetCurrency() throws Exception {

        mvc.perform(
                get("/trips/listByRoute").contentType(MediaType.APPLICATION_JSON)
                .param("route", trip0.getRoute().getId().toString())
                .param("currency", "EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].basePrice", is(trip0.getBasePrice())))
                .andExpect(jsonPath("$[1].basePrice", is(trip1.getBasePrice())));

        verify(currencyService, times(1)).getCurrency("EUR");
        verify(tripService, times(1)).listTripsByRouteID(Mockito.any());
    }
}