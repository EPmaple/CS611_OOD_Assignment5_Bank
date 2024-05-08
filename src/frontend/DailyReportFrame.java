package frontend;

import javax.swing.*;

import account.BalanceListener;

import java.awt.event.*;
import java.sql.Time;
import java.awt.*;

import java.util.List;

import role.Manager;
import transaction.Stock_Transaction;
import transaction.Transaction;

/*
 * Listen to when there is a txn done !!!
 */

public class DailyReportFrame extends JFrame implements TimeModelListener, BalanceListener, CurrencyModelListener{
  private TimeModel tmInstance = TimeModel.getInstance();
  private Middleware mwInstance = Middleware.getInstance();
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  private Manager manager;

  private CardLayout cardLayout = new CardLayout();
  private JPanel cardPanel = new JPanel(cardLayout);

  public void timeUpdate() {
    String msg = "Another day has come.";
    regenerateFrame(msg);
  }

  public void balanceUpdated(String customer) {
    String msg = "A new transaction has occurred";
    regenerateFrame(msg);
  }

  public void currencyUpdate() {
    String msg = "The currency in use has been changed, or the current" +
    " currency's conversion rate has been changed.";
    regenerateFrame(msg);
  }

  private DailyReportFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    tmInstance.removeTimeModelListener(this);
    mwInstance.removeBalanceListener(this);
    cmInstance.removeCurrencyModelListener(this);
    this.dispose();
    DailyReportFrame reportFrame = new DailyReportFrame(manager);
    reportFrame.showWindow();
    return reportFrame;
  }

  private void deregisterListeners() {
    System.out.println("Deregistering listeners...");
    tmInstance.removeTimeModelListener(this);
    mwInstance.removeBalanceListener(this);
    cmInstance.removeCurrencyModelListener(this);
    System.out.println("Listeners deregistered.");
}

  
  public DailyReportFrame(Manager manager) {
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          deregisterListeners();
      }
    });

    tmInstance.addTimeModelListener(this);
    mwInstance.addBalanceListener(this);
    cmInstance.addCurrencyModelListener(this);
    
    this.manager = manager;

    int currentTime = tmInstance.getTime();

    JComboBox<String> jcbCurrency = cmInstance.createCurrencyComboBox();
    JLabel jlbTime = new JLabel("Select the time to view the txns for");
    JComboBox<String> jcbTime = createTimeComboBox();
    JPanel timePanel = new JPanel();
    timePanel.add(jcbCurrency);
    timePanel.add(jlbTime);
    timePanel.add(jcbTime);

    createPanelForEachDay(currentTime);

    add(timePanel, BorderLayout.NORTH);
    add(cardPanel, BorderLayout.CENTER);

  }

  public JComboBox<String> createTimeComboBox() {
    int currentTime = tmInstance.getTime(); // Assume tmInstance.getTime() returns the current time as int
    JComboBox<String> timeComboBox = new JComboBox<>();

    // Populate the JComboBox in reverse order from currentTime to 0
    for (int i = currentTime; i >= 0; i--) {
        timeComboBox.addItem("" + i);
    }

    timeComboBox.addActionListener(e -> 
    cardLayout.show(cardPanel, 
    timeComboBox.getItemAt(timeComboBox.getSelectedIndex()) ));

    return timeComboBox;
  }

  public void createPanelForEachDay(int currentTime) {
    for (int i = currentTime; i >= 0; i--) {
      String strTime = "" + i;

      List<Transaction> txns = manager.getTransactionToday(strTime);
      JTable txnTable = new JTable(convertTransactionsToData(txns), new String[]{"Name", "Acct Type", "Txn Type", "Txn Amt", "Time"});
      JScrollPane leftPane = new JScrollPane(txnTable);

      JLabel leftPaneHeader = new JLabel("Normal transactions");
      JPanel leftTotalPane = new JPanel(new BorderLayout());
      leftTotalPane.add(leftPaneHeader, BorderLayout.NORTH);
      leftTotalPane.add(leftPane, BorderLayout.CENTER);
  
      List<Stock_Transaction> stockTxns = manager.getStockTransactionToday(strTime);
      JTable stockTxnTable = new JTable(convertStockTransactionsToData(stockTxns), new String[]{"Name", "Stock", "Txn Type", "Num of Shares", "Txn Amt", "Time"});
      JScrollPane rightPane = new JScrollPane(stockTxnTable);

      JLabel rightPaneHeader = new JLabel("Stock transactions");
      JPanel rightTotalPane = new JPanel(new BorderLayout());
      rightTotalPane.add(rightPaneHeader, BorderLayout.NORTH);
      rightTotalPane.add(rightPane, BorderLayout.CENTER);
  
      JPanel mainPanel = new JPanel(new GridLayout(0, 2));
      mainPanel.add(leftTotalPane);
      mainPanel.add(rightTotalPane);
  
      cardPanel.add(mainPanel, strTime);
    }
  }

  private String[][] convertTransactionsToData(List<Transaction> transactions) {
    String[][] data = new String[transactions.size()][5];
    for (int i = 0; i < transactions.size(); i++) {
      Transaction txn = transactions.get(i);

      data[i][0] = txn.getName();
      data[i][1] = txn.getAccount_type();
      data[i][2] = txn.getType();
      double number = Double.parseDouble(txn.getNumber());
      data[i][3] = cmInstance.convertToCurrentCurrency(number);
      data[i][4] = txn.getTime();
    }
    return data;
  }

  private String[][] convertStockTransactionsToData(List<Stock_Transaction> transactions) {
    String[][] data = new String[transactions.size()][6];

    for (int i = 0; i < transactions.size(); i++) {
      /*
       * stockName, type(buy/sell), numOfShares, cost, time
       */
      Stock_Transaction stockTxs = transactions.get(i);
      data[i][0] = stockTxs.getName();
      data[i][1] = stockTxs.getStockName();
      data[i][2] = stockTxs.getType();
      double number = Double.parseDouble(stockTxs.getNumber());
      data[i][3] = cmInstance.convertToCurrentCurrency(number);
      data[i][4] = stockTxs.getCost();
      data[i][5] = stockTxs.getTime();
    }
    return data;
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Daily Reports on Transactions");
    this.setSize( 1000, 600 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
