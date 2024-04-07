package hw1.roadroam.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import hw1.roadroam.models.Route;
import hw1.roadroam.models.City;
import hw1.roadroam.repositories.RouteRepo;
import hw1.roadroam.services.RouteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @Mock(lenient = true)
    private RouteRepo routeRepository;

    @InjectMocks
    private RouteService routeService;

    private City c0 = new City();
    private City c1 = new City();
    private Route route0 = new Route();
    private Route route1 = new Route();

    @BeforeEach
    public void setUp() {

        //  Create two cities
        c0.setId(1L);
        c0.setName("Aveiro");
        c1.setId(2L);
        c1.setName("Leiria");

        //  Create a route
        route0.setId(1L);
        route0.setOrigin(c0);
        route0.setDestination(c1);

        //  Create a route
        route1.setId(2L);
        route1.setOrigin(c1);
        route1.setDestination(c0);

        List<Route> allRoutes = Arrays.asList(route0, route1);

        Mockito.when(routeRepository.save(route0)).thenReturn(route0);
        Mockito.when(routeRepository.save(route1)).thenReturn(route1);
        Mockito.when(routeRepository.findAll()).thenReturn(allRoutes);
        Mockito.when(routeRepository.findById(route0.getId())).thenReturn(Optional.of(route0));
        Mockito.when(routeRepository.findById(route1.getId())).thenReturn(Optional.of(route1));
        Mockito.when(routeRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(routeRepository.findByOriginId(c0.getId())).thenReturn(Arrays.asList(route0));
        Mockito.when(routeRepository.findByOriginId(c1.getId())).thenReturn(Arrays.asList(route1));
    }

    @Test
     void whenSaveValidRoute_thenRouteShouldBeReturned() {
        Route returned = routeService.save(route0);
        assertThat(returned.getOrigin().getName()).isEqualTo(route0.getOrigin().getName());

        returned = routeService.save(route1);
        assertThat(returned.getOrigin().getName()).isEqualTo(route1.getOrigin().getName());
    }

    @Test
     void whenSearchValidID_thenRouteshouldBeFound() {
        Route found = routeService.getRoute(route0.getId());
        assertThat(found.getDestination().getName()).isEqualTo(route0.getDestination().getName());

        found = routeService.getRoute(route1.getId());
        assertThat(found.getDestination().getName()).isEqualTo(route1.getDestination().getName());
    }

    @Test
     void whenSearchInvalidID_thenRouteShouldNotBeFound() {
        Route fromDb = routeService.getRoute(-99L);
        assertThat(fromDb).isNull();

        Mockito.verify(routeRepository, 
                VerificationModeFactory.times(1))
                    .findById(-99L);
    }

    @Test
     void given2Routes_whengetAll_thenReturn2Records() {
        List<Route> allRoutes = routeService.listRoutes();

        assertThat(allRoutes).hasSize(2).extracting(Route::getDestination).contains(route0.getDestination(), route1.getDestination());

        Mockito.verify(routeRepository, 
                VerificationModeFactory.times(1))
                    .findAll();
    }

    @Test
     void given2Routes_whengetByOrigin_thenReturnOnlyWithSameOrigin() {
        List<Route> allRoutesWithOrigin = routeService.listRoutesByOriginCity(route0.getOrigin().getId());
        assertThat(allRoutesWithOrigin).hasSize(1).extracting(Route::getId).contains(route0.getId());

        allRoutesWithOrigin = routeService.listRoutesByOriginCity(route1.getOrigin().getId());
        assertThat(allRoutesWithOrigin).hasSize(1).extracting(Route::getId).contains(route1.getId());

        Mockito.verify(routeRepository, 
                VerificationModeFactory.times(1))
                    .findByOriginId(route0.getOrigin().getId());

        Mockito.verify(routeRepository, 
                VerificationModeFactory.times(1))
                    .findByOriginId(route1.getOrigin().getId());
    }
}
