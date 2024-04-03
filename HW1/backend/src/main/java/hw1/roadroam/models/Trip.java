package hw1.roadroam.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import java.util.NoSuchElementException;
import java.util.Set;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numberOfSeatsAvailable;
    private Integer numberOfSeatsTotal;
    private String tripLengthTime;
    private String tripLengthKm;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    @JsonFormat(pattern = "HH:mm:ss")
    private String time;

    private Integer busNumber;
    private Double basePrice;

    private Set<Integer> filledSeats;

    @ManyToOne
    private Route route;

    public Trip() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfSeatsAvailable() {
        return numberOfSeatsAvailable;
    }

    public void setNumberOfSeatsAvailable(Integer numberOfSeatsAvailable) {
        this.numberOfSeatsAvailable = numberOfSeatsAvailable;
    }

    public Integer getNumberOfSeatsTotal() {
        return numberOfSeatsTotal;
    }

    public void setNumberOfSeatsTotal(Integer numberOfSeatsTotal) {
        this.numberOfSeatsTotal = numberOfSeatsTotal;
    }

    public String getTripLengthTime() {
        return tripLengthTime;
    }

    public void setTripLengthTime(String tripLengthTime) {
        this.tripLengthTime = tripLengthTime;
    }

    public String getTripLengthKm() {
        return tripLengthKm;
    }

    public void setTripLengthKm(String tripLengthKm) {
        this.tripLengthKm = tripLengthKm;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(Integer busNumber) {
        this.busNumber = busNumber;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Set<Integer> getFilledSeats() {
        return filledSeats;
    }

    public void fillSeat(int seatID, boolean toFill) {
        if (toFill) {
            this.filledSeats.add(seatID);
        }
        else {
            if (this.filledSeats.contains(seatID)) {
                throw new NoSuchElementException("This seat was not yet filled!");
            }
            this.filledSeats.remove(seatID);
        }
    }

    public void setFilledSeats(Set<Integer> filledSeats) {
        this.filledSeats = filledSeats;
    }

    public boolean checkSeatIsFilled(Integer seatsNum) {
        return this.filledSeats.contains(seatsNum);
    }
}
