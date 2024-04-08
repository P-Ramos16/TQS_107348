package hw.roadroam.models;

import java.util.Objects;

import hw.roadroam.services.CurrencyService;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String creditCard;
    private String currency;

    private Integer numberOfPeople;
    private Double finalPrice;
    private Date aquisitionDate;
    private Integer seatNumber;

    @ManyToOne
    private Trip trip;

    public Double calculateFinalPrice(CurrencyService currencyService) {
        Double regularPrice = this.numberOfPeople * this.trip.getBasePrice();

        //  Increase the price of each ticket by a bit (up to 30%) so that the earlier tickets are cheaper 
        Double percentageFilledSeats = (double)this.trip.getNumberOfSeatsAvailable() / this.trip.getNumberOfSeatsAvailable();
        Double percentageToIncrease = percentageFilledSeats / 0.3;

        this.finalPrice = regularPrice + (regularPrice * percentageToIncrease);
        
        this.finalPrice *= currencyService.getCurrency(currency).getExchangeRate();

        this.finalPrice = Math.floor(this.finalPrice * 100) / 100;
        
        return this.finalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return id.equals(ticket.id) && Objects.equals(numberOfPeople, ticket.numberOfPeople) && Objects.equals(finalPrice, ticket.finalPrice) && Objects.equals(aquisitionDate, ticket.aquisitionDate);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfPeople, finalPrice, aquisitionDate, trip, currency);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Date getAquisitionDate() {
        return aquisitionDate;
    }

    public void setAquisitionDate(Date aquisitionDate) {
        this.aquisitionDate = aquisitionDate;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
