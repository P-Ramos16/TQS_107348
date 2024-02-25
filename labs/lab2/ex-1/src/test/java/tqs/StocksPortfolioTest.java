/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


/**
 * @author pramos
 */
@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {

    @Mock
    private IStockmarketService market;

    @InjectMocks
    private StocksPortfolio portfolio;

    @BeforeEach
    public void setUp() {
        assertNotNull(market);

        market = mock(IStockmarketService.class);
        portfolio = new StocksPortfolio(market);

        portfolio.addStock(new Stock("UAV", 3));
        portfolio.addStock(new Stock("CTT", 1));
        portfolio.addStock(new Stock("CMC", 8));
        portfolio.addStock(new Stock("WFD", 12));

        when(market.lookUpPrice("UAV")).thenReturn(12.3);
        when(market.lookUpPrice("CTT")).thenReturn(7.7);
        when(market.lookUpPrice("CMC")).thenReturn(3.1);
        when(market.lookUpPrice("WFD")).thenReturn(4.5);
        when(market.lookUpPrice("STM")).thenReturn(10.0);
    }


    @Test
    public void testGetTotalValue() {

        Double startingValue = portfolio.totalValue();

        //  Sucessfully assert that the total value is correct
        assertThat(startingValue, notNullValue());
        assertThat(startingValue, equalTo(123.4));

        //  Verify that each mock method was called exactly once
        verify(market, times(1)).lookUpPrice("UAV");
        verify(market, times(1)).lookUpPrice("CTT");
        verify(market, times(1)).lookUpPrice("CMC");
        verify(market, times(1)).lookUpPrice("WFD");

        //  Add a new stock to the portfolio and assert that the final value changes to the correct value
        portfolio.addStock(new Stock("STM", 1));
        assertThat(startingValue + market.lookUpPrice("STM"), equalTo(portfolio.totalValue()));

        //  Verify that all mock methods have been called the correct number of times 
        verify(market, times(10)).lookUpPrice(anyString());
    }
}
