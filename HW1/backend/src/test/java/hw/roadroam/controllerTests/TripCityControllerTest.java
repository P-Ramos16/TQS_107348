package hw.roadroam.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import hw.roadroam.controllers.TripController;
import hw.roadroam.models.City;
import hw.roadroam.services.CityService;
import hw.roadroam.services.CurrencyService;
import hw.roadroam.services.TripService;
import hw.roadroam.services.RouteService;
import hw.roadroam.services.TicketService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TripController.class)
class TripCityControllerTest {

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

    private City city0 = new City();
    private City city1 = new City();

    @BeforeEach
    public void setUp() throws Exception {
        //  Create two cities
        city0.setId(1L);
        city0.setName("Aveiro");

        city1.setId(2L);
        city1.setName("Lisboa");
        

        when(cityService.save(Mockito.any())).thenReturn(city0);
        when(cityService.listCities()).thenReturn(List.of(city0, city1));
    }
    @Test
    void whenPostValidCity_thenCreateCity() throws Exception {
        mvc.perform(
                post("/trips/addCity").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(city0)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(city0.getName())));

        verify(cityService, times(1)).save(Mockito.any());

    }

    @Test
    void givenTwoCities_thenReturnThem() throws Exception {

        mvc.perform(
                get("/trips/listCity").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(city0.getName())))
                .andExpect(jsonPath("$[1].name", is(city1.getName())));

        verify(cityService, times(1)).listCities();
    }
}