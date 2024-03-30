package hw1.roadroam.services;

import org.springframework.stereotype.Service;

import hw1.roadroam.models.Ticket;
import hw1.roadroam.repositories.CarRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    final
    CarRepo carRepository;

    public CarService(CarRepo carRepository) {
        this.carRepository = carRepository;
    }

    public Ticket save(Ticket c) {
        return carRepository.save(c);
    }

    public List<Ticket> listCars() {

        return carRepository.findAll();
    }

    public Ticket getCar(Long id) {
        Optional<Ticket> car = carRepository.findById(id);
        if (car.isPresent()) {
            return car.get();
        }
        else {
            return null;
        }
    }

    public Ticket getCarByModel(String model) {
        return carRepository.findByModel(model);
    }


    public boolean carExistsByModel(String model) {
        return carRepository.findByModel(model) != null;
    }
}
