package frontend;

import role.*;

import javax.swing.*;

import account.*;
import utility.*;

import java.awt.event.*;
import java.awt.*;

public class CustomerFrame extends JFrame implements BalanceListener, CurrencyModelListener, AccountListener, TimeModelListener, DeleteAccountListener{
  private Middleware mwInstance = Middleware.getInstance();
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  private TimeModel timeInstance = TimeModel.getInstance(); 
  private Customer customer;
  // private CheckingAccount checkingAccount;
  private JButton jbtCheckingAccount = new JButton();

  private JButton jbtSavingAccount = new JButton();
  private JButton jbtStockingAccount = new JButton();

  // private JButton jbtCheckingAccount = new JButton();
  JPanel rightPanel = new JPanel(new GridLayout(5, 0));

  JComboBox<String> jcbCurrencyOptions = cmInstance.createCurrencyComboBox();

  public void accountDeleted(String customerName) {
    if (customerName.equals(customer.get_name())) {
      String msg = "One of your accounts has been deleted";
      regenerateFrame(msg);
    }
  }

  private void deregisterListeners() {
    cmInstance.removeCurrencyModelListener(this);
    mwInstance.removeBalanceListener(this);
    mwInstance.removeAccountListener(this);
    timeInstance.removeTimeModelListener(this);
    mwInstance.removeDeleteAccountListener(this);
  }

  public CustomerFrame(Customer customer) {
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          deregisterListeners();
      }
    });

    this.customer = customer;
    cmInstance.addCurrencyModelListener(this);
    mwInstance.addBalanceListener(this);
    mwInstance.addAccountListener(this);
    timeInstance.addTimeModelListener(this);
    mwInstance.addDeleteAccountListener(this);
    /*
     * If account info is null, or empty, then show create button, else take
     * the info and create another button that allows for viewing
     */
    // checking account button
    if (customer.getCheckingAccount() != null) {
      // balanceUpdated(Constants.CHECKING);
      jbtCheckingAccount.setText("checking: " + cmInstance.convertToCurrentCurrency(customer.getCheckingAccount().getBalance()));
      CheckingAccountListener caListener = new CheckingAccountListener();
      jbtCheckingAccount.addActionListener(caListener);

    } else {
      jbtCheckingAccount.setText("Create Checking Account");
      CreateCheckingAccountListener ccaListener = new CreateCheckingAccountListener();
      jbtCheckingAccount.addActionListener(ccaListener);

    }
    rightPanel.add(jbtCheckingAccount);

    // saving account button
    if (customer.getSavingAccount() != null) {
      // balanceUpdated(Constants.SAVING);
      jbtSavingAccount.setText("saving: " + cmInstance.convertToCurrentCurrency(customer.getSavingAccount().getBalance()));
      // create a saving account listener
      SavingAccountListener saListener = new SavingAccountListener();
      jbtSavingAccount.addActionListener(saListener);

    } else {
      jbtSavingAccount.setText("Create Saving Account");
      CreateSavingsAccountListener csaListener = new CreateSavingsAccountListener();
      jbtSavingAccount.addActionListener(csaListener);

    }
    rightPanel.add(jbtSavingAccount);

    // stocking account
    if (customer.getStockAccount() != null) {
      // customer.getStockAccount().addAccountListener(this);
      jbtStockingAccount.setText("securities: " + cmInstance.convertToCurrentCurrency(customer.getStockAccount().getBalance()) +
      " (Enter stock trade)");
      StockingAccountListener stockAListener = new StockingAccountListener();
      jbtStockingAccount.addActionListener(stockAListener);

      rightPanel.add(jbtStockingAccount);

    } else {
      JButton jbtCreateStockAcct = new JButton("How to create securities account");
      jbtCreateStockAcct.addActionListener(e -> {
        String msg = "To create a securities account:\n"+
        "1.) Your saving account must first have more than " + cmInstance.convertToCurrentCurrency(5000) + "\n" +
        "2.) Then from saving account, make a transfer of at least " + cmInstance.convertToCurrentCurrency(1000) +
        " to your securities/stock account";
        JOptionPane.showMessageDialog(rootPane, msg);
      });
      rightPanel.add(jbtCreateStockAcct);

    }


    jcbCurrencyOptions.addActionListener(new ChangeCurrencyListener());

    JLabel jlbMessage = new JLabel("Select your transaction");

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(2, 0));
    leftPanel.add(jcbCurrencyOptions);
    leftPanel.add(jlbMessage);

    if (customer.get_has_loan()) {
      JButton jbtPayLoan = new JButton("Pay Loan (via checking)");
      jbtPayLoan.addActionListener(e -> {
        PayLoanFrame plFrame = new PayLoanFrame(customer);
        plFrame.showWindow();
      });
      rightPanel.add(jbtPayLoan);

    } else {
      // meaning the customer does not have a checking account, and 
      // we are requiring our customer to have at least a checking 
      // account before making a loan
      if (customer.getCheckingAccount() == null) {
        JLabel jlbLoan = new JLabel("You must have a checking account "+
        "before you can request a loan");
        rightPanel.add(jlbLoan);

      } else {
        JButton jbtRequestLoan = new JButton("Request Loan");
        jbtRequestLoan.addActionListener(e -> {
          RequestLoanFrame rlFrame = new RequestLoanFrame(customer);
          rlFrame.showWindow();
          
        });
        rightPanel.add(jbtRequestLoan);
      }
    }

    if (customer.has_check_account() || customer.has_saving_account() || customer.has_stock_account()) {
      JButton jbtViewTransaction = new JButton("View Transaction");
      rightPanel.add(jbtViewTransaction);
      jbtViewTransaction.addActionListener(new ViewTransactionListener());

    } else {
      JLabel jlbViewTxn = new JLabel("You have yet to make any transactions");
      rightPanel.add(jlbViewTxn);
    }

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(0, 2));
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);

    add(mainPanel);

  }



  class ChangeCurrencyListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String selectedCurrency = jcbCurrencyOptions.getItemAt(jcbCurrencyOptions.getSelectedIndex());

      if (!selectedCurrency.equals(cmInstance.getCurrentCurrency())) {
        cmInstance.setCurrentCurrency(selectedCurrency);
      }
    }
  }

  class CreateCheckingAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * pass the account to this checking account frame?
       */
      BankingAccountCreationFrame bankingAccountCreationFrame = new BankingAccountCreationFrame(customer, CustomerFrame.this, Constants.CHECKING);
      bankingAccountCreationFrame.showWindow();
    }
  }

  class CheckingAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      BankingAccountFrame baFrame = new BankingAccountFrame(customer, Constants.CHECKING);
      baFrame.showWindow();
    }
  }

  class CreateSavingsAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * pass the account to this checking account frame?
       */
      BankingAccountCreationFrame bankingAccountCreationFrame = new BankingAccountCreationFrame(customer, CustomerFrame.this, Constants.SAVING);
      bankingAccountCreationFrame.showWindow();
    }
  }

  class SavingAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      SavingAccountFrame saFrame = new SavingAccountFrame(customer);
      saFrame.showWindow();
    }
  }

  class StockingAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      CustomerViewStocksFrame cvsFrame = new CustomerViewStocksFrame(customer);
      cvsFrame.showWindow();
    }
  }

  class ViewTransactionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      CustomerViewTransactionFrame txnFrame = new CustomerViewTransactionFrame(customer);
      txnFrame.showWindow();
    }
  }

  public void balanceUpdated(String customerName) {
    if (customerName.equals(customer.get_name())) {
      String msg = "There is a balance update to one of your accounts";
      regenerateFrame(msg);
    }
  }

  public void accountUpdated(String customerName) {
    if (customerName.equals(customer.get_name())) {
      String msg = "There is an account update to one of your accounts";
      regenerateFrame(msg);
    }
  }

  public void timeUpdate() {
    String msg = "There is a time update";
    regenerateFrame(msg);
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Customer: " + customer.get_name() );
    this.setSize( 900, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }

  public CustomerFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    cmInstance.removeCurrencyModelListener(this);
    mwInstance.removeBalanceListener(this);
    mwInstance.removeAccountListener(this);
    timeInstance.removeTimeModelListener(this);
    mwInstance.removeDeleteAccountListener(this);
    this.dispose();
    CustomerFrame customerFrame = new CustomerFrame(customer);
    customerFrame.showWindow();
    return customerFrame;
  }

  public void currencyUpdate() {
    String msg = "The currency in use has been changed, or the current" +
    " currency's conversion rate has been changed.";
    regenerateFrame(msg);
  }
}
