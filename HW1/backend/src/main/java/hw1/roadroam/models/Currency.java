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
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String abreviation;
    private Double exchange_rate;

    public Currency() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency curr = (Currency) o;

        return id.equals(curr.id) && Objects.equals(abreviation, curr.abreviation) && Objects.equals(exchange_rate, curr.exchange_rate);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public Double getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(Double exchange_rate) {
        this.exchange_rate = exchange_rate;
    }
}
