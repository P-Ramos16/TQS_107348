package hw.roadroam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hw.roadroam.models.Ticket;
import hw.roadroam.models.Trip;
import hw.roadroam.models.Currency;
import hw.roadroam.services.TicketService;
import hw.roadroam.services.TicketValidator;
import hw.roadroam.services.TripService;
import hw.roadroam.services.CurrencyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private TripService tripService;
    @Autowired
    private CurrencyService currencyService;

    private static TicketValidator tValidator = new TicketValidator();
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @PostMapping("/buy") 
    public ResponseEntity<Ticket> buyTicket(@RequestParam String firstname, @RequestParam String lastname,
                                            @RequestParam String phone, @RequestParam String email,
                                            @RequestParam String creditCard, @RequestParam Integer numberOfPeople,
                                            @RequestParam Integer seatNumber,
                                            @RequestParam Long trip, @RequestParam String currency) {

        if (firstname == null || firstname.equals("") || lastname == null || lastname.equals("") || 
            phone == null || phone.equals("") || email == null || email.equals("") || creditCard == null || creditCard.equals("")) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Please input all the required fields!");
        }

        if (!tValidator.validateEmail(email)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The provided email is not valid!");
        }

        if (!tValidator.validatePhone(phone)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The provided phone number is not valid!");
        }

        if (!tValidator.validateSeatNumber(seatNumber)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The seat number on the ticket cannot be zero or smaller!");
        }

        Trip tripObj = tripService.getTrip(trip);
        if (tripObj == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A trip with the provided ID could not be found!");
        }

        Currency currencyObj = currencyService.getCurrency(currency);
        if (currencyObj == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A Currency with the provided abreviation could not be found!");
        }

        if (tripObj.checkSeatIsFilled(seatNumber)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The requested seat is already filled!");
        }

        if (!tValidator.validateNumberOfPeople(numberOfPeople, tripObj.getNumberOfSeatsAvailable())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The trip cannot fit the requested number of people!");
        }


        Ticket tick = new Ticket();
        tick.setFirstname(firstname);
        tick.setLastname(lastname);
        tick.setPhone(phone);
        tick.setEmail(email);
        tick.setCreditCard(creditCard);
        tick.setNumberOfPeople(numberOfPeople);
        tick.setTrip(tripObj);
        tick.setCurrency(currency);
        tick.setSeatNumber(seatNumber);
        tick.setAquisitionDate(new Date());
        tick.calculateFinalPrice(currencyService);

        Ticket saved = ticketService.save(tick);

        tripObj.fillSeat(seatNumber, true);
        tripObj.setNumberOfSeatsAvailable(tripObj.getNumberOfSeatsAvailable() - 1);
        //  If more than one seat is purchased, allocate it to the closest (sequential) seat
        Integer numSeatsToFill = numberOfPeople - 1;
        Integer currSeatIdx = seatNumber + 1;

        //  Should never become an infinite since the number of seats available is checked before 
        while (numSeatsToFill != 0) {
            if (!tripObj.checkSeatIsFilled(currSeatIdx)) {
                tripObj.fillSeat(currSeatIdx, true);
                tripObj.setNumberOfSeatsAvailable(tripObj.getNumberOfSeatsAvailable() - 1);
                numSeatsToFill--;
                
                //  Loop arround the seat numbers
                if (currSeatIdx >= tripObj.getNumberOfSeatsTotal()) {
                    currSeatIdx = 0;
                }
            }
            currSeatIdx++;
        }

        tripService.save(tripObj);

        logger.info("A ticket with id {} was sucessfully persisted!", saved.getId());

        return ResponseEntity.ok().body(saved);
    }

    @GetMapping(path = "/list",  produces = "application/json")
    public List<Ticket> getAllTickets() {
        return ticketService.listTickets();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable(value = "id") Long id) {
        Ticket ticket = ticketService.getTicket(id);
        
        if (ticket == null) {
            return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok().body(ticket);
    }

    @GetMapping(path = "/getPrice", produces = "application/json")
    public ResponseEntity<Double> getTicketPriceByValues(@RequestParam Long trip, @RequestParam Integer numpeople, @RequestParam String currency) {
        
        Trip tripObj = tripService.getTrip(trip);
        if (tripObj == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A trip with the provided ID could not be found!");
        }

        Currency currencyObj = currencyService.getCurrency(currency);
        if (currencyObj == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A Currency with the provided abreviation could not be found!");
        }
        Double price = Math.floor(tripObj.getBasePrice() * numpeople * currencyObj.getExchangeRate() * 100) / 100;

        return ResponseEntity.ok().body(price);
    }

}
