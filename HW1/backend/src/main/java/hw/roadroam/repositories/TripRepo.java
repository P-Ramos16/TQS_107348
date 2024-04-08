package hw.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hw.roadroam.models.Trip;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {

    public Optional<Trip> findById(Long id);

    public List<Trip> findAll();

    public List<Trip> findByRouteId(Long route);
}
