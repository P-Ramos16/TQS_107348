package hw.roadroam.unitTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import hw.roadroam.services.TicketValidator;


public class TicketValidatorTests {

    @Test
     void testValidEmail() {
        TicketValidator ticketValidator = new TicketValidator();
        assertTrue(ticketValidator.validateEmail("josesinhi@ua.com"));
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
}