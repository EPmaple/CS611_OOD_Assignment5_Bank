import role.Customer;
import role.EnumRole;
import role.Manager;
import role.User;
import transaction.Deposit;
import transaction.Transaction;
import stock.Market;

import static frontend.UserSelectionFrame.startGUI;

import java.util.List;
import java.util.UUID;

public class ATM {
    List<Manager> managers;
    List<Customer> customers;
    List<Transaction> transactions;
    User currentUser;

    public ATM(List<Manager> managers, List<Customer> customers, List<Transaction> transactions, Market market){
        this.managers = managers;
        this.customers = customers;
        this.transactions = transactions;

    }

    public void login(){
        System.out.println("Please login");
        this.currentUser = new User("manager_0", EnumRole.Manager); // TODO

        startGUI();
    }

    public void input(){
        System.out.println("Please create your transaction (deposits/withdrawal/trade) : "); // TODO
        Transaction t = new Deposit(UUID.randomUUID(), 999); // the user chose to deposit $999
    }

    private void run(Transaction t){
        // TODO
    }
}
