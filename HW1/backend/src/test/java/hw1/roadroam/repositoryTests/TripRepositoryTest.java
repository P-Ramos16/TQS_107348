package hw1.roadroam.repositoryTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hw1.roadroam.models.Route;
import hw1.roadroam.models.Trip;
import hw1.roadroam.repositories.RouteRepo;
import hw1.roadroam.repositories.TripRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
class TripRepositoryTest {

    @Autowired
    private TripRepo tripRepository;
    @Autowired
    private RouteRepo routeRepository;

    private Trip trip0 = new Trip();
    private Trip trip1 = new Trip();
    private Route c0 = new Route();
    private Route c1 = new Route();

    @BeforeAll
    public void setUp() throws Exception {

        c0.setId(1L);
        c1.setId(2L);
        
        routeRepository.saveAndFlush(c0);
        routeRepository.saveAndFlush(c1);

        //  Create a trip
        trip0.setId(1L);
        trip0.setBusNumber(16);
        trip0.setRoute(c0);

        //  Create a trip
        trip1.setId(2L);
        trip1.setBusNumber(22);
        trip1.setRoute(c1);
        
        // arrange a new trip and insert into db
        //ensure data is persisted at this point
        tripRepository.saveAndFlush(trip0);
        tripRepository.saveAndFlush(trip1);
    }

    @Test
    void whenFindTripById_thenReturnTrip() {

        // test the query method of interest
        Trip found = tripRepository.findById(trip0.getId()).get();
        
        assertThat(found.getBusNumber()).isEqualTo(trip0.getBusNumber());
    }

    @Test
    void whenInvalidTripId_thenReturnNull() {
        Trip found = tripRepository.findById(123L).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void whenTripIsDeleted_thenReturnNull() {
        //ensure data is persisted at this point
        tripRepository.saveAndFlush(trip0);

        tripRepository.delete(trip0);

        Trip found = tripRepository.findById(trip0.getId()).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void givenSetOfTrips_whenFindAll_thenReturnAllTrips() {

        List<Trip> allTrips = tripRepository.findAll();

        assertThat(allTrips).hasSize(24).extracting(Trip::getBusNumber).containsOnly(trip0.getBusNumber(), trip1.getBusNumber(), 21, 14, 11, 9, 5, 28, 23, 30, 32, 18, 7, 12, 26, 27, 10, 2, 1, 8, 17, 13);
    }

    @Test
    void givenSetOfTrips_whenFindByRoute_thenReturnAllTrips() {

        List<Trip> allTrips = tripRepository.findByRouteId(2L);

        assertThat(allTrips).hasSize(3).extracting(Trip::getBusNumber).containsOnly(trip1.getBusNumber(), 11, 9);
    }

}