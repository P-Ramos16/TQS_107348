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
import hw1.roadroam.models.Currency;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TripController.class)
class TripCurrencyControllerTest {

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

    private Currency curr0 = new Currency();
    private Currency curr1 = new Currency();

    @BeforeEach
    public void setUp() throws Exception {
        //  Create two currencies
        curr0.setId(1L);
        curr0.setAbreviation("EUR");
        curr0.setExchange_rate(2.0);

        curr1.setId(2L);
        curr1.setAbreviation("AMD");
        curr1.setExchange_rate(1.5);
        

        when(currencyService.listCurrencies()).thenReturn(List.of(curr0, curr1));
        when(currencyService.getCurrency("EUR")).thenReturn(curr0);
        when(currencyService.getCurrency("AMD")).thenReturn(curr1);
    }

    @Test
    void givenTwoCurrencies_thenReturnThem() throws Exception {

        mvc.perform(
                get("/trips/listCurrencies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].exchange_rate", is(curr0.getExchange_rate())))
                .andExpect(jsonPath("$[1].exchange_rate", is(curr1.getExchange_rate())));

        verify(currencyService, times(1)).listCurrencies();
    }

    @Test
    void givenTheAbreviation_thenReturnTheCorrectCurrency() throws Exception {
        mvc.perform(
                get("/trips/getCurrency/" + curr0.getAbreviation()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exchange_rate", is(2.0)));

        verify(currencyService, times(1)).getCurrency(Mockito.any());

    }
}