/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import tqs.ConfigUtils;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.Address;
import tqs.AddressResolverService;
import tqs.ISimpleHttpClient;

import java.util.Optional;


/**
 * @author pramos
 */
@ExtendWith(MockitoExtension.class)
class AddressResolverServiceTest {

    @Mock
    private ISimpleHttpClient httpClient;

    @InjectMocks
    private AddressResolverService addressResolver;

    @BeforeEach
    public void setUp() {
        assertNotNull(httpClient);

        httpClient = mock(ISimpleHttpClient.class);
        addressResolver = new AddressResolverService(httpClient);

        String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");

        try {
            when(httpClient.doHttpGet("https://www.mapquestapi.com/geocoding/v1/reverse?key=" + apiKey + "&location=39.00000%2C-9.00000&outFormat=json&thumbMaps=false")).thenReturn("{\"results\": [{\"providedLocation\": {\"latLng\": {\"lat\": 39.00000,\"lng\": -9.00000 }},\"locations\": [{\"street\": \"Rua Primeiro de Maio 11\",\"adminArea5\": \"Cadafais\",\"postalCode\": \"2025-123\"}]}]}");
            when(httpClient.doHttpGet("https://www.mapquestapi.com/geocoding/v1/reverse?key=" + apiKey + "&location=240.00000%2C-409.00000&outFormat=json&thumbMaps=false")).thenReturn("{\"results\": [{\"locations\": []}]}");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetTotalValue() {

        try {
            Optional<tqs.Address> add = addressResolver.findAddressForLocation(39.00000, -9.00000);
            assertThat(add.get(), notNullValue());

            Address address = add.get();

            //  Sucessfully assert that the total value is correct
            assertThat(address.getCity(), equalTo("Cadafais"));
            assertThat(address.getRoad(), equalTo("Rua Primeiro de Maio 11"));
            assertThat(address.getZio(), equalTo("2025-123"));

            //  Verify that all mock methods have been called the correct number of times 
            verify(httpClient, times(1)).doHttpGet(anyString());

            add = addressResolver.findAddressForLocation(240.00000, -409.00000);
            assertThat(add, is(Optional.empty()));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
