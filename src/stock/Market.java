package stock;

import utility.Read;

import java.util.ArrayList;
import java.util.List;

public class Market {
    private List<Stock> stocks;
    private static Market market;

    private Market() {
        this.stocks = new ArrayList<>();
    }
    public void update(){
        market.stocks = Read.readStock();
    }
    public static Market getInstance(){
        if(market == null){
            market = new Market();
        }
        market.update();
        return market;
    }
    public List<Stock> getStocks(){
        return stocks;
    }
    public Stock getStock(String stockName){
        for(int i = 0; i<stocks.size();i++){
            if(stocks.get(i).getName().equals(stockName)){
                return stocks.get(i);
            }
        }
        return null;
    }
}
