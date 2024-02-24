package tqs;
import java.util.List;
import java.util.LinkedList;

public class StocksPortfolio {
    
    private IStockmarketService market;
    private List<Stock> stocks = new LinkedList<Stock>();


    public StocksPortfolio(IStockmarketService market) {
        this.market = market;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double totalValue() {
        double val = 0.0;
        
        for (Stock stock : stocks) {
            val += market.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
    
        return val;
    }
}
