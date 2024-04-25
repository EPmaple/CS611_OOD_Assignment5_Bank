import role.Customer;
import role.Manager;
import transaction.Transaction;
import stock.Market;

import java.util.ArrayList;
import java.util.List;

import static frontend.UserSelectionFrame.startGUI;

public class Bank {
    final private ATM atm;
    List<Manager> managers;
    List<Customer> customers;
    List<Transaction> transactions;
    Market market;


    public Bank(){
        this.managers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.market = new Market();

        this.atm = new ATM(managers, customers, transactions, market);
    }

    public void init() {
        System.out.println("creating the bank!");
    }

    public void start() {
        System.out.println("launching the bank app...");
        atm.login();
        atm.input();
    }
}
