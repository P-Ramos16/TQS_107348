package tqs.ex4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tqs.ex4.models.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends JpaRepository<Car, Long> {

    public Optional<Car> findById(Long Id);

    public Car findByModel(String model);

    public List<Car> findAll();

}
