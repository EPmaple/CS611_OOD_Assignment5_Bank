package stock;

public class StockEntry {
    private int number;
    private String name;
    private String stockName;
    public StockEntry(String name,String number, String stockName){
        this.name = name;
        this.number = Integer.parseInt(number);
        this.stockName = stockName;
    }

    public int getNumber() {
        return number;
    }
    public String getName(){
        return name;
    }

    public String getStockName() {
        return stockName;
    }
    public void setNumber(int number){
        this.number = number;
    }

    @Override
    public String toString(){
        return name+","+number+","+stockName;
    }
}
