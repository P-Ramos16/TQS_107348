package hw1.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hw1.roadroam.models.Trip;

import java.util.List;
import java.util.Optional;

public interface TripRepo extends JpaRepository<Trip, Long> {

    public Optional<Trip> findById(Long id);

    public List<Trip> findAll();

    public List<Trip> findByRouteId(Long route);
}
