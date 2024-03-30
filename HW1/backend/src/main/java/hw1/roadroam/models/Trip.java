package hw1.roadroam.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numberOfSeatsAvailable;
    private Integer numberOfSeatsTotal;
    private String tripLength;

    private Date date;

    @ManyToOne
    private City origin;
    
    @ManyToOne
    private City destination;

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

    public String getTripLength() {
        return tripLength;
    }

    public void setTripLength(String tripLength) {
        this.tripLength = tripLength;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    
}
