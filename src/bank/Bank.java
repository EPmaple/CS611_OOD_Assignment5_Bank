package bank;
import role.Customer;
import role.Manager;
import transaction.Stock_Transaction;
import transaction.Transaction;
import stock.Market;
import utility.Read;

import java.util.ArrayList;
import java.util.List;

import static frontend.UserSelectionFrame.startGUI;

public class Bank {
    final private ATM atm;
    List<Manager> managers;
    List<Customer> customers;
    List<Transaction> transactions;
    private static Bank bank;

    private Bank(){
        this.managers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.transactions = new ArrayList<>();

        this.atm = new ATM(managers, customers, transactions, Market.getInstance());
    }
    public static Bank get_bank(){
        if(bank == null){
            bank = new Bank();
        }
        return bank;
    }
    public void init() {
        System.out.println("creating the bank!");
    }

    public void start() {
        System.out.println("launching the bank app...");
        atm.login();
        atm.input();
    }
    public boolean createUser(String name,String password){
        Customer customer = Read.get_customer(name);
        if(customer == null){
            customer = new Customer(name);
            customer.setPassword(password);
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        // Bank bank = Bank.get_bank();
        // bank.createUser("Gtl","123456");
        // Customer customer=Read.get_customer("Gtl");
        // customer.log_in("123456");
        // customer.createCheckingAccount();
        // customer.createSavingAccount();
        // customer.getCheckingAccount().transferIn(100);
        // customer.getSavingAccount().transferOut(100);
        // customer.getSavingAccount().transferIn(60000);
        // customer.createStockingAccount();
        // Manager.get_manager().createStock("Apple","1.5");
        // customer.getStockAccount().buyStock("Apple",50);
        // customer.getStockAccount().sellStock("Apple",10);
        // customer.request_loan(500);
        // customer.pay_loan(10);
    }

}