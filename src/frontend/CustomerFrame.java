package frontend;

import role.*;

import javax.swing.*;

import account.*;
import utility.*;

import java.awt.event.*;
import java.awt.*;

public class CustomerFrame extends JFrame implements AccountListener{
  private Customer customer;
  // private CheckingAccount checkingAccount;
  private JButton jbtCheckingAccount = new JButton();
  private ActionListener jbtCACurrentListener;

  private JButton jbtSavingAccount = new JButton();
  private ActionListener jbtSACurrentListener;
  JButton jbtSecuritiesAccount = new JButton();

  // private JButton jbtCheckingAccount = new JButton();
  JPanel rightPanel = new JPanel(new GridLayout(5, 0));
  // also grab the account info from middleware as well

  public CustomerFrame(Customer customer) {
    this.customer = customer;
    /*
     * If account info is null, or empty, then show create button, else take
     * the info and create another button that allows for viewing
     */
    // checking account button
    if (customer.has_check_account()) {
      // balanceUpdated(Constants.CHECKING);
      jbtCheckingAccount.setText("checking: "  + "$" + customer.getCheckingAccount().getBalance());
      CheckingAccountListener caListener = new CheckingAccountListener();
      jbtCheckingAccount.addActionListener(caListener);

    } else {
      jbtCheckingAccount.setText("Create Checking Account");
      CreateCheckingAccountListener ccaListener = new CreateCheckingAccountListener();
      jbtCheckingAccount.addActionListener(ccaListener);
      jbtCACurrentListener = ccaListener;

    }

    // saving account button
    if (customer.has_saving_account()) {
      // balanceUpdated(Constants.SAVING);
      jbtSavingAccount.setText("saving: "  + "$" + customer.getCheckingAccount().getBalance());
      // create a saving account listener
      SavingAccountListener saListener = new SavingAccountListener();
      jbtSavingAccount.addActionListener(saListener);

    } else {
      jbtSavingAccount.setText("Create Saving Account");
      CreateSavingsAccountListener csaListener = new CreateSavingsAccountListener();
      jbtSavingAccount.addActionListener(csaListener);
      jbtSACurrentListener = csaListener;

    }

    // jbtSavingsAccount = new JButton("Create Savings Account"); // make several states of this
    jbtSecuritiesAccount = new JButton("Create Securities Account through Savings Account"); // make several states of this
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

    // JPanel rightPanel = new JPanel();
    // rightPanel.setLayout(new GridLayout(5, 0));
    rightPanel.add(jbtCheckingAccount);
    rightPanel.add(jbtSavingAccount);
    rightPanel.add(jbtSecuritiesAccount);
    rightPanel.add(jbtRequestLoan);
    rightPanel.add(jbtViewTransaction);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(0, 2));
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);

    add(mainPanel);

    jbtSecuritiesAccount.addActionListener(new CreateSecuritiesAccountListener());
    jbtRequestLoan.addActionListener(new RequestLoanListener());
    jbtViewTransaction.addActionListener(new ViewTransactionListener());

    // open up a window that allows for deposit, transfer, withdraw,
    // jbtCheckingAccount.addActionListener(...);
  }

  public void updateCheckingAccountButtonListener() {
    // update the action listener
    jbtCheckingAccount.removeActionListener(jbtCACurrentListener);
    // create the new action listener
    CheckingAccountListener caListener = new CheckingAccountListener();
    jbtCheckingAccount.addActionListener(caListener);
  }

  public void updateSavingAccountButtonListener() {
    // update the action listener
    jbtSavingAccount.removeActionListener(jbtSACurrentListener);
    // create the new action listener
    SavingAccountListener saListener = new SavingAccountListener();
    jbtSavingAccount.addActionListener(saListener);
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

    }
  }

  class CreateSecuritiesAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * pass the account to this checking account frame?
       */
      BankingAccountCreationFrame bankingAccountCreationFrame = new BankingAccountCreationFrame(customer, CustomerFrame.this, "securities");
      bankingAccountCreationFrame.showWindow();
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
    // System.out.println("accountType: " + accountType);
    // System.out.println("jbtCheckingAccount: " + jbtCheckingAccount.toString());
    // System.out.println("checkingAccount.getBalance(): " + checkingAccount);
    // TODO Auto-generated method stub
    if (accountType.equals(Constants.CHECKING)) {
      String msg = "There is a balance update to your checking account";
      JOptionPane.showMessageDialog(rootPane, msg);
      this.dispose();
      CustomerFrame customerFrame = new CustomerFrame(customer);
      customerFrame.showWindow();
      // JOptionPane.showMessageDialog(rootPane, accountType);
      // SwingUtilities.invokeLater(() -> {
      //   System.out.println("Is on EDT: " + SwingUtilities.isEventDispatchThread());
      //   jbtCheckingAccount.setText("Checking: " + "$" + customer.getCheckingAccount().getBalance());
      //   this.revalidate();
      //   this.repaint();
      // });

    } else if (accountType.equals(Constants.SAVING)) {
      System.out.println("see if the loop is executed");
      String msg = "There is a balance update to your checking account";
      JOptionPane.showMessageDialog(rootPane, msg);
      this.dispose();
      System.out.println("see if the loop is executed");
      CustomerFrame customerFrame = new CustomerFrame(customer);
      customerFrame.showWindow();
      // SwingUtilities.invokeLater(() -> {
      //   System.out.println("Is on EDT: " + SwingUtilities.isEventDispatchThread());
      //   jbtSavingAccount.setText("Saving: " + "$" + customer.getSavingAccount().getBalance());
      //   this.revalidate();
      //   this.repaint();
      // });

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
}

/*
 * Deposit frame
 * each button shows the current balances
 * click on the account button, takes in to viewing the current balances, to deposit, to withdraw
 */

  /*
   * a frame issues a change in the current currency, via JCombobox
   * 
   * set the currentCurrency in CurrencyModel accordingly
   * 
   * CurrencyModel notifies all its listeners (which are also frames), these
   * frames would implement the listener interface to make sure they implement
   * the method to update the numbers they are currently showing
   * 
   * for deposit, we have the currentCurrencyType, then we take the number,
   * get the rate from currentCurrency to DOLLAR, and then save that to the
   * db
   */

