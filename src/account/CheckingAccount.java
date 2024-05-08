package account;

import transaction.Transaction;
import utility.Read;
import utility.Write;

import java.util.*;

import frontend.Middleware;
import frontend.TimeModel;

public class CheckingAccount extends Account{
    private Middleware mwInstance = Middleware.getInstance();
    private TimeModel tmInstance = TimeModel.getInstance();
    // ***************************************

    private String name;
    private double balance=0;
    private String type;
    private List<Transaction> transactionList;
    public CheckingAccount(String name,String balance,String type,List<Transaction> transactionList ){
        this.name = name;
        this.balance = Double.parseDouble(balance);
        this.type = type;
        this.transactionList = transactionList;
    }
    public CheckingAccount(String name,String balance,String type){
        this.name = name;
        this.balance = Double.parseDouble(balance);
        this.type = type;
    }
    public String toString(){
        return name+","+balance+","+type;
    }
    public void updateTransactionList(){
        this.transactionList = Read.getHistoryTransactionAccount(name,type);
    }

    public List<Transaction> getTransactionList() {
        this.updateTransactionList();
        return transactionList;
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
    public void getLoan(double amount){
        this.balance+=amount;
        Write.rewriteCheckingAccount(this);
        String time = tmInstance.getTime() + "";
        Write.writeTransaction(new Transaction(name,type,"getLoan",String.valueOf(amount),time));
        // mwInstance.notifyBalanceUpdated(this.name);
    }
    public boolean payLoan(double amount){
        if(balance<amount){
            return false;
        }
        this.balance-=amount;
        Write.rewriteCheckingAccount(this);
        String time = tmInstance.getTime() + "";
        Write.writeTransaction(new Transaction(name,type,"payLoan",String.valueOf(amount),time));
        // mwInstance.notifyBalanceUpdated(this.name);
        return true;
    }
    public void transferIn(double amount){
        this.balance+=amount;
        Write.rewriteCheckingAccount(this);
        String time = tmInstance.getTime() + "";
        Write.writeTransaction(new Transaction(name,type,"transferIn",String.valueOf(amount),time));
        mwInstance.notifyBalanceUpdated(this.name);
    }
    public boolean transferOut(double amount){
        if(balance<amount* (1.0+Account.transfer_rate)){
            return false;
        }
        this.balance-=amount* (1.0+Account.transfer_rate);
        Write.rewriteCheckingAccount(this);
        String time = tmInstance.getTime() + "";
        Write.writeTransaction(new Transaction(name,type,"transferOut",String.valueOf(amount* (1.0+Account.transfer_rate)),time));
        mwInstance.notifyBalanceUpdated(this.name);
        return true;
    }
    public void deleteAccount(){
        Write.deleteCheckingAccount(this);
    }
}
