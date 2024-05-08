package frontend;

import javax.swing.*;

import account.BalanceListener;
import account.DeleteAccountListener;
import account.SavingAccount;
import account.StockAccount;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.awt.*;

import role.Customer;
import utility.Constants;
import utility.Read;
import stock.Stock;
import stock.StockEntry;
import stock.StockListener;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CustomerViewStocksFrame extends JFrame implements BalanceListener, CurrencyModelListener, StockListener, DeleteAccountListener{
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  private Middleware mwInstance = Middleware.getInstance();
  private Customer customer;

  private CardLayout cardLayout = new CardLayout();
  private JPanel cardPanel = new JPanel(cardLayout);

  private void deregisterListeners() {
    cmInstance.removeCurrencyModelListener(this);
    mwInstance.removeBalanceListener(this);
    mwInstance.removeStockListener(this);
    mwInstance.removeDeleteAccountListener(this);
  }

  public void accountDeleted(String customerName) {
    if (customerName.equals(customer.get_name())) {
      String msg = "One of your accounts has been deleted";
      regenerateFrame(msg);
    }
  }

  public CustomerViewStocksFrame(Customer customer) {
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          deregisterListeners();
      }
    });

    this.customer = customer;
    mwInstance.addBalanceListener(this);
    cmInstance.addCurrencyModelListener(this);
    mwInstance.addStockListener(this);
    mwInstance.addDeleteAccountListener(this);

    // ***************************

    JButton jbtBuyStocks = new JButton("Buy Stocks");
    jbtBuyStocks.addActionListener(e -> {
      cardLayout.show(cardPanel, "Buy Stocks");
    });
    JButton jbtSellStocks = new JButton("Sell Stocks");
    jbtSellStocks.addActionListener(e -> {
      cardLayout.show(cardPanel, "Sell Stocks");
    });
    JButton jbtToTransfer = new JButton("To Transfer");
    jbtToTransfer.addActionListener(e -> {
      cardLayout.show(cardPanel, "To Transfer");
    });
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(jbtBuyStocks);
    buttonPanel.add(jbtSellStocks);
    buttonPanel.add(jbtToTransfer);

    add(buttonPanel, BorderLayout.NORTH);
    add(cardPanel, BorderLayout.CENTER);

    createPanelsForTrade();

    JButton jbtDelete = new JButton("Delete Account");
    buttonPanel.add(jbtDelete);
    jbtDelete.addActionListener(e -> {
      // Show a confirmation dialog
      int confirmed = JOptionPane.showConfirmDialog(rootPane,
      "Do you want to delete this account?",
      "Confirm Account Deletion",
      JOptionPane.YES_NO_OPTION,
      JOptionPane.WARNING_MESSAGE);

      if (confirmed == JOptionPane.YES_OPTION) {
        // Logic to delete the account
        if (customer.deleteStockAccount()) {
          JOptionPane.showMessageDialog(rootPane, "Account deleted successfully.");

          // Close this window
          deregisterListeners();
          this.dispose();
        } else {
          String msg = "You cannot delete your securities account because "+
          "you currently hold stocks";
          JOptionPane.showMessageDialog(rootPane, msg);
        }

      }
    });
  }

  // class DeleteAccountListener implements ActionListener {
  //   public void actionPerformed(ActionEvent e) {
  //     // Show a confirmation dialog
  //     int confirmed = JOptionPane.showConfirmDialog(rootPane,
  //     "Do you want to delete this account?",
  //     "Confirm Account Deletion",
  //     JOptionPane.YES_NO_OPTION,
  //     JOptionPane.WARNING_MESSAGE);

  //     if (confirmed == JOptionPane.YES_OPTION) {
  //       // Logic to delete the account
  //       customer.deleteStockAccount();
  //       JOptionPane.showMessageDialog(rootPane, "Account deleted successfully.");

  //       // Close this window
  //       dispose();
  //     }
  //   }
  // }

  private void createPanelsForTrade() {
    createBuyPanel();
    createSellPanel();
    createTransferPanel();
  }

    private String[] getAvailableAccountsForTransfer() {
    List<String> options = new ArrayList<String>();

    StockAccount stockAccount = customer.getStockAccount();
    // this balance when first acquired is always DOLLAR
    double balance = stockAccount.getBalance();

    if (customer.has_check_account()) {
      options.add(Constants.CHECKING);
    }

    if (customer.has_saving_account()) {
      options.add(Constants.SAVING);
    } 

    return options.toArray(new String[0]);
  }

  private void createTransferPanel() {
    JLabel jlbSecuritiesBalance = new JLabel("Securities account currently has "+
    cmInstance.convertToCurrentCurrency(customer.getStockAccount().getBalance()));
    JLabel jlbMsg = new JLabel("Select the account you would like to "+
    "transfer to, and enter the amount you would like to transfer:");
    JTextField jtfTransfer = new JTextField("Enter amt to transfer");
    JComboBox<String> jcbOptions = new JComboBox<String>(getAvailableAccountsForTransfer());
    JButton jbtTransfer = new JButton("Confirm to Transfer");

    JPanel msgPanel = new JPanel(new GridLayout(2, 0));
    msgPanel.add(jlbSecuritiesBalance);
    msgPanel.add(jlbMsg);

    JPanel bodyPanel = new JPanel();
    bodyPanel.add(jcbOptions);
    bodyPanel.add(jtfTransfer);
    bodyPanel.add(jbtTransfer);

    JPanel transferPanel = new JPanel(new BorderLayout());
    transferPanel.add(msgPanel, BorderLayout.NORTH);
    transferPanel.add(bodyPanel, BorderLayout.CENTER);

    cardPanel.add(transferPanel, "To Transfer");

    jbtTransfer.addActionListener(e -> {
      try {
        double transferAmt = Double.parseDouble(jtfTransfer.getText());
        double transferInDollar = cmInstance.convertToCurrencyForStorage(transferAmt);
        StockAccount stockAccount = customer.getStockAccount();
        double saBalance = stockAccount.getBalance();

        if (transferInDollar < 0) {
          String msg = "To transfer, please enter a valid positive number";
          JOptionPane.showMessageDialog(rootPane, msg);

        } else if (transferInDollar > saBalance) {
          String msg = "The amount you are attempting to transfer is greater "+
          "than the account balance";
          JOptionPane.showMessageDialog(rootPane, msg);

        } else {
          String option = jcbOptions.getItemAt(jcbOptions.getSelectedIndex());
          stockAccount.transferOut(transferInDollar);
          if (option.equals(Constants.CHECKING)) {
            customer.getCheckingAccount().transferIn(transferInDollar);

          } else if (option.equals(Constants.SAVING)) {
            customer.getSavingAccount().transferIn(transferInDollar);
          }
        }
      } catch (NumberFormatException err) {
        String msg = "To transfer, please enter a valid positive number";
        JOptionPane.showMessageDialog(rootPane, msg);
      }
    });
  }

  private void createSellPanel() {
    JLabel jlbSecuritiesBalance = new JLabel("Securities account currently has "+
    cmInstance.convertToCurrentCurrency(customer.getStockAccount().getBalance()));
    JTextField jtfName = new JTextField("Stock name");
    JTextField jtfNumShares = new JTextField("Number of shares");

    JLabel jlbSell = new JLabel("Enter name of the stock to sell, and the number of shares to sell.");

    JButton jbtSell = new JButton("Confirm to sell");
    JPanel sellActionPanel = new JPanel(new GridLayout(0, 3));
    sellActionPanel.add(jtfName);
    sellActionPanel.add(jtfNumShares);
    sellActionPanel.add(jbtSell);

    JPanel header = new JPanel(new GridLayout(3, 0));
    header.add(jlbSecuritiesBalance);
    header.add(jlbSell);
    header.add(sellActionPanel);

    String columnHeaders[] = {"Stock Name", "Current Price", "Num of Shares"};

    JTable jt = new JTable(convertHoldsToData(), columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel sellPanel = new JPanel(new BorderLayout());
    sellPanel.add(header, BorderLayout.NORTH);
    sellPanel.add(sp, BorderLayout.CENTER);

    cardPanel.add(sellPanel, "Sell Stocks");

    jbtSell.addActionListener(e -> {
      tryToSellStock(jtfName.getText(), jtfNumShares.getText());
    });
  }

  private String[][] convertHoldsToData() {
    StockAccount stockAccount = customer.getStockAccount();
    List<StockEntry> stockEntries = stockAccount.getHoldlist();

    String[][] data = new String[stockEntries.size()][3];
    for (int i = 0; i < stockEntries.size(); i++) {
      StockEntry stockEntry = stockEntries.get(i);

      data[i][0] = stockEntry.getStockName();
      Stock stock = Read.getStock(stockEntry.getStockName());
      double price = stock.getPrice();
      data[i][1] = cmInstance.convertToCurrentCurrency(price);
      data[i][2] = stockEntry.getNumber() + "";
    }
    return data;
  }

  private void tryToSellStock(String inputStockName, String inputNumShares) {
    StockAccount stockAccount = customer.getStockAccount();
    // Stock stock = Read.getStock(inputStockName);
    try {
      int numShares = Integer.parseInt(inputNumShares);
      if (stockAccount.sellStock(inputStockName, numShares)) {
        String msg = "You've successfully sold " + numShares + " shares of " +
        inputStockName;
        JOptionPane.showMessageDialog(rootPane, msg);
        
      } else {
        String msg = "Make sure the stock name you've enter is correct, "+
        ", and the number of shares you want to sell is valid.";
        JOptionPane.showMessageDialog(rootPane, msg);
      }

    } catch (NumberFormatException err) {
      String msg = "Please enter a valid whole number of shares to buy";
      JOptionPane.showMessageDialog(rootPane, msg);
    }
  }

  private void createBuyPanel() {
    JLabel jlbSecuritiesBalance = new JLabel("Securities account currently has "+
    cmInstance.convertToCurrentCurrency(customer.getStockAccount().getBalance()));
    JTextField jtfName = new JTextField("Stock name");
    JTextField jtfNumShares = new JTextField("Number of shares");

    JLabel jlbBuy = new JLabel("Enter name of the stock to buy, and the number of shares to buy.");

    JButton jbtBuy = new JButton("Confirm to Buy");
    JPanel buyActionPanel = new JPanel(new GridLayout(0, 3));
    buyActionPanel.add(jtfName);
    buyActionPanel.add(jtfNumShares);
    buyActionPanel.add(jbtBuy);

    JPanel header = new JPanel(new GridLayout(3, 0));
    header.add(jlbSecuritiesBalance);
    header.add(jlbBuy);
    header.add(buyActionPanel);

    String columnHeaders[] = {"Stock Name", "Current Price", "Price Change"};

    JTable jt = new JTable(convertStocksToData(), columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel buyPanel = new JPanel(new BorderLayout());
    buyPanel.add(header, BorderLayout.NORTH);
    buyPanel.add(sp, BorderLayout.CENTER);

    cardPanel.add(buyPanel, "Buy Stocks");

    jbtBuy.addActionListener(e -> {
      tryToBuyStock(jtfName.getText(), jtfNumShares.getText());
    });
  }

  private String[][] convertStocksToData() {
    List<Stock> stocks = Read.readStock();

    String[][] data = new String[stocks.size()][3];
    for (int i = 0; i < stocks.size(); i++) {
      Stock stock = stocks.get(i);

      if (stock.isOnSale()) {
        data[i][0] = stock.getName();
        double price = stock.getPrice();
        data[i][1] = cmInstance.convertToCurrentCurrency(price);
        data[i][2] = getStockPriceChange(stock);
      }
    }
    return data;
  }

  private String getStockPriceChange(Stock stock) {
    List<Double> prices = stock.getHistoryPrice();

    if (prices.size() <= 1) {
        return "-";
    }

    // Get the last price and the one before it to calculate the change
    double lastPrice = prices.get(prices.size() - 1);
    double previousPrice = prices.get(prices.size() - 2);

    if (previousPrice == 0) {
        return "Previous price is zero, cannot calculate percentage change";
    }

    double percentageChange = ((lastPrice - previousPrice) / previousPrice) * 100;

    // Format the percentage change to two decimal places
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(percentageChange) + "%";
  }

  private void tryToBuyStock(String inputStockName, String inputNumShares) {
    StockAccount stockAccount = customer.getStockAccount();
    // Stock stock = Read.getStock(inputStockName);
    try {
      int numShares = Integer.parseInt(inputNumShares);
      if (stockAccount.buyStock(inputStockName, numShares)) {
        String msg = "You've successfully bought " + numShares + " of " +
        inputStockName;
        JOptionPane.showMessageDialog(rootPane, msg);
        
      } else {
        String msg = "Make sure the stock name you've enter is correct, "+
        ", the number of shares you want to buy is valid, and you have " +
        "enough balance in your stock account to buy them.";
        JOptionPane.showMessageDialog(rootPane, msg);
      }

    } catch (NumberFormatException err) {
      String msg = "Please enter a valid whole number of shares to buy";
      JOptionPane.showMessageDialog(rootPane, msg);
    }
  }

  public void balanceUpdated(String customerName) {
    if (customerName.equals(customer.get_name())) {
      String msg = "There is a balance update to one of your accounts";
      regenerateFrame(msg);
    }
  }

  public void currencyUpdate() {
    String msg = "The currency in use has been changed, or the current" +
    " currency's conversion rate has been changed.";
    regenerateFrame(msg);
  }

  public void stockUpdated() {
    String msg = "There is an update to the stock market.";
    regenerateFrame(msg);
  }

  private CustomerViewStocksFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    cmInstance.removeCurrencyModelListener(this);
    mwInstance.removeBalanceListener(this);
    mwInstance.removeStockListener(this);
    mwInstance.removeDeleteAccountListener(this);
    this.dispose();
    CustomerViewStocksFrame cvsFrame = new CustomerViewStocksFrame(customer);
    cvsFrame.showWindow();
    return cvsFrame;
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Customer View of Stocks");
    this.setSize( 1000, 600 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
