/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.IStockmarketService;
import tqs.StocksPortfolio;
import tqs.Stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


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
    }


    @Test
    public void testGetTotalValue() {

        double startingValue = portfolio.totalValue();

        //  Sucessfully add an element to a set
        assertEquals(startingValue, 123.4, "total value: total value is incorrect.");

        //  Wrongfully add an element to a full set
        verify(market).lookUpPrice("UAV");
        verify(market).lookUpPrice("CTT");
        verify(market).lookUpPrice("CMC");
        verify(market).lookUpPrice("WFD");
    }
}
