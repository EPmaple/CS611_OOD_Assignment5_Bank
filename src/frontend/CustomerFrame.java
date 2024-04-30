package frontend;

import role.*;

import javax.swing.*;

import account.*;
import utility.*;

import java.awt.event.*;
import java.awt.*;

public class CustomerFrame extends JFrame implements AccountListener, CurrencyModelListener{
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  private Customer customer;
  // private CheckingAccount checkingAccount;
  private JButton jbtCheckingAccount = new JButton();

  private JButton jbtSavingAccount = new JButton();
  private JButton jbtStockingAccount = new JButton();

  // private JButton jbtCheckingAccount = new JButton();
  JPanel rightPanel = new JPanel(new GridLayout(5, 0));
  // also grab the account info from middleware as well

  public CustomerFrame(Customer customer) {
    this.customer = customer;
    cmInstance.addCurrencyModelListener(this);
    /*
     * If account info is null, or empty, then show create button, else take
     * the info and create another button that allows for viewing
     */
    // checking account button
    if (customer.has_check_account()) {
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
    if (customer.has_saving_account()) {
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
    if (customer.has_stock_account()) {
      jbtStockingAccount.setText("saving: " + cmInstance.convertToCurrentCurrency(customer.getStockAccount().getBalance()));
      StockingAccountListener stockAListener = new StockingAccountListener();
      jbtStockingAccount.addActionListener(stockAListener);

      rightPanel.add(jbtStockingAccount);

    } else {
      JLabel jlbStockingAccount = new JLabel("Create Securities Account through Saving Account");
      rightPanel.add(jlbStockingAccount);

    }
    

    JButton jbtRequestLoan = new JButton("Request Loan");
    JButton jbtViewTransaction = new JButton("View Transaction");
    // JButton 
    JLabel jlbUsername = new JLabel(customer.get_name());
    JLabel jlbEmpty = new JLabel();
    JLabel jlbMessage = new JLabel("Select your transaction");

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(3, 0));
    leftPanel.add(jlbUsername);
    leftPanel.add(jlbEmpty);
    leftPanel.add(jlbMessage);

    rightPanel.add(jbtRequestLoan);
    rightPanel.add(jbtViewTransaction);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(0, 2));
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);

    add(mainPanel);

    jbtRequestLoan.addActionListener(new RequestLoanListener());
    jbtViewTransaction.addActionListener(new ViewTransactionListener());

    // open up a window that allows for deposit, transfer, withdraw,
    // jbtCheckingAccount.addActionListener(...);
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
      BankingAccountFrame baFrame = new BankingAccountFrame(CustomerFrame.this, customer, Constants.CHECKING);
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
      BankingAccountFrame baFrame = new BankingAccountFrame(CustomerFrame.this, customer, Constants.SAVING);
      baFrame.showWindow();
    }
  }

  class StockingAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // ...
    }
  }

  class RequestLoanListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // specifics are yet to be implemented
      RequestLoanFrame requestLoanFrame = new RequestLoanFrame();
      requestLoanFrame.showWindow();
    }
  }

  class ViewTransactionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      CustomerViewTransactionFrame txnFrame = new CustomerViewTransactionFrame();
      txnFrame.showWindow();
    }
  }

  @Override
  public void balanceUpdated(String accountType) {
    if (accountType.equals(Constants.CHECKING)) {
      String msg = "There is a balance update to your " + accountType + " account";
      regenerateFrame(msg);

    } else if (accountType.equals(Constants.SAVING)) {
      String msg = "There is a balance update to your " + accountType + " account";
      regenerateFrame(msg);

    } else if (accountType.equals(Constants.STOCKING)) {


    } else {
      System.out.println("The account type in balanceUpdate() is: " + 
      accountType + ", which is not supported.");
    }
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Customer: " + customer.get_name() );
    this.setSize( 500, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }

  public CustomerFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
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
