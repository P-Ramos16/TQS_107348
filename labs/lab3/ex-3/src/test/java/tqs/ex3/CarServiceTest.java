package tqs.ex3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.ex3.models.Car;
import tqs.ex3.repositories.CarRepo;
import tqs.ex3.services.CarService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock( lenient = true)
    private CarRepo carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {

        Car audi = new Car("audi", "quattro");
        audi.setId(123L);
        Car renault = new Car("renault", "laguna");
        Car mazda = new Car("mazda", "mx-3");

        List<Car> allCars = Arrays.asList(audi, renault, mazda);

        Mockito.when(carRepository.findByModel(audi.getModel())).thenReturn(audi);
        Mockito.when(carRepository.findById(123L)).thenReturn(Optional.of(audi));
        Mockito.when(carRepository.findByModel(renault.getModel())).thenReturn(renault);
        Mockito.when(carRepository.findByModel(mazda.getModel())).thenReturn(mazda);
        Mockito.when(carRepository.findByModel("225xe")).thenReturn(null);
        Mockito.when(carRepository.findById(renault.getId())).thenReturn(Optional.of(renault));
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
     void whenSearchValidModel_thenCarShouldBeFound() {
        String model = "laguna";
        Car found = carService.getCarByModel(model);

        assertThat(found.getModel()).isEqualTo(model);
    }

    @Test
     void whenSearchInvalidModel_thenCarShouldNotBeFound() {
        Car fromDb = carService.getCarByModel("225xe");
        assertThat(fromDb).isNull();

        verifyFindByModelIsCalledOnce("225xe");
    }

    @Test
     void whenValidModel_thenCarShouldExist() {
        boolean doesCarExist = carService.carExistsByModel("laguna");
        assertThat(doesCarExist).isTrue();

        verifyFindByModelIsCalledOnce("laguna");
    }

    @Test
     void whenNonExistingModel_thenCarShouldNotExist() {
        boolean doesCarExist = carService.carExistsByModel("yaris");
        assertThat(doesCarExist).isFalse();
        verifyFindByModelIsCalledOnce("yaris");
    }

    @Test
     void whenValidId_thenCarShouldBeFound() {
        Car fromDb = carService.getCar(123L);
        assertThat(fromDb.getModel()).isEqualTo("quattro");

        verifyFindByIdIsCalledOnce();
    }

    @Test
     void whenInValidId_thenCarShouldNotBeFound() {
        Car fromDb = carService.getCar(-99L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
     void given3Cars_whengetAll_thenReturn3Records() {
        Car audi = new Car("audi", "quattro");
        Car renault = new Car("renault", "laguna");
        Car mazda = new Car("mazda", "mx-3");

        List<Car> allCars = carService.listCars();
        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(audi.getModel(), renault.getModel(), mazda.getModel());
    }

    private void verifyFindByModelIsCalledOnce(String model) {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByModel(model);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
