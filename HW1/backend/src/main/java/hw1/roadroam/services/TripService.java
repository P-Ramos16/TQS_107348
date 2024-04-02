package hw1.roadroam.services;

import org.springframework.stereotype.Service;

import hw1.roadroam.models.Trip;
import hw1.roadroam.repositories.TripRepo;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    final TripRepo tripRepository;

    public TripService(TripRepo tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip save(Trip c) {
        return tripRepository.save(c);
    }

    public List<Trip> listCurrencies() {
        return tripRepository.findAll();
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
