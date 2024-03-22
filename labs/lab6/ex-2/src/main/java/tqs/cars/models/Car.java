package tqs.cars.models;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maker;

    @Column(unique=true)
    private String model;

    public Car() {
    }

    public Car(Long id, String maker, String model) {
        this.id = id;
        this.maker = maker;
        this.model = model;
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id.equals(car.id) && Objects.equals(maker, car.maker) && Objects.equals(model, car.model);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maker, model);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
