package hw1.roadroam;

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
import hw1.roadroam.services.CarService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;


    @BeforeEach
    public void setUp() throws Exception {}

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Ticket c0 = new Ticket("bmw", "323i");

        when(service.save(Mockito.any())).thenReturn(c0);

        mvc.perform(
                post("/car/add").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(c0)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("bmw")));

        verify(service, times(1)).save(Mockito.any());

    }

    @Test
    void givenManyCars_thenReturnInJsonArray() throws Exception {
        Ticket audi = new Ticket("audi", "quatro");
        Ticket renault = new Ticket("renault", "laguna");
        Ticket mazda = new Ticket("mazda", "mx-3");

        List<Ticket> allCars = Arrays.asList(audi, renault, mazda);

        when(service.listCars()).thenReturn(allCars);

        mvc.perform(
                get("/car/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(audi.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(renault.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(mazda.getMaker())));

        verify(service, times(1)).listCars();
    }
}