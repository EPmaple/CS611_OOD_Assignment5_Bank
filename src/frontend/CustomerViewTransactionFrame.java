package frontend;

import javax.swing.*;

import account.BalanceListener;
import account.CheckingAccount;
import account.SavingAccount;
import account.StockAccount;

import java.awt.event.*;
import java.awt.*;

import transaction.*;
import java.util.List;
import java.util.ArrayList;

import role.Customer;

public class CustomerViewTransactionFrame extends JFrame implements CurrencyModelListener, BalanceListener{
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  private Middleware mwInstance = Middleware.getInstance();
  private Customer customer;
  private CardLayout cardLayout = new CardLayout();
  private JPanel cardPanel = new JPanel(cardLayout); // This panel will contain all the cards
  
  public CustomerViewTransactionFrame(Customer customer) {
    this.customer = customer;
    cmInstance.addCurrencyModelListener(this);
    mwInstance.addBalanceListener(this);

    if (customer.has_check_account() || customer.has_saving_account() || customer.has_stock_account()) {
      setupTransactionTables();
      setupCardSwitchingComponents();
      add(cardPanel, BorderLayout.CENTER);
    } 

  }

  private void setupCardSwitchingComponents() {
    JPanel buttonPanel = new JPanel();

    JComboBox<String> jcbCurrencyOptions = cmInstance.createCurrencyComboBox();
    jcbCurrencyOptions.addActionListener(e -> {
      String selectedCurrency = jcbCurrencyOptions.getItemAt(jcbCurrencyOptions.getSelectedIndex());

      if (!selectedCurrency.equals(cmInstance.getCurrentCurrency())) {
        cmInstance.setCurrentCurrency(selectedCurrency);
      }
    });
    buttonPanel.add(jcbCurrencyOptions);

    if (customer.has_check_account()) {
      JButton checkingButton = new JButton("Checking Account");
      checkingButton.addActionListener(e -> cardLayout.show(cardPanel, "Checking"));
      buttonPanel.add(checkingButton);
    }

    if (customer.has_saving_account()) {
      JButton savingButton = new JButton("Saving Account");
      savingButton.addActionListener(e -> cardLayout.show(cardPanel, "Saving"));
      buttonPanel.add(savingButton);
    }

    if (customer.has_stock_account()) {
      JButton stockButton = new JButton("Stock Account");
      stockButton.addActionListener(e -> cardLayout.show(cardPanel, "Stock"));
      buttonPanel.add(stockButton);
    }

    add(buttonPanel, BorderLayout.NORTH);
}

  private void setupTransactionTables() {
    // Checking account transactions
    if (customer.has_check_account()) {
      CheckingAccount checkingAccount = customer.getCheckingAccount();
      List<Transaction> checkingTxnList = checkingAccount.getTransactionList();
      JTable checkingTable = new JTable(convertTransactionsToData(checkingTxnList), new String[]{"Txn Type", "Txn Amt", "Time"});
      cardPanel.add(new JScrollPane(checkingTable), "Checking");
    }

    // Saving account transactions
    if (customer.has_saving_account()) {
      SavingAccount savingAccount = customer.getSavingAccount();
      List<Transaction> savingTxnList = savingAccount.getTransactionList();
      JTable savingTable = new JTable(convertTransactionsToData(savingTxnList), new String[]{"Txn Type", "Txn Amt", "Time"});
      cardPanel.add(new JScrollPane(savingTable), "Saving");
    }

    // Stock account transactions
    if (customer.has_stock_account()) {
      StockAccount stockAccount = customer.getStockAccount();

      List<Stock_Transaction> stockTxnList = stockAccount.getStockTransactionList();
      JTable stockTable = new JTable(convertStockTransactionsToData(stockTxnList), new String[]{"Stock Name", "Txn Type", "Num Of Shares", "Cost", "Time"});
      JScrollPane leftPane = new JScrollPane(stockTable);

      List<Transaction> txnList = stockAccount.getTransactionList();
      JTable normalTxnTable = new JTable(convertTransactionsToData(txnList), new String[]{"Txn Type", "Txn Amt", "Time"});
      JScrollPane rightPane = new JScrollPane(normalTxnTable);

      JPanel mainStockPanel = new JPanel(new GridLayout(0, 2));
      mainStockPanel.add(leftPane);
      mainStockPanel.add(rightPane);

      cardPanel.add(new JScrollPane(mainStockPanel), "Stock");
    }

  }

  private Object[][] convertTransactionsToData(List<Transaction> transactions) {
    Object[][] data = new Object[transactions.size()][3];
    for (int i = 0; i < transactions.size(); i++) {
      Transaction txn = transactions.get(i);
      data[i][0] = txn.getType();
      double number = Double.parseDouble(txn.getNumber());
      data[i][1] = cmInstance.convertToCurrentCurrency(number);
      data[i][2] = txn.getTime();
    }
    return data;
}

  private String[][] convertStockTransactionsToData(List<Stock_Transaction> transactions) {
    String[][] data = new String[transactions.size()][5];

    for (int i = 0; i < transactions.size(); i++) {
      /*
       * stockName, type(buy/sell), numOfShares, cost, time
       */
      Stock_Transaction stockTxs = transactions.get(i);
      data[i][0] = stockTxs.getStockName();
      data[i][1] = stockTxs.getType();
      double number = Double.parseDouble(stockTxs.getNumber());
      data[i][2] = cmInstance.convertToCurrentCurrency(number);
      data[i][3] = stockTxs.getCost();
      data[i][4] = stockTxs.getTime();
    }
    return data;
  }

  public void currencyUpdate() {
    String msg = "The currency in use has been changed, or the current" +
    " currency's conversion rate has been changed.";
    regenerateFrame(msg);
  }

  public void balanceUpdated(String customerName) {
    if (customerName.equals(customer.get_name())) {
      String msg = "There is an update to transactions occurred.";
      regenerateFrame(msg);
    }
  }

  public CustomerViewTransactionFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    cmInstance.removeCurrencyModelListener(this);
    mwInstance.removeBalanceListener(this);
    this.dispose();
    CustomerViewTransactionFrame txnFrame = new CustomerViewTransactionFrame(customer);
    txnFrame.showWindow();
    return txnFrame;
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Transactions of " + customer.get_name());
    this.setSize( 850, 500 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}