package hw1.roadroam.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import hw1.roadroam.models.City;
import hw1.roadroam.repositories.CityRepo;
import hw1.roadroam.services.CityService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock(lenient = true)
    private CityRepo cityRepository;

    @InjectMocks
    private CityService cityService;

    private City city0 = new City();
    private City city1 = new City();

    @BeforeEach
    public void setUp() {

        //  Create two cities
        city0.setId(1L);
        city0.setName("Aveiro");
        city1.setId(2L);
        city1.setName("Leiria");

        List<City> allCities = Arrays.asList(city0, city1);

        Mockito.when(cityRepository.save(city0)).thenReturn(city0);
        Mockito.when(cityRepository.save(city1)).thenReturn(city1);
        Mockito.when(cityRepository.findAll()).thenReturn(allCities);
        Mockito.when(cityRepository.findById(city0.getId())).thenReturn(Optional.of(city0));
        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));
        Mockito.when(cityRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
     void whenSaveValidCity_thenCityShouldBeReturned() {
        City returned = cityService.save(city0);
        assertThat(returned.getName()).isEqualTo(city0.getName());

        returned = cityService.save(city1);
        assertThat(returned.getName()).isEqualTo(city1.getName());
    }

    @Test
     void whenSearchValidID_thenCityshouldBeFound() {
        City found = cityService.getCity(city0.getId());
        assertThat(found.getName()).isEqualTo(city0.getName());

        found = cityService.getCity(city1.getId());
        assertThat(found.getName()).isEqualTo(city1.getName());
    }

    @Test
     void whenSearchInvalidID_thenCityShouldNotBeFound() {
        City fromDb = cityService.getCity(-99L);
        assertThat(fromDb).isNull();

        Mockito.verify(cityRepository, 
                VerificationModeFactory.times(1))
                    .findById(-99L);
    }

    @Test
     void given2Cities_whengetAll_thenReturn2Records() {
        List<City> allCitys = cityService.listCities();

        assertThat(allCitys).hasSize(2).extracting(City::getName).contains(city0.getName(), city1.getName());

        Mockito.verify(cityRepository, 
                VerificationModeFactory.times(1))
                    .findAll();
    }
}
