package tqs.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.cars.models.Car;
import tqs.cars.repositories.CarRepo;

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
        Car bmw = new Car("bmw", "225xe");
        entityManager.persistAndFlush(bmw); //ensure data is persisted at this point

        // test the query method of interest
        Car found = carRepository.findByModel(bmw.getModel());
        assertThat(found).isEqualTo(bmw);
    }

    @Test
    void whenInvalidCarName_thenReturnNull() {
        Car fromDb = carRepository.findByModel("Does Not Exist");
        assertThat(fromDb).isNull();
    }

    @Test
    void whenFindEmployedByExistingId_thenReturnCar() {
        Car bmw = new Car("bmw", "225xe");
        entityManager.persistAndFlush(bmw);

        Car fromDb = carRepository.findById(bmw.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getMaker()).isEqualTo(bmw.getMaker());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car audi = new Car("audi", "quattro");
        Car renault = new Car("renault", "laguna");
        Car mazda = new Car("mazda", "mx-3");

        entityManager.persist(audi);
        entityManager.persist(renault);
        entityManager.persist(mazda);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getModel).containsOnly(audi.getModel(), renault.getModel(), mazda.getModel());
    }

}