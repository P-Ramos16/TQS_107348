package hw1.roadroam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hw1.roadroam.models.Trip;
import hw1.roadroam.models.City;
import hw1.roadroam.models.Route;
import hw1.roadroam.models.Currency;
import hw1.roadroam.services.TripService;
import hw1.roadroam.services.CityService;
import hw1.roadroam.services.RouteService;
import hw1.roadroam.services.CurrencyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.LinkedList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;
    private final CityService cityService;
    private final RouteService routeService;
    private final CurrencyService currencyService;

    public TripController(TripService tservice, CityService ciservice, RouteService rservice, CurrencyService cuservice) {
        this.tripService = tservice;
        this.cityService = ciservice;
        this.routeService = rservice;
        this.currencyService = cuservice;
    }

    @PostMapping("/add") public ResponseEntity<Trip> createTrip(@RequestParam Integer numberOfSeatsAvailable, @RequestParam Integer numberOfSeatsTotal,
                                                                @RequestParam String tripLengthTime, @RequestParam String tripLengthKm, 
                                                                @RequestParam Date date, @RequestParam String time,
                                                                @RequestParam Integer busNumber, @RequestParam Double basePrice,
                                                                @RequestParam Long route) {
        
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

        Route activeRoute = routeService.getRoute(route);
        if (activeRoute == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A route with the provided ID could not be found!");
        }
        
        Trip trip = new Trip();
        trip.setNumberOfSeatsAvailable(numberOfSeatsAvailable);
        trip.setNumberOfSeatsTotal(numberOfSeatsTotal);
        trip.setBusNumber(busNumber);
        trip.setBasePrice(basePrice);
        trip.setTripLengthTime(tripLengthTime);
        trip.setTripLengthKm(tripLengthKm);
        trip.setDate(date);
        trip.setTime(time);
        trip.setRoute(activeRoute);
        trip.setFilledSeats(new LinkedHashSet<Integer>());
        
        Trip saved = tripService.save(trip);
        
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/list",  produces = "application/json")
    public List<Trip> getAllTrips() {
        return tripService.listTrips();
    }

    @GetMapping(path = "/listByRoute",  produces = "application/json")
    public List<Trip> listTripsByRoute(@RequestParam Long route, @RequestParam String currency) {

        if (currency == null || currency.equals("")) {
            currency = "USD";
        }

        Route rt = routeService.getRoute(route);
        if (rt == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A route with the provided ID could not be found!");
        }

        Currency curr = currencyService.getCurrency(currency);
        if (curr == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A currency with the provided abreviation could not be found!");
        }

        Double exchangeRate = curr.getExchange_rate();
        List<Trip> trips = tripService.listTripsByRouteID(route);

        for (Trip tr : trips) {
            Double price = Math.floor(tr.getBasePrice() * exchangeRate * 100) / 100;
            tr.setBasePrice(price);
        }

        return trips;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable(value = "id") Long id) {
        Trip trip = tripService.getTrip(id);
        
        if (trip == null) {
            return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok().body(trip);
    }

    @PostMapping("/addRoute") public ResponseEntity<Route> createRoute(@RequestParam Long origin, @RequestParam Long destination) {

        City ogCity = cityService.getCity(origin);
        if (ogCity == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Origin city with the provided ID could not be found!");
        }

        City dtCity = cityService.getCity(destination);
        if (dtCity == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Destination city with the provided ID could not be found!");
        }

        Route route = new Route();
        route.setOrigin(ogCity);
        route.setDestination(dtCity);

        HttpStatus status = HttpStatus.CREATED;
        Route saved = routeService.save(route);

        return new ResponseEntity<>(saved, status);
    }


    @GetMapping(path = "/listRouteOrigins",  produces = "application/json")
    public List<HashMap<String, String>> listRouteOrigins() {

        List<Route> routes = routeService.listRoutes();
        //  Map of all the Cities IDs that are origins of routes and their names
        List<HashMap<String, String>> response = new LinkedList<HashMap<String, String>>();

        Set<Long> addedIds = new LinkedHashSet<Long>();

        for (Route rt : routes) {
            if (addedIds.contains(rt.getOrigin().getId())) {
                continue;
            }

            HashMap<String, String> route = new HashMap<String, String>();
            route.put("id", rt.getOrigin().getId().toString());
            route.put("name", rt.getOrigin().getName());
            response.add(route);

            addedIds.add(rt.getId());
        }

        return response;
    }


    @GetMapping(path = "/listRouteDestinationsByOrigin",  produces = "application/json")
    public List<HashMap<String, String>> listRouteDestinationsByOrigin(@RequestParam Long origin) {

        City ogCity = cityService.getCity(origin);
        if (ogCity == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Origin city with the provided ID could not be found!");
        }

        List<Route> routes = routeService.listRoutesByOriginCity(origin);
        //  Map of all the Cities IDs that are origins of routes and their names
        List<HashMap<String, String>> response = new LinkedList<HashMap<String, String>>();

        for (Route rt : routes) {
            HashMap<String, String> route = new HashMap<String, String>();
            route.put("id", rt.getId().toString());
            route.put("name", rt.getDestination().getName());
            response.add(route);
        }
        return response;
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
