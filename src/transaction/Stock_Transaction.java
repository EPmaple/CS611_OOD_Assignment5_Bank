package transaction;

public class Stock_Transaction extends Transaction{
    private String stockName;
    private String cost;
    public Stock_Transaction(String name, String stock_name, String type, String number, String cost, String time){
        super(name,"",type,number,time);
        this.stockName = stock_name;
        this.cost = cost;
    }
    @Override
    public String toString(){
        return name+","+stockName+","+this.type+","+number+","+cost+","+time;
    }
    public String getStockName(){
        return stockName;
    }
    public String getCost(){
        return cost;
    }

}

