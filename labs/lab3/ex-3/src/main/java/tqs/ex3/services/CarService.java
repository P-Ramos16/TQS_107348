package tqs.ex3.services;

import org.springframework.stereotype.Service;

import tqs.ex3.models.Car;
import tqs.ex3.repositories.CarRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    final
    CarRepo carRepository;

    public CarService(CarRepo carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car c) {
        return carRepository.save(c);
    }

    public List<Car> listCars() {

        return carRepository.findAll();
    }

    public Car getCar(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            return car.get();
        }
        else {
            return null;
        }
    }

    public Car getCarByModel(String model) {
        return carRepository.findByModel(model);
    }


    public boolean carExistsByModel(String model) {
        return carRepository.findByModel(model) != null;
    }
}
