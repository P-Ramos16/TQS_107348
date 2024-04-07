package hw1.roadroam.repositoryTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hw1.roadroam.models.City;
import hw1.roadroam.repositories.CityRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private CityRepo cityRepository;

    private City city0 = new City();
    private City city1 = new City();

    @BeforeAll
    public void setUp() throws Exception {

        city0.setId(1L);
        city0.setName("Aveiro");
        city1.setId(2L);
        city1.setName("Lisboa");
        
        // arrange a new city and insert into db
        //ensure data is persisted at this point
        cityRepository.saveAndFlush(city0);
        cityRepository.saveAndFlush(city1);
    }

    @Test
    void whenFindCityById_thenReturnCity() {

        // test the query method of interest
        City found = cityRepository.findById(city0.getId()).get();
        
        assertThat(found.getName()).isEqualTo(city0.getName());
    }

    @Test
    void whenInvalidCityId_thenReturnNull() {
        City found = cityRepository.findById(123L).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void whenCityIsDeleted_thenReturnNull() {
        //ensure data is persisted at this point
        cityRepository.saveAndFlush(city0);

        cityRepository.delete(city0);

        City found = cityRepository.findById(city0.getId()).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void givenSetOfCitys_whenFindAll_thenReturnAllCitys() {

        List<City> allCitys = cityRepository.findAll();

        assertThat(allCitys).hasSize(2).extracting(City::getName).containsOnly(city0.getName(), city1.getName());
    }

}