package hw1.roadroam.repositoryTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hw1.roadroam.models.City;
import hw1.roadroam.models.Route;
import hw1.roadroam.repositories.CityRepo;
import hw1.roadroam.repositories.RouteRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
class RouteRepositoryTest {

    @Autowired
    private RouteRepo routeRepository;
    @Autowired
    private CityRepo cityRepository;

    private Route route0 = new Route();
    private Route route1 = new Route();
    private City c0 = new City();
    private City c1 = new City();

    @BeforeAll
    public void setUp() throws Exception {

        c0.setId(1L);
        c0.setName("Aveiro");
        c1.setId(2L);
        c1.setName("Lisboa");
        
        cityRepository.saveAndFlush(c0);
        cityRepository.saveAndFlush(c1);

        //  Create a route
        route0.setId(1L);
        route0.setOrigin(c0);
        route0.setDestination(c1);

        route1.setId(2L);
        route1.setOrigin(c1);
        route1.setDestination(c0);

        // arrange a new route and insert into db
        //ensure data is persisted at this point
        routeRepository.saveAndFlush(route0);
        routeRepository.saveAndFlush(route1);
    }

    @Test
    void whenFindRouteById_thenReturnRoute() {

        // test the query method of interest
        Route found = routeRepository.findById(route0.getId()).get();
        
        assertThat(found.getOrigin().getName()).isEqualTo(route0.getOrigin().getName());
        assertThat(found.getDestination().getName()).isEqualTo(route0.getDestination().getName());
    }

    @Test
    void whenInvalidRouteId_thenReturnNull() {
        Route found = routeRepository.findById(123L).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void whenRouteIsDeleted_thenReturnNull() {
        //ensure data is persisted at this point
        routeRepository.saveAndFlush(route0);

        routeRepository.delete(route0);

        Route found = routeRepository.findById(route0.getId()).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void givenSetOfRoutes_whenFindAll_thenReturnAllRoutes() {

        List<Route> allRoutes = routeRepository.findAll();

        assertThat(allRoutes).hasSize(2).extracting(Route::getId).containsOnly(route0.getId(), route1.getId());
    }

    @Test
    void givenSetOfRoutes_whenFindByOrigin_thenReturnAllRoutes() {

        List<Route> allRoutes = routeRepository.findByOriginId(2L);

        assertThat(allRoutes).hasSize(1).extracting(Route::getId).containsOnly(route1.getId());
    }

}