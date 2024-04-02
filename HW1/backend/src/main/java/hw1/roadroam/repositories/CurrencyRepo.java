package hw1.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hw1.roadroam.models.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {

    public Optional<Currency> findById(Long id);

    public Currency findByAbreviation(String model);

    public List<Currency> findAll();

}
