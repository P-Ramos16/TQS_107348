package hw.roadroam.frontendTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import hw.roadroam.models.Ticket;
import hw.roadroam.repositories.CarRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
// @TestPropertySource( locations = "application-integrationtest.properties")
class IntegrationTest {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepo repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Test
     void whenValidInput_thenCreateCar() {
        Ticket bmw = new Ticket("bmw", "225xe");
        ResponseEntity<Ticket> entity = restTemplate.postForEntity("/car/add", bmw, Ticket.class);

        List<Ticket> found = repository.findAll();
        assertThat(found).extracting(Ticket::getModel).containsOnly("225xe");
    }

    @Test
     void givenCars_whenGetCars_thenStatus200()  {
        createTestCar("bmw", "225xe");
        createTestCar("audi", "quattro");


        ResponseEntity<List<Ticket>> response = restTemplate
                .exchange("/car/list", HttpMethod.GET, null, new ParameterizedTypeReference<List<Ticket>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Ticket::getModel).containsExactly("225xe", "quattro");

    }


    private void createTestCar(String name, String email) {
        Ticket emp = new Ticket(name, email);
        repository.saveAndFlush(emp);
    }

}
