package hw1.roadroam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hw1.roadroam.models.Trip;
import hw1.roadroam.models.City;
import hw1.roadroam.models.Currency;
import hw1.roadroam.services.TripService;
import hw1.roadroam.services.CityService;
import hw1.roadroam.services.CurrencyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Date;
import java.util.LinkedHashSet;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;
    private final CityService cityService;
    private final CurrencyService currencyService;

    public TripController(TripService tservice, CityService ciservice, CurrencyService cuservice) {
        this.tripService = tservice;
        this.cityService = ciservice;
        this.currencyService = cuservice;
    }

    @PostMapping("/add") public ResponseEntity<Trip> createTrip(@RequestParam Integer numberOfSeatsAvailable, @RequestParam Integer numberOfSeatsTotal,
                                                                @RequestParam String tripLength, @RequestParam Date date, @RequestParam String time,
                                                                @RequestParam Integer busNumber, @RequestParam Double basePrice,
                                                                @RequestParam Long origin, @RequestParam Long destination) {
        
        
        if (numberOfSeatsAvailable > numberOfSeatsTotal) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Number of seats available cannot be larger than the number of seats of the bus!");
        }

        if (numberOfSeatsAvailable < 0 || numberOfSeatsTotal < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Number of total seats and available cannot be smaller than zero!");
        }

        if (busNumber < 0 || busNumber > 20) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The bus number can only be between 1 and 20!");
        }

        if (basePrice < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The base price cannot be smaller than 0!");
        }

        City ogCity = cityService.getCity(origin);
        if (ogCity == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Origin city with the provided ID could not be found!");
        }

        City dtCity = cityService.getCity(destination);
        if (dtCity == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Destination city with the provided ID could not be found!");
        }
        
        Trip trip = new Trip();
        trip.setNumberOfSeatsAvailable(numberOfSeatsAvailable);
        trip.setNumberOfSeatsTotal(numberOfSeatsTotal);
        trip.setBusNumber(busNumber);
        trip.setBasePrice(basePrice);
        trip.setTripLength(tripLength);
        trip.setDate(date);
        trip.setTime(time);
        trip.setOrigin(ogCity);
        trip.setDestination(dtCity);
        trip.setFilledSeats(new LinkedHashSet<Integer>());
        
        Trip saved = tripService.save(trip);
        
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/list",  produces = "application/json")
    public List<Trip> getAllTrips() {
        return tripService.listTrips();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable(value = "id") Long id) {
        Trip trip = tripService.getTrip(id);
        
        if (trip == null) {
            return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok().body(trip);
    }

    @PostMapping("/addCity") public ResponseEntity<City> createCity(@RequestBody City oneCity) {
        HttpStatus status = HttpStatus.CREATED;
        City saved = cityService.save(oneCity);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/listCity",  produces = "application/json")
    public List<City> getAllCities() {
        return cityService.listCities();
    }

    @GetMapping(path = "/listCurrencies",  produces = "application/json")
    public List<Currency> getAllCurrencies() {
        return currencyService.listCurrencies();
    }

    @GetMapping("/getCurrency/{abreviation}")
    public ResponseEntity<Currency> getTripById(@PathVariable(value = "abreviation") String abre) {
        Currency curr = currencyService.getCurrency(abre);
        
        if (curr == null) {
            return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok().body(curr);
    }

}
