package hw.roadroam.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import hw.roadroam.controllers.TripController;
import hw.roadroam.services.CurrencyService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TripController.class)
class CacheControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyService currencyService;


    @BeforeEach
    public void setUp() throws Exception {
        when(currencyService.getCacheHits()).thenReturn(1);
        when(currencyService.getCacheMisses()).thenReturn(2);
    }
    @Test
    void whenPostValidCity_thenCreateCity() throws Exception {
        mvc.perform(
                post("/cache/getHits").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.", is(1)))
                .andExpect(status().isOk());

        verify(currencyService, times(1)).getCacheHits();

    }

    @Test
    void givenTwoCities_thenReturnThem() throws Exception {
        mvc.perform(
                post("/cache/getMisses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.", is(2)))
                .andExpect(status().isOk());

        verify(currencyService, times(1)).getCacheHits();
    }
}