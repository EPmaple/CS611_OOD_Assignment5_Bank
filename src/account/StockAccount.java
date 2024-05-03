package account;

import stock.Market;
import stock.Stock;
import stock.StockEntry;
import transaction.Stock_Transaction;
import transaction.Transaction;
import utility.Read;
import utility.Write;

import java.util.List;

import frontend.Middleware;
import frontend.TimeModel;

public class StockAccount extends Account {
    private Middleware mwInstance = Middleware.getInstance();
    private TimeModel tmInstance = TimeModel.getInstance();
    private String name;
    private double balance=0;
    private String type;
    private List<Stock_Transaction> stockTransactionList;
    private List<Transaction> transactionList;
    private List<StockEntry> Holdlist;
    private boolean hasStockingAccount = false;
    public StockAccount(String name,String balance,String type){
        this.name = name;
        this.balance = Double.parseDouble(balance);
        this.type = type;
    }
    public String toString(){
        return name+","+balance+","+type;
    }
    public void updateStockTransactionList(){
        this.stockTransactionList = Read.getHistoryStockTransactionAccount(name);
    }
    public void updateHoldList(){
        this.Holdlist = Read.getStockHoldUser(name);
    }
    public void updateTransactionList(){
        this.transactionList = Read.getHistoryTransactionAccount(name,type);
    }
    public List<Transaction> getTransactionList() {
        this.updateTransactionList();
        return transactionList;
    }

    public List<Stock_Transaction> getStockTransactionList() {
        this.updateStockTransactionList();
        return stockTransactionList;
    }

    public List<StockEntry> getHoldlist() {
        this.updateHoldList();
        return Holdlist;
    }

    public String getName(){
        return name;
    }
    public double getBalance(){
        return balance;
    }
    public String getType(){
        return type;
    }
    public void transferIn(double amount){
        this.balance+=amount;
        Write.rewriteStockAccount(this);
        String time = tmInstance.getTime() + "";
        Write.writeTransaction(new Transaction(name,type,"transferIn",String.valueOf(amount),time));
        mwInstance.notifyBalanceUpdated(this.name);
    }
    public boolean transferOut(double amount){
        if(balance<amount* (1.0+Account.transfer_rate)){
            return false;
        }
        this.balance-=amount* (1.0+Account.transfer_rate);
        Write.rewriteStockAccount(this);
        String time = tmInstance.getTime() + "";
        Write.writeTransaction(new Transaction(name,type,"transferOut",String.valueOf(amount* (1.0+Account.transfer_rate)),time));
        mwInstance.notifyBalanceUpdated(this.name);
        return true;
    }
    public boolean buyStock(String stockName,int number){
        if(number<0){
            return false;
        }
        Stock stock = Market.getInstance().getStock(stockName);
        if(stock==null){
            return false;
        }
        if(!stock.isOnSale()){
            return false;
        }
        if(this.balance<number*stock.getPrice()){
            return false;
        }
        List<StockEntry> EntryList = Read.getStockHoldUser(name);
        StockEntry entry = null;
        for (int i = 0; i < EntryList.size(); i++) {
            if (EntryList.get(i).getStockName().equals(stockName)) {
                entry = EntryList.get(i);
                EntryList.remove(i);
                break;
            }
        }
        if(entry==null){
            entry = new StockEntry(name,String.valueOf(number),stockName);
        }else{
            entry.setNumber(entry.getNumber()+number);
        }
        String time = tmInstance.getTime() + "";
        Write.writeStockTransaction(new Stock_Transaction(name,stockName,"buy",String.valueOf(number),String.valueOf(number*stock.getPrice()),time));
        this.balance-=number*stock.getPrice();
        Write.rewriteStockAccount(this);
        Write.rewriteStockHold(entry);
        this.updateHoldList();
        this.updateTransactionList();
        mwInstance.notifyBalanceUpdated(this.name);
        return true;
    }
    public boolean sellStock(String stockName,int number){
        if(number<0){
            return false;
        }
        Stock stock = Market.getInstance().getStock(stockName);
        if(stock==null){
            return false;
        }
        List<StockEntry> EntryList = Read.getStockHoldUser(name);
        StockEntry entry = null;
        for (int i = 0; i < EntryList.size(); i++) {
            if (EntryList.get(i).getStockName().equals(stockName)) {
                entry = EntryList.get(i);
                EntryList.remove(i);
                break;
            }
        }
        int actual = number;
        if(entry==null){
            return false;
        }else{
            actual = entry.getNumber();
            entry.setNumber(entry.getNumber()-number);
        }
        if(entry.getNumber()<0){
            entry.setNumber(0);
        }else{
            actual = number;
        }
        String time = tmInstance.getTime() + "";
        Write.writeStockTransaction(new Stock_Transaction(name,stockName,"sale",String.valueOf(actual),String.valueOf(actual*stock.getPrice()),time));
        this.balance+=actual*stock.getPrice();
        Write.rewriteStockAccount(this);
        Write.rewriteStockHold(entry);
        this.updateHoldList();
        this.updateTransactionList();
        mwInstance.notifyBalanceUpdated(this.name);
        return true;
    }
    public double getProfitGet(){
        List<Stock_Transaction> list = Read.getHistoryStockTransactionAccount(name);
        double profit = 0;
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getType().equals("sale")){
                profit+= Double.parseDouble(list.get(i).getCost());
            }else{
                profit-= Double.parseDouble(list.get(i).getCost());
            }
        }
        return profit;
    }
    public void deleteAccount(){
        Write.deleteStockAccount(this);
    }
}


