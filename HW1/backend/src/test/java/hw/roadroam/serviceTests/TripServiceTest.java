package hw.roadroam.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import hw.roadroam.models.Trip;
import hw.roadroam.models.City;
import hw.roadroam.models.Route;
import hw.roadroam.repositories.TripRepo;
import hw.roadroam.services.TripService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock(lenient = true)
    private TripRepo tripRepository;

    @InjectMocks
    private TripService tripService;

    private City c0 = new City();
    private City c1 = new City();
    private Route r0 = new Route();
    private Trip trip0 = new Trip();
    private Trip trip1 = new Trip();

    @BeforeEach
    public void setUp() {

        //  Create two cities
        c0.setId(1L);
        c0.setName("Aveiro");
        c1.setId(2L);
        c1.setName("Leiria");

        //  Create a route
        r0.setId(1L);
        r0.setOrigin(c0);
        r0.setDestination(c1);

        //  Create a trip
        trip0.setId(1L);
        trip0.setBusNumber(16);
        trip0.setBasePrice(12.91);
        trip0.setNumberOfSeatsTotal(6);
        trip0.setNumberOfSeatsAvailable(2);
        trip0.setTripLengthKm("120km");
        trip0.setTripLengthTime("1h2m");
        trip0.setDate(null);
        trip0.setFilledSeats(new HashSet<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }});
        trip0.setRoute(r0);

        //  Create a trip
        trip1.setId(2L);
        trip1.setBusNumber(18);
        trip1.setBasePrice(8.12);
        trip1.setNumberOfSeatsTotal(8);
        trip1.setNumberOfSeatsAvailable(6);
        trip1.setTripLengthKm("25km");
        trip1.setTripLengthTime("47m");
        trip1.setDate(null);
        trip1.setFilledSeats(new HashSet<Integer>() {{
            add(2);
            add(5);
        }});
        trip1.setRoute(r0);

        List<Trip> allTrips = Arrays.asList(trip0, trip1);

        Mockito.when(tripRepository.save(trip0)).thenReturn(trip0);
        Mockito.when(tripRepository.save(trip1)).thenReturn(trip1);
        Mockito.when(tripRepository.findAll()).thenReturn(allTrips);
        Mockito.when(tripRepository.findById(trip0.getId())).thenReturn(Optional.of(trip0));
        Mockito.when(tripRepository.findById(trip1.getId())).thenReturn(Optional.of(trip1));
        Mockito.when(tripRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(tripRepository.findByRouteId(r0.getId())).thenReturn(allTrips);
    }

    @Test
     void whenSaveValidTrip_thenTripShouldBeReturned() {
        Trip returned = tripService.save(trip0);
        assertThat(returned.getBasePrice()).isEqualTo(trip0.getBasePrice());

        returned = tripService.save(trip1);
        assertThat(returned.getBasePrice()).isEqualTo(trip1.getBasePrice());
    }

    @Test
     void whenSearchValidID_thenTripshouldBeFound() {
        Trip found = tripService.getTrip(trip0.getId());
        assertThat(found.getBusNumber()).isEqualTo(trip0.getBusNumber());

        found = tripService.getTrip(trip1.getId());
        assertThat(found.getBusNumber()).isEqualTo(trip1.getBusNumber());
    }

    @Test
     void whenSearchInvalidID_thenTripShouldNotBeFound() {
        Trip fromDb = tripService.getTrip(-99L);
        assertThat(fromDb).isNull();

        Mockito.verify(tripRepository, 
                VerificationModeFactory.times(1))
                    .findById(-99L);
    }

    @Test
     void given2Trips_whengetAll_thenReturn2Records() {

        List<Trip> allTrips = tripService.listTrips();

        assertThat(allTrips).hasSize(2).extracting(Trip::getTripLengthKm).contains(trip0.getTripLengthKm(), trip1.getTripLengthKm());

        Mockito.verify(tripRepository, 
                VerificationModeFactory.times(1))
                    .findAll();
    }
}
