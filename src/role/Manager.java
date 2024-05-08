package role;
import java.util.ArrayList;
import java.util.List;

import frontend.Middleware;
import stock.Market;
import stock.Stock;
import transaction.Stock_Transaction;
import transaction.Transaction;
import utility.Read;
import utility.Write;

public class Manager extends User {
    private Middleware mwInstance = Middleware.getInstance();
    private static Manager manager;
    private final String password;
    private Manager(String name,String password){
        super(name, EnumRole.Manager);
        this.password = password;
    }
    public static boolean log_in(String password){
        if(manager == null){
            manager = new Manager("manager","123456");
        }
        return password.equals(manager.password);

    }
    public static Manager get_manager(){
        if(manager == null){
            manager = new Manager("manager","123456");
        }
        return manager;
    }
    public boolean createStock(String stockName,String price){
        Stock stock = new Stock(stockName,price,"true");
        Write.rewriteStock(stock);
        Write.writeStockPrice(stock);
        mwInstance.notifyStockUpdated();
        return true;
    }
    public boolean changeStockPrice(String stockName,double price){
        Stock stock = Market.getInstance().getStock(stockName);
        if(stock == null){
            return false;
        }
        stock.setPrice(price);
        Write.writeStockPrice(stock);
        mwInstance.notifyStockUpdated();
        return true;
    }
    public boolean changeStockStatus(String stockName,boolean status){
        Stock stock = Market.getInstance().getStock(stockName);
        if(stock == null){
            return false;
        }
        stock.setOnSale(status);
        mwInstance.notifyStockUpdated();
        return true;
    }
    public List<Customer> getCustomerWithLoan(){
        List<Customer> customers = Read.readUsers();
        List<Customer> re = new ArrayList<>();
        for(int i =0;i<customers.size();i++){
            if(customers.get(i).get_has_loan()){
                re.add(customers.get(i));
            }
        }
        return re;
    }
    public List<Customer> getWealthyCustomer(){
        List<Customer> customers = Read.readUsers();
        List<Customer> re = new ArrayList<>();
        for(int i =0;i<customers.size();i++){
            if(customers.get(i).has_stock_account()){
                re.add(customers.get(i));
            }
        }
        return re;
    }
    public List<Transaction> getTransactionToday(String time){
        List<Transaction> transactions = Read.readTransaction();
        List<Transaction> re = new ArrayList<Transaction>();
        for(int i =0;i<transactions.size();i++){
            if(transactions.get(i).getTime().equals(time)){
                re.add(transactions.get(i));
            }
        }
        return re;
    }
    public List<Stock_Transaction> getStockTransactionToday(String time){
        List<Stock_Transaction> transactions = Read.readStockTransaction();
        List<Stock_Transaction> re = new ArrayList<Stock_Transaction>();
        for(int i =0;i<transactions.size();i++){
            if(transactions.get(i).getTime().equals(time)){
                re.add(transactions.get(i));
            }
        }
        return re;
    }
    public void addInterest(){
        List<Customer> customers = Read.readUsers();
        for(int i = 0;i<customers.size();i++){
            if(customers.get(i).has_saving_account()){
                customers.get(i).getSavingAccount().addInterest();
            }
        }
    }
}
