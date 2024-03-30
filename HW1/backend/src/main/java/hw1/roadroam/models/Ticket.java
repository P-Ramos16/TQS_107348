package hw1.roadroam.models;

import java.util.Objects;
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

    private Integer numberOfPeople;
    private Double finalPrice;
    private Date aquisitionDate;

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private Currency currency;


    public Ticket() {}

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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
