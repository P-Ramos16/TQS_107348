package hw1.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hw1.roadroam.models.Route;

import java.util.List;
import java.util.Optional;

public interface RouteRepo extends JpaRepository<Route, Long> {

    public Optional<Route> findById(Long id);

    public List<Route> findAll();

    //@Query("SELECT r FROM routes r WHERE r.origin = :origin")
    //public List<Route> findByOriginID(@Param("origin") Long origin);

    public List<Route> findByOriginId(Long origin);
}
