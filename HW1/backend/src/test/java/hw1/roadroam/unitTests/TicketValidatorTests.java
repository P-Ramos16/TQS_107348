package hw1.roadroam.unitTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import hw1.roadroam.services.TicketValidator;


public class TicketValidatorTests {


    @Test
    void testInvalidEmail() {
        TicketValidator ticketValidator = new TicketValidator();
        assertFalse(ticketValidator.validateEmail("gandamail@.pt"));
    }

    @Test
     void testValidEmail() {
        TicketValidator ticketValidator = new TicketValidator();
        assertTrue(ticketValidator.validateEmail("zemanel@gmail.com"));
    }

    
    @Test
     void testInvalidPhone() {
        TicketValidator ticketValidator = new TicketValidator();
        assertFalse(ticketValidator.validatePhone("9123723"));
    }

    @Test
     void testValidPhone() {
        TicketValidator ticketValidator = new TicketValidator();
        assertTrue(ticketValidator.validatePhone("917264927"));
    }


}