package hw.roadroam.services;

import java.util.regex.Pattern;

public class TicketValidator {
    
    public boolean validateEmail(String email) {
        String ePattern = "^^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern, Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean validatePhone(String phone) {
        String pPattern = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pPattern, Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
    }

    public boolean validateCreditCard(String creditcard) {
        return !creditcard.isEmpty();
    }

    public boolean validateNumberOfPeople(Integer numberOfPeople, Integer availableSeats) {
        return numberOfPeople > 0 && availableSeats - numberOfPeople > 0;
    }

    public boolean validateSeatNumber(Integer seatNumber) {
        return seatNumber > 0;
    }
}
