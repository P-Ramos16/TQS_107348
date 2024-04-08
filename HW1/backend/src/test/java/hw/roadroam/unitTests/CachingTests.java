package hw.roadroam.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;

import hw.roadroam.repositories.CurrencyRepo;
import hw.roadroam.services.CurrencyService;
import hw.roadroam.models.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
class CachingTests {

    @Mock(lenient = true)
    private CurrencyRepo currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    @Order(1)
    void testThatCachIsEmpty() {
        //  Prevent Update right now
        currencyService.lastUpdate = System.currentTimeMillis() / 1000;

        Currency eurBefore = currencyService.getCurrency("USD");

        assertThat(eurBefore).isNull();
        assertThat(currencyService.getCacheMisses()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void testCachingUpdate() {
        //  Force a cache reset on the next currency check

        assertThat(currencyService.getCacheMisses()).isEqualTo(0);

        currencyService.lastUpdate = 0;
        currencyService.getCurrency("EUR");

        long lastUpdateBefore = currencyService.lastUpdate;

        assertThat(currencyService.getCacheMisses()).isEqualTo(1);

        // Sleep for 35 seconds so cache is invalid
        try {
            Thread.sleep(35000);
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        //  Should update cache again
        currencyService.getCurrency("EUR");

        long lastUpdateAfter = currencyService.lastUpdate;

        assertThat(currencyService.getCacheMisses()).isEqualTo(2);

        //  Check that the update timer updated
        assertThat(lastUpdateBefore).isLessThan(lastUpdateAfter);
    }

}