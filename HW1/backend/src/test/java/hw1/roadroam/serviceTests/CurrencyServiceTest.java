package hw1.roadroam.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import hw1.roadroam.models.Currency;
import hw1.roadroam.repositories.CurrencyRepo;
import hw1.roadroam.services.CurrencyService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock(lenient = true)
    private CurrencyRepo currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    private Currency currency0 = new Currency();
    private Currency currency1 = new Currency();

    @BeforeEach
    public void setUp() {

        //  Create two currencies
        currency0.setId(1L);
        currency0.setAbreviation("EUR");
        currency0.setExchange_rate(3234.12);
        currency1.setId(2L);
        currency1.setAbreviation("AMD");
        currency1.setExchange_rate(1.5);

        List<Currency> allCurrencies = Arrays.asList(currency0, currency1);

        Mockito.when(currencyRepository.save(currency0)).thenReturn(currency0);
        Mockito.when(currencyRepository.save(currency1)).thenReturn(currency1);
        Mockito.when(currencyRepository.findAll()).thenReturn(allCurrencies);
        Mockito.when(currencyRepository.findByAbreviation(currency0.getAbreviation())).thenReturn(Optional.of(currency0));
        Mockito.when(currencyRepository.findByAbreviation(currency1.getAbreviation())).thenReturn(Optional.of(currency1));
        Mockito.when(currencyRepository.findByAbreviation("WLF")).thenReturn(Optional.empty());
    }

    @Test
     void whenReloadCache_updateLastUpdate_andKeepTheEurCurrencyAbreviation() {
        long lastUpdateBefore = currencyService.lastUpdate;
        //  Prevent Update right now
        currencyService.lastUpdate = System.currentTimeMillis() / 1000;
        Currency eurBefore = currencyService.getCurrency(currency0.getAbreviation());
        assertThat(eurBefore.getExchange_rate()).isEqualTo(currency0.getExchange_rate());

        double eurRateBefore = eurBefore.getExchange_rate().doubleValue();

        currencyService.reloadCache();


        long lastUpdateAfter = currencyService.lastUpdate;
        //  Allow Update
        currencyService.lastUpdate = 0;
        Currency eurAfter = currencyService.getCurrency(currency0.getAbreviation());

        assertThat(lastUpdateBefore).isLessThan(lastUpdateAfter);
        assertThat(eurAfter.getExchange_rate()).isLessThan(eurRateBefore);
    }

    @Test
     void whenSearchValidID_thenCurrencyshouldBeFound() {
        Currency found = currencyService.getCurrency(currency0.getAbreviation());
        assertThat(found.getExchange_rate()).isEqualTo(currency0.getExchange_rate());

        found = currencyService.getCurrency(currency1.getAbreviation());
        assertThat(found.getExchange_rate()).isEqualTo(currency1.getExchange_rate());
    }

    @Test
     void whenSearchInvalidID_thenCurrencyShouldNotBeFound() {
        Currency fromDb = currencyService.getCurrency("WLF");
        assertThat(fromDb).isNull();
    }

    @Test
     void given2Currencies_whengetAll_thenReturn2Records() {
        List<Currency> allCurrencies = currencyService.listCurrencies();

        assertThat(allCurrencies).hasSize(2).extracting(Currency::getAbreviation).contains(currency0.getAbreviation(), currency1.getAbreviation());
    }
}
