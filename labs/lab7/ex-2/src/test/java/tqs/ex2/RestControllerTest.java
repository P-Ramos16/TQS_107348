package tqs.ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.ex2.controllers.CarController;
import tqs.ex2.models.Car;
import tqs.ex2.services.CarService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(CarController.class)
class RestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;


    @BeforeEach
    public void setUp() throws Exception {}

    @Test
    void whenListCars_thenGetCars() throws Exception {
        RestAssuredMockMvc
            .given()
            .mockMvc( mvc)
            .when().get("/car/list");
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car c0 = new Car("bmw", "323i");

        when(service.save(Mockito.any())).thenReturn(c0);

        String url = "/car/add";

        RestAssuredMockMvc
            .given().mockMvc(mvc).when().
                get(url);


        mvc.perform(
                post("/car/add").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(c0)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("bmw")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_thenReturnInJsonArray() throws Exception {
        Car audi = new Car("audi", "quatro");
        Car renault = new Car("renault", "laguna");
        Car mazda = new Car("mazda", "mx-3");

        String url = "/car/list";

        List<Car> allCars = Arrays.asList(audi, renault, mazda);

        when(service.listCars()).thenReturn(allCars);


        Car[] response = RestAssuredMockMvc
            .given().mockMvc(mvc).when().
                get(url).then().
                    log().all().
                    statusCode(HttpStatus.OK.value()).
                    body("$.size()", equalTo(3)).
                    body("[0].maker", equalTo(audi.getMaker())).
                    body("[1].maker", equalTo(renault.getMaker())).
                    body("[2].maker", equalTo(mazda.getMaker())).
                    body("[0].model", equalTo(audi.getModel())).
                    body("[1].model", equalTo(renault.getModel())).
                    body("[2].model", equalTo(mazda.getModel())).
                    extract().as(Car[].class);

        verify(service, times(1)).listCars();
    }
}