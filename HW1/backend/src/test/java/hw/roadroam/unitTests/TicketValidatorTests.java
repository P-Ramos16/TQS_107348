package hw.roadroam.unitTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import hw.roadroam.services.TicketValidator;


class TicketValidatorTests {

    @Test
     void testValidEmail() {
        TicketValidator ticketValidator = new TicketValidator();
        assertTrue(ticketValidator.validateEmail("jose@fino.ua.pt"));
    }

    @Test
    void testInvalidEmail() {
        TicketValidator ticketValidator = new TicketValidator();
        assertFalse(ticketValidator.validateEmail("gandamail@.pt"));
    }

    @Test
     void testValidPhone() {
        TicketValidator ticketValidator = new TicketValidator();
        assertTrue(ticketValidator.validatePhone("917264927"));
    }

    @Test
     void testInvalidPhone() {
        TicketValidator ticketValidator = new TicketValidator();
        assertFalse(ticketValidator.validatePhone("9123723"));
    }

    @Test
     void testValidCreditCard() {
        TicketValidator ticketValidator = new TicketValidator();
        assertTrue(ticketValidator.validateCreditCard("9123723"));
    }

    @Test
     void testValidNumberOfPeople() {
        TicketValidator ticketValidator = new TicketValidator();
        assertTrue(ticketValidator.validateNumberOfPeople(2, 3));
        assertFalse(ticketValidator.validateNumberOfPeople(-2, 3));
        assertFalse(ticketValidator.validateNumberOfPeople(12, 3));
    }
}