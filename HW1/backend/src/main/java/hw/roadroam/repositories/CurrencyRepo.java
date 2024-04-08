package hw.roadroam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hw.roadroam.models.Currency;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Long> {

    public Optional<Currency> findById(Long id);

    public Optional<Currency> findByAbreviation(String model);

    public List<Currency> findAll();

}
