package tqs.ex2.controllers;

import tqs.ex2.models.Car;
import tqs.ex2.services.CarService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService service) {
        this.carService = service;
    }


    @PostMapping("/add") public ResponseEntity<Car> createCar(@RequestBody Car oneCar) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carService.save(oneCar);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/list",  produces = "application/json")
    public List<Car> getAllCars() {
        return carService.listCars();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long id) {
        Car car = carService.getCar(id);
        
        if (car == null) {
            return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok().body(car);
    }

}
