package tqs.ex4;

import tqs.ex4.models.Car;
import tqs.ex4.repositories.CarRepo;
import tqs.ex4.controllers.CarController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepo repository;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:14")
      .withUsername("ex4")
      .withPassword("TQS_Pass")
      .withDatabaseName("test");
  
    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }


    @BeforeEach
    public void setUp() throws Exception {}
    
    @Test
    @Order(1)
    void whenValidInput_thenCreateCar() throws Exception {
        Car c0 = new Car("AUDI", "A3");

        String url = "/car/add";

        RestAssuredMockMvc
            .given().mockMvc(mvc).body(c0).when().
                post(url);

        mvc.perform(
                post("/car/add").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(c0)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("AUDI")));
    }

    @Test
    @Order(2)
    void givenManyCars_thenReturnInJsonArray() throws Exception {
        String url = "/car/list";

        Car[] response = RestAssuredMockMvc
            .given().mockMvc(mvc).when().
                get(url).then().
                    log().all().
                    statusCode(HttpStatus.OK.value()).
                    body("$.size()", equalTo(4)).
                    body("[0].maker", equalTo("BMW")).
                    body("[1].maker", equalTo("Honda")).
                    body("[2].maker", equalTo("Suzuki")).
                    body("[3].maker", equalTo("AUDI")).
                    body("[0].model", equalTo("240i")).
                    body("[1].model", equalTo("Civic")).
                    body("[2].model", equalTo("Swift")).
                    body("[3].model", equalTo("A3")).
                    extract().as(Car[].class);
    }
}
