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
import hw1.roadroam.services.CityService;
import hw1.roadroam.services.CurrencyService;
import hw1.roadroam.services.TripService;
import hw1.roadroam.services.RouteService;
import hw1.roadroam.services.TicketService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TripController.class)
class TripRouteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TicketService ticketService;
    @MockBean
    private TripService tripService;
    @MockBean
    private CurrencyService currencyService;
    @MockBean
    private CityService cityService;
    @MockBean
    private RouteService routeService;

    private Route route0 = new Route();
    private Route route1 = new Route();

    private City city0 = new City();
    private City city1 = new City();
    private City city2 = new City();

    @BeforeEach
    public void setUp() throws Exception {        
        //  Create three cities
        city0.setId(1L);
        city0.setName("Aveiro");

        city1.setId(2L);
        city1.setName("Lisboa");

        city2.setId(3L);
        city2.setName("Leiria");

        //  Create two routes
        route0.setId(1L);
        route0.setOrigin(city0);
        route0.setDestination(city2);

        route1.setId(2L);
        route1.setOrigin(city1);
        route1.setDestination(city2);

        when(routeService.save(Mockito.any())).thenReturn(route0);
        when(routeService.listRoutes()).thenReturn(List.of(route0, route1));
        when(routeService.listRoutesByOriginCity(city0.getId())).thenReturn(List.of(route0));
        when(routeService.listRoutesByOriginCity(city1.getId())).thenReturn(List.of(route1));

        when(cityService.getCity(1L)).thenReturn(city0);
        when(cityService.getCity(2L)).thenReturn(city1);
        when(cityService.getCity(3L)).thenReturn(city2);
    }

    @Test
    void whenPostValidRoute_thenCreateRoute() throws Exception {
        mvc.perform(
                post("/trips/addRoute").contentType(MediaType.APPLICATION_JSON)
                .param("origin", route0.getOrigin().getId().toString())
                .param("destination", route0.getDestination().getId().toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destination.name", is(route0.getDestination().getName())));

        verify(cityService, times(2)).getCity(Mockito.any());
        verify(routeService, times(1)).save(Mockito.any());
    }

    @Test
    void givenTwoRoutes_returnAllTheCitiesThatAreOriginOfAtleastOne() throws Exception {

        mvc.perform(
                get("/trips/listRouteOrigins").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(city0.getName())))
                .andExpect(jsonPath("$[1].name", is(city1.getName())));

        verify(routeService, times(1)).listRoutes();
    }

    @Test
    void givenAOriginCity_returnAllTheRoutesWithTheSameOrigin() throws Exception {

        mvc.perform(
                get("/trips/listRouteDestinationsByOrigin").contentType(MediaType.APPLICATION_JSON)
                .param("origin", route0.getOrigin().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(city2.getName())));

        mvc.perform(
                get("/trips/listRouteDestinationsByOrigin").contentType(MediaType.APPLICATION_JSON)
                .param("origin", route1.getOrigin().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(city2.getName())));

        verify(routeService, times(1)).listRoutesByOriginCity(route0.getOrigin().getId());
        verify(routeService, times(1)).listRoutesByOriginCity(route1.getOrigin().getId());
    }
}