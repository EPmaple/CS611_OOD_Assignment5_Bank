package account;

import transaction.Transaction;
import utility.Read;
import utility.Write;

import java.util.ArrayList;
import java.util.List;

import frontend.Middleware;
import frontend.TimeModel;

public class SavingAccount extends Account{
    private Middleware mwInstance = Middleware.getInstance();
    private TimeModel tmInstance = TimeModel.getInstance();
    // ***************************************

    private String name;
    private double balance=0;
    private String type;
    private List<Transaction> transactionList;
    private boolean hasStockingAccount = false;
    public SavingAccount(String name,String balance,String type,List<Transaction> transactionList,String hasStockingAccount){
        this.name = name;
        this.balance = Double.parseDouble(balance);
        this.type = type;
        this.transactionList = transactionList;
        this.hasStockingAccount = hasStockingAccount.equals("true");
    }
    public SavingAccount(String name,String balance,String type){
        this.name = name;
        this.balance = Double.parseDouble(balance);
        this.type = type;
    }
    public SavingAccount(String name,String balance,String type,String hasStockingAccount){
        this.name = name;
        this.balance = Double.parseDouble(balance);
        this.type = type;
        this.hasStockingAccount = hasStockingAccount.equals("true");
    }
    public String toString(){
        return name+","+balance+","+type+","+hasStockingAccount;
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
    public boolean isHasStockingAccount(){
        return hasStockingAccount;
    }
    public void setHasStockingAccount(boolean hasStockingAccount){
        this.hasStockingAccount = hasStockingAccount;
    }
    public void transferIn(double amount){
        this.balance+=amount;
        Write.rewriteSavingAccount(this);
        String time = tmInstance.getTime() + "";
        Write.writeTransaction(new Transaction(name,type,"transferIn",String.valueOf(amount),time));
        mwInstance.notifyBalanceUpdated(this.name);
    }
    public boolean transferOut(double amount){
        if(balance<amount* (1.0+Account.transfer_rate)){
            return false;
        }
        if(this.hasStockingAccount){
            if(balance<amount* (1.0+Account.transfer_rate)+Account.minimun_balance){
                return false;
            }
        }
        this.balance-=amount* (1.0+Account.transfer_rate);
        Write.rewriteSavingAccount(this);
        String time = tmInstance.getTime() + "";
        Write.writeTransaction(new Transaction(name,type,"transferOut",String.valueOf(amount* (1.0+Account.transfer_rate)),time));
        mwInstance.notifyBalanceUpdated(this.name);
        return true;
    }

    public void addInterest(){
        this.balance+=this.balance*Account.interest_rate;
        Write.rewriteSavingAccount(this);
    }
    public void deleteAccount(){
        Write.deleteSavingAccount(this);
        mwInstance.notifyBalanceUpdated(this.name);
    }
}
