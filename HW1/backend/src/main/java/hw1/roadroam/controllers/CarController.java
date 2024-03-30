package hw1.roadroam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hw1.roadroam.models.Ticket;
import hw1.roadroam.services.CarService;

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


    @PostMapping("/add") public ResponseEntity<Ticket> createCar(@RequestBody Ticket oneCar) {
        HttpStatus status = HttpStatus.CREATED;
        Ticket saved = carService.save(oneCar);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/list",  produces = "application/json")
    public List<Ticket> getAllCars() {
        return carService.listCars();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Ticket> getCarById(@PathVariable(value = "id") Long id) {
        Ticket car = carService.getCar(id);
        
        if (car == null) {
            return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok().body(car);
    }

}
