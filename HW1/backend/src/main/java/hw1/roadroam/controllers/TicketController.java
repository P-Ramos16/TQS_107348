package hw1.roadroam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hw1.roadroam.models.Ticket;
import hw1.roadroam.models.Trip;
import hw1.roadroam.models.Currency;
import hw1.roadroam.services.TicketService;
import hw1.roadroam.services.TripService;
import hw1.roadroam.services.CurrencyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TripService tripService;
    private final CurrencyService currencyService;

    public TicketController(TicketService tiservice, TripService trservice, CurrencyService cservice) {
        this.ticketService = tiservice;
        this.tripService = trservice;
        this.currencyService = cservice;
    }


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

        if (numberOfPeople <= 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Number of people on the ticket cannot be zero or smaller!");
        }

        if (seatNumber < 0) {
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

        if (tripObj.getNumberOfSeatsAvailable() < numberOfPeople) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The trip cannot fit the requested number of people!");
        }

        if (tripObj.getNumberOfSeatsTotal() < seatNumber) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The seat number on the ticket cannot be larger than the number of available seats!");
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
        Double price = Math.floor(tripObj.getBasePrice() * numpeople * currencyObj.getExchange_rate() * 100) / 100;

        return ResponseEntity.ok().body(price);
    }

}
