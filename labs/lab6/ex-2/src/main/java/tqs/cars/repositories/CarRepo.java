package tqs.cars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tqs.cars.models.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends JpaRepository<Car, Long> {

    public Optional<Car> findById(Long id);

    public Car findByModel(String model);

    public List<Car> findAll();

}
