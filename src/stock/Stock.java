package stock;

import utility.Read;
import utility.Write;

public class Stock {
    private String name;
    private double price;
    private boolean onSale;
    public Stock(String name,String price,String onSale){
        this.name = name;
        this.price = Double.parseDouble(price);
        this.onSale = onSale.equals("true");
    }
    public double getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public boolean isOnSale(){
        return onSale;
    }
    public void setPrice(double price){
        this.price = price;
        Write.rewriteStock(this);
    }
    public void setOnSale(boolean val){
        onSale = val;
        Write.rewriteStock(this);
    }
    @Override
    public String toString(){
        return name+","+price+","+onSale;
    }
}
