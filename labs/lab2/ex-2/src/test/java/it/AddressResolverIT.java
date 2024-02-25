/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tqs.Address;
import tqs.AddressResolverService;
import tqs.ISimpleHttpClient;
import tqs.TqsBasicHttpClient;

import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


import java.util.Optional;


/**
 * @author pramos
 */
class AddressResolverIT {

    private ISimpleHttpClient httpClient;

    private AddressResolverService addressResolver;

    @BeforeEach
    public void setUp() {
        httpClient = new TqsBasicHttpClient();
        assertNotNull(httpClient);

        addressResolver = new AddressResolverService(httpClient);
        assertNotNull(addressResolver);
    }


    @Test
    public void testGetTotalValue() {

        try {
            Optional<tqs.Address> add = addressResolver.findAddressForLocation(39.33963264507241, -9.32593877856922);
            assertThat(add.get(), notNullValue());

            Address address = add.get();

            //  Sucessfully assert that the total value is correct
            assertThat(address.getCity(), equalTo("Peniche"));
            assertThat(address.getRoad(), equalTo("N114"));
            assertThat(address.getZio(), equalTo("2525-024"));

            add = addressResolver.findAddressForLocation(240.00000, -409.00000);
            assertThat(add, is(Optional.empty()));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
