package hw.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hw.roadroam.models.City;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {

    public Optional<City> findById(Long id);

    public List<City> findAll();

}
