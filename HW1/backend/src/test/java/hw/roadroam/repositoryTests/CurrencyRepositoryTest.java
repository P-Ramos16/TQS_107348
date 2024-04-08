package hw.roadroam.repositoryTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hw.roadroam.models.Currency;
import hw.roadroam.repositories.CurrencyRepo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepo currencyRepository;

    private Currency currency0 = new Currency();
    private Currency currency1 = new Currency();

    @BeforeAll
    public void setUp() throws Exception {

        currencyRepository.deleteAll();
        //  Create a currency
        currency0.setId(1L);
        currency0.setAbreviation("EUR");
        currency0.setExchangeRate(2.0);

        currency1.setId(2L);
        currency1.setAbreviation("AMD");
        currency1.setExchangeRate(1.5);

        // arrange a new currency and insert into db
        //ensure data is persisted at this point
        currencyRepository.saveAndFlush(currency0);
        currencyRepository.saveAndFlush(currency1);

        currency0.setId(currencyRepository.findByAbreviation("EUR").get().getId());
        currency1.setId(currencyRepository.findByAbreviation("AMD").get().getId());
    }

    @Test
    void whenFindCurrencyById_thenReturnCurrency() {

        // test the query method of interest
        Currency found = currencyRepository.findById(currency0.getId()).get();
        
        assertThat(found.getAbreviation()).isEqualTo(currency0.getAbreviation());
        assertThat(found.getExchangeRate()).isEqualTo(currency0.getExchangeRate());
    }

    @Test
    void whenInvalidCurrencyId_thenReturnNull() {
        Currency found = currencyRepository.findById(123L).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void whenFindCurrencyByAbreviation_thenReturnCurrency() {

        // test the query method of interest
        Currency found = currencyRepository.findByAbreviation(currency0.getAbreviation()).get();
        
        assertThat(found.getId()).isEqualTo(currency0.getId());
        assertThat(found.getExchangeRate()).isEqualTo(currency0.getExchangeRate());
    }

    @Test
    void whenInvalidCurrencyAbreviation_thenReturnNull() {
        Currency found = currencyRepository.findByAbreviation("WLF").orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void whenCurrencyIsDeleted_thenReturnNull() {
        //ensure data is persisted at this point
        currencyRepository.saveAndFlush(currency0);

        currencyRepository.delete(currency0);

        Currency found = currencyRepository.findById(currency0.getId()).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    void givenSetOfCurrencys_whenFindAll_thenReturnAllCurrencys() {

        List<Currency> allCurrencys = currencyRepository.findAll();

        assertThat(allCurrencys).hasSize(2).extracting(Currency::getAbreviation).containsOnly(currency0.getAbreviation(), currency1.getAbreviation());
    }
}