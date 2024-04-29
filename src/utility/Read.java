package utility;

import account.CheckingAccount;
import account.SavingAccount;
import account.StockAccount;
import role.Customer;
import stock.Stock;
import stock.StockEntry;
import transaction.Stock_Transaction;
import transaction.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Read {
    public static List<Customer> readUsers() {
        try {
            File userFile = new File("storage/user.txt");
            Scanner customerReader = new Scanner(userFile);
            List<Customer> customerList= new ArrayList<Customer>();
            while (customerReader.hasNextLine()) {
                String userLine = customerReader.nextLine();
                String[] splits = userLine.split(",");
                Customer customer = new Customer(splits[0], splits[1], splits[2], splits[3],splits[4],splits[5],splits[6],splits[7]);
                customerList.add(customer);
            }
            customerReader.close();
            return customerList;
        } catch (FileNotFoundException e) {
            System.out.println("Exception: File Not Found.");
        }
        return new ArrayList<Customer>();
    }
    public static Customer get_customer(String name){
        List<Customer> customerList = readUsers();
        Customer re;
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).get_name().equals(name)) {
                re = customerList.get(i);
                return re;
            }
        }
        return null;
    }
    public static List<Transaction> readTransaction(){
        try {
            File userFile = new File("storage/transaction.txt");
            Scanner Reader = new Scanner(userFile);
            List<Transaction>  TransactionList = new ArrayList<Transaction>();
            while (Reader.hasNextLine()) {
                String userLine = Reader.nextLine();
                String[] splits = userLine.split(",");
                Transaction transaction = new Transaction(splits[0], splits[1], splits[2],splits[3],splits[4]);
                TransactionList.add(transaction);
            }
            Reader.close();
            return TransactionList;
        } catch (FileNotFoundException e) {
            System.out.println("Exception: File Not Found.");
        }
        return new ArrayList<Transaction>();
    }
    public static List<Transaction> getHistoryTransactionAccount(String name, String account_type){
        List<Transaction> TransactionList = readTransaction();
        List<Transaction> result = new ArrayList<Transaction>();
        for (int i = 0; i < TransactionList.size(); i++) {
            if (TransactionList.get(i).getName().equals(name) && TransactionList.get(i).getAccount_type().equals(account_type)) {
                result.add(TransactionList.get(i));
            }
        }
        return result;
    }
    public static List<CheckingAccount> readCheckingAccount(){
        try {
            File userFile = new File("storage/checkingAccount.txt");
            Scanner Reader = new Scanner(userFile);
            List<CheckingAccount>  checkingAccountList = new ArrayList<CheckingAccount>();
            while (Reader.hasNextLine()) {
                String userLine = Reader.nextLine();
                String[] splits = userLine.split(",");
                CheckingAccount checkingAccount = new CheckingAccount(splits[0], splits[1], splits[2]);
                checkingAccountList.add(checkingAccount);
            }
            Reader.close();
            return checkingAccountList;
        } catch (FileNotFoundException e) {
            System.out.println("Exception: File Not Found.");
        }
        return new ArrayList<CheckingAccount>();
    }
    public static CheckingAccount getCheckingAccount(String name){
        List<CheckingAccount>  checkingAccountList = readCheckingAccount();
        CheckingAccount re;
        for (int i = 0; i < checkingAccountList.size(); i++) {
            if (checkingAccountList.get(i).getName().equals(name)) {
                re = checkingAccountList.get(i);
                return re;
            }
        }
        return null;
    }
    public static List<SavingAccount> readSavingAccount(){
        try {
            File userFile = new File("storage/savingAccount.txt");
            Scanner Reader = new Scanner(userFile);
            List<SavingAccount>  savingAccountList = new ArrayList<SavingAccount>();
            while (Reader.hasNextLine()) {
                String userLine = Reader.nextLine();
                String[] splits = userLine.split(",");
                SavingAccount savingAccount = new SavingAccount(splits[0], splits[1], splits[2],splits[3]);
                savingAccountList.add(savingAccount);
            }
            Reader.close();
            return savingAccountList;
        } catch (FileNotFoundException e) {
            System.out.println("Exception: File Not Found.");
        }
        return new ArrayList<SavingAccount>();
    }
    public static SavingAccount getSavingAccount(String name){
        List<SavingAccount>  savingAccountList = readSavingAccount();
        SavingAccount re;
        for (int i = 0; i < savingAccountList.size(); i++) {
            if (savingAccountList.get(i).getName().equals(name)) {
                re = savingAccountList.get(i);
                return re;
            }
        }
        return null;
    }
    public static List<StockAccount> readStockAccount(){
        try {
            File userFile = new File("storage/stockAccount.txt");
            Scanner Reader = new Scanner(userFile);
            List<StockAccount>  stockAccountList = new ArrayList<StockAccount>();
            while (Reader.hasNextLine()) {
                String userLine = Reader.nextLine();
                String[] splits = userLine.split(",");
                StockAccount stockAccount = new StockAccount(splits[0], splits[1], splits[2]);
                stockAccountList.add(stockAccount);
            }
            Reader.close();
            return stockAccountList;
        } catch (FileNotFoundException e) {
            System.out.println("Exception: File Not Found.");
        }
        return new ArrayList<StockAccount>();
    }
    public static StockAccount getStockAccount(String name){
        List<StockAccount>  stockAccountList = readStockAccount();
        StockAccount re;
        for (int i = 0; i < stockAccountList.size(); i++) {
            if (stockAccountList.get(i).getName().equals(name)) {
                re = stockAccountList.get(i);
                return re;
            }
        }
        return null;
    }
    public static List<Stock> readStock(){
        try {
            File userFile = new File("storage/stock.txt");
            Scanner Reader = new Scanner(userFile);
            List<Stock>  stockList = new ArrayList<Stock>();
            while (Reader.hasNextLine()) {
                String userLine = Reader.nextLine();
                String[] splits = userLine.split(",");
                Stock stock = new Stock(splits[0], splits[1], splits[2]);
                stockList.add(stock);
            }
            Reader.close();
            return stockList;
        } catch (FileNotFoundException e) {
            System.out.println("Exception: File Not Found.");
        }
        return new ArrayList<Stock>();
    }
    public static Stock getStock(String name){
        List<Stock>  stockList = readStock();
        Stock re;
        for (int i = 0; i < stockList.size(); i++) {
            if (stockList.get(i).getName().equals(name)) {
                re = stockList.get(i);
                return re;
            }
        }
        return null;
    }
    public static List<Stock_Transaction> readStockTransaction(){
        try {
            File userFile = new File("storage/stockTransaction.txt");
            Scanner Reader = new Scanner(userFile);
            List<Stock_Transaction>  TransactionList = new ArrayList<Stock_Transaction>();
            while (Reader.hasNextLine()) {
                String userLine = Reader.nextLine();
                String[] splits = userLine.split(",");
                Stock_Transaction transaction = new Stock_Transaction(splits[0], splits[1], splits[2],splits[3],splits[4],splits[5]);
                TransactionList.add(transaction);
            }
            Reader.close();
            return TransactionList;
        } catch (FileNotFoundException e) {
            System.out.println("Exception: File Not Found.");
        }
        return new ArrayList<Stock_Transaction>();
    }
    public static List<Stock_Transaction> getHistoryStockTransactionAccount(String name){
        List<Stock_Transaction> TransactionList = readStockTransaction();
        List<Stock_Transaction> result = new ArrayList<Stock_Transaction>();
        for (int i = 0; i < TransactionList.size(); i++) {
            if (TransactionList.get(i).getName().equals(name)) {
                result.add(TransactionList.get(i));
            }
        }
        return result;
    }
    public static List<Stock_Transaction> getHistoryStockTransaction(String stock_name){
        List<Stock_Transaction> TransactionList = readStockTransaction();
        List<Stock_Transaction> result = new ArrayList<Stock_Transaction>();
        for (int i = 0; i < TransactionList.size(); i++) {
            if (TransactionList.get(i).getStockName().equals(stock_name)) {
                result.add(TransactionList.get(i));
            }
        }
        return result;
    }
    public static List<StockEntry> readStockHold(){
        try {
            File userFile = new File("storage/stockHold.txt");
            Scanner Reader = new Scanner(userFile);
            List<StockEntry>  EntrtList = new ArrayList<StockEntry>();
            while (Reader.hasNextLine()) {
                String userLine = Reader.nextLine();
                String[] splits = userLine.split(",");
                StockEntry entry = new StockEntry(splits[0], splits[1], splits[2]);
                EntrtList.add(entry);
            }
            Reader.close();
            return EntrtList;
        } catch (FileNotFoundException e) {
            System.out.println("Exception: File Not Found.");
        }
        return new ArrayList<StockEntry>();
    }
    public static List<StockEntry> getStockHoldUser(String name){
        List<StockEntry> EntryList = readStockHold();
        List<StockEntry> result = new ArrayList<StockEntry>();
        for (int i = 0; i <EntryList.size(); i++) {
            if (EntryList.get(i).getName().equals(name)) {
                result.add(EntryList.get(i));
            }
        }
        return result;
    }
    public static List<StockEntry> getStockHoldStock(String stockName){
        List<StockEntry> EntryList = readStockHold();
        List<StockEntry> result = new ArrayList<StockEntry>();
        for (int i = 0; i <EntryList.size(); i++) {
            if (EntryList.get(i).getStockName().equals(stockName)) {
                result.add(EntryList.get(i));
            }
        }
        return result;
    }
}
