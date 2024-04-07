package hw1.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hw1.roadroam.models.Route;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepo extends JpaRepository<Route, Long> {

    public Optional<Route> findById(Long id);

    public List<Route> findAll();

    public List<Route> findByOriginId(Long origin);
}
