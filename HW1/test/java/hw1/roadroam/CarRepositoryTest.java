package hw1.roadroam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import hw1.roadroam.models.Ticket;
import hw1.roadroam.repositories.CarRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepo carRepository;

    @Test
    void whenFindBMWByName_thenReturnBMW() {
        // arrange a new car and insert into db
        Ticket bmw = new Ticket("bmw", "225xe");
        entityManager.persistAndFlush(bmw); //ensure data is persisted at this point

        // test the query method of interest
        Ticket found = carRepository.findByModel(bmw.getModel());
        assertThat(found).isEqualTo(bmw);
    }

    @Test
    void whenInvalidCarName_thenReturnNull() {
        Ticket fromDb = carRepository.findByModel("Does Not Exist");
        assertThat(fromDb).isNull();
    }

    @Test
    void whenFindEmployedByExistingId_thenReturnCar() {
        Ticket bmw = new Ticket("bmw", "225xe");
        entityManager.persistAndFlush(bmw);

        Ticket fromDb = carRepository.findById(bmw.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getMaker()).isEqualTo(bmw.getMaker());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Ticket fromDb = carRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Ticket audi = new Ticket("audi", "quattro");
        Ticket renault = new Ticket("renault", "laguna");
        Ticket mazda = new Ticket("mazda", "mx-3");

        entityManager.persist(audi);
        entityManager.persist(renault);
        entityManager.persist(mazda);
        entityManager.flush();

        List<Ticket> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Ticket::getModel).containsOnly(audi.getModel(), renault.getModel(), mazda.getModel());
    }

}