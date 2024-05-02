package utility;

import account.CheckingAccount;
import account.SavingAccount;
import account.StockAccount;
import role.Customer;
import stock.Stock;
import stock.StockEntry;
import transaction.Stock_Transaction;
import transaction.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Write {
    public static void writeTime(int currentTime) {
        try (PrintWriter writer = new PrintWriter("storage/time.txt")) {
            writer.print(currentTime);  // Using print() to avoid adding a newline character
        } catch (IOException e) {
            System.out.println("Exception: Unable to write to the file.");
        }
    }

    public static void writeUsers(List<Customer> customerList){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/user.txt", false));
            for(Customer customer:customerList){
                out.write(customer.toString() + "\n");
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rewriteUserInfo(Customer customer) {
        List<Customer> customerList= Read.readUsers();
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).get_id().equals(customer.get_id())) {
                customerList.remove(i);
                break;
            }
        }
        customerList.add(customer);
        writeUsers(customerList);
    }
    public static void writeTransaction(Transaction transaction){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/transaction.txt", true));
            out.write(transaction.toString() + "\n");
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeCheckingAccount(List<CheckingAccount> checkingAccountList){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/checkingAccount.txt", false));
            for(CheckingAccount checkingAccount:checkingAccountList){
                out.write(checkingAccount.toString() + "\n");
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rewriteCheckingAccount(CheckingAccount checkingAccount) {
        List<CheckingAccount> checkingAccountList= Read.readCheckingAccount();
        for (int i = 0; i < checkingAccountList.size(); i++) {
            if (checkingAccountList.get(i).getName().equals(checkingAccount.getName())) {
                checkingAccountList.remove(i);
                break;
            }
        }
        checkingAccountList.add(checkingAccount);
        writeCheckingAccount(checkingAccountList);
    }
    public static void writeSavingAccount(List<SavingAccount> savingAccountList){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/savingAccount.txt", false));
            for(SavingAccount savingAccount:savingAccountList) {
                out.write(savingAccount.toString() + "\n");
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rewriteSavingAccount(SavingAccount savingAccount) {
        List<SavingAccount> savingAccountList= Read.readSavingAccount();
        for (int i = 0; i < savingAccountList.size(); i++) {
            if (savingAccountList.get(i).getName().equals(savingAccount.getName())) {
                savingAccountList.remove(i);
                break;
            }
        }
        savingAccountList.add(savingAccount);
        writeSavingAccount(savingAccountList);
    }
    public static void writeStockAccount(List<StockAccount> StockAccountList){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/stockAccount.txt", false));
            for(StockAccount stockAccount:StockAccountList) {
                out.write(stockAccount.toString() + "\n");
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rewriteStockAccount(StockAccount stockAccount) {
        List<StockAccount> stockAccountList= Read.readStockAccount();
        for (int i = 0; i < stockAccountList.size(); i++) {
            if (stockAccountList.get(i).getName().equals(stockAccount.getName())) {
                stockAccountList.remove(i);
                break;
            }
        }
        stockAccountList.add(stockAccount);
        writeStockAccount(stockAccountList);
    }
    public static void writeStock(List<Stock> StockList){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/stock.txt", false));
            for (Stock stock:StockList){
                out.write(stock.toString() + "\n");
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rewriteStock(Stock stock) {
        List<Stock> stockList= Read.readStock();
        for (int i = 0; i < stockList.size(); i++) {
            if (stockList.get(i).getName().equals(stock.getName())) {
                stockList.remove(i);
                break;
            }
        }
        stockList.add(stock);
        writeStock(stockList);
    }
    public static void writeStockTransaction(Stock_Transaction stockTransaction){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/stockTransaction.txt", true));
            out.write(stockTransaction.toString() + "\n");
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeStockHold(List<StockEntry> EntryList){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/stockHold.txt", false));
            for(StockEntry stockEntry:EntryList){
                out.write(stockEntry.toString() + "\n");
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rewriteStockHold(StockEntry entry) {
        List<StockEntry> EntryList= Read.readStockHold();
        for (int i = 0; i < EntryList.size(); i++) {
            if (EntryList.get(i).getName().equals(entry.getName())) {
                EntryList.remove(i);
                break;
            }
        }
        if(entry.getNumber()>0){
            EntryList.add(entry);
        }
        writeStockHold(EntryList);
    }
    public static void writeStockPrice(Stock stock){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("storage/stockHistoryPrice.txt", true));
            out.write(stock.getName() + "," + stock.getPrice() + "\n");
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteSavingAccount(SavingAccount savingAccount){
        List<SavingAccount> savingAccountList= Read.readSavingAccount();
        for (int i = 0; i < savingAccountList.size(); i++) {
            if (savingAccountList.get(i).getName().equals(savingAccount.getName())) {
                savingAccountList.remove(i);
                break;
            }
        }
        writeSavingAccount(savingAccountList);
    }
    public static void deleteCheckingAccount(CheckingAccount checkingAccount) {
        List<CheckingAccount> checkingAccountList= Read.readCheckingAccount();
        for (int i = 0; i < checkingAccountList.size(); i++) {
            if (checkingAccountList.get(i).getName().equals(checkingAccount.getName())) {
                checkingAccountList.remove(i);
                break;
            }
        }
        writeCheckingAccount(checkingAccountList);
    }
    public static void deleteStockAccount(StockAccount stockAccount){
        List<StockAccount> stockAccountList= Read.readStockAccount();
        for (int i = 0; i < stockAccountList.size(); i++) {
            if (stockAccountList.get(i).getName().equals(stockAccount.getName())) {
                stockAccountList.remove(i);
                break;
            }
        }
        writeStockAccount(stockAccountList);
        List<StockEntry> EntryList= Read.readStockHold();
        for (int i = 0; i < EntryList.size(); i++) {
            if (EntryList.get(i).getName().equals(stockAccount.getName())) {
                EntryList.remove(i);
                break;
            }
        }
        writeStockHold(EntryList);

    }
}
