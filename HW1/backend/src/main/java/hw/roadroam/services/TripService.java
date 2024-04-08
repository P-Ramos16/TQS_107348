package hw.roadroam.services;

import org.springframework.stereotype.Service;

import hw.roadroam.models.Trip;
import hw.roadroam.repositories.TripRepo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepo tripRepository;

    public Trip save(Trip c) {
        return tripRepository.save(c);
    }

    public List<Trip> listTrips() {
        return tripRepository.findAll();
    }

    public List<Trip> listTripsByRouteID(Long route) {
        return tripRepository.findByRouteId(route);
    }

    public Trip getTrip(Long id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isPresent()) {
            return trip.get();
        }
        else {
            return null;
        }
    }
}
