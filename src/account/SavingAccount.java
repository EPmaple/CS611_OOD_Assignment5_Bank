package account;

import transaction.Transaction;
import utility.Read;
import utility.Write;

import java.util.List;

public class SavingAccount {
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
        Write.writeTransaction(new Transaction(name,type,"transferIn",String.valueOf(amount),"0"));
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
        Write.writeTransaction(new Transaction(name,type,"transferOut",String.valueOf(amount* (1.0+Account.transfer_rate)),"0"));
        return true;
    }
}
