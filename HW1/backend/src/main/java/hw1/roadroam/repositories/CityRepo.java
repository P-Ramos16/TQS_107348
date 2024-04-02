package hw1.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hw1.roadroam.models.City;

import java.util.List;
import java.util.Optional;

public interface CityRepo extends JpaRepository<City, Long> {

    public Optional<City> findById(Long id);

    public List<City> findAll();

}
