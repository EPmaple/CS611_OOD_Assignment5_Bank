package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CustomerFrame extends JFrame{
  private String username;
  // also grab the account info from middleware as well

  public CustomerFrame(String username) {
    this.username = username;
    /*
     * If account info is null, or empty, then show create button, else take
     * the info and create another button that allows for viewing
     */

    JButton jbtCreateCheckingAccount = new JButton("Create Checking Account"); // make several states of this
    JButton jbtCreateSavingsAccount = new JButton("Create Savings Account"); // make several states of this
    JButton jbtCreateSecuritiesAccount = new JButton("Create Securities Account through Savings Account"); // make several states of this
    JButton jbtRequestLoan = new JButton("Request Loan");
    JButton jbtViewTransaction = new JButton("View Transaction");
    // JButton 
    JLabel jlbUsername = new JLabel(username);
    JLabel jlbEmpty = new JLabel();
    JLabel jlbMessage = new JLabel("Select your transaction");

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(3, 0));
    leftPanel.add(jlbUsername);
    leftPanel.add(jlbEmpty);
    leftPanel.add(jlbMessage);

    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new GridLayout(5, 0));
    rightPanel.add(jbtCreateCheckingAccount);
    rightPanel.add(jbtCreateSavingsAccount);
    rightPanel.add(jbtCreateSecuritiesAccount);
    rightPanel.add(jbtRequestLoan);
    rightPanel.add(jbtViewTransaction);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(0, 2));
    mainPanel.add(leftPanel);
    mainPanel.add(rightPanel);

    add(mainPanel);

    jbtCreateCheckingAccount.addActionListener(new CreateCheckingAccountListener());
    jbtCreateSavingsAccount.addActionListener(new CreateSavingsAccountListener());
    jbtCreateSecuritiesAccount.addActionListener(new CreateSecuritiesAccountListener());
    jbtRequestLoan.addActionListener(new RequestLoanListener());
    jbtViewTransaction.addActionListener(new ViewTransactionListener());
  }

  class CreateCheckingAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * pass the account to this checking account frame?
       */
      BankingAccountCreationFrame bankingAccountCreationFrame = new BankingAccountCreationFrame("checking");
      bankingAccountCreationFrame.showWindow();
    }
  }

  class CreateSavingsAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * pass the account to this checking account frame?
       */
      BankingAccountCreationFrame bankingAccountCreationFrame = new BankingAccountCreationFrame("savings");
      bankingAccountCreationFrame.showWindow();
    }
  }

  class CreateSecuritiesAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * pass the account to this checking account frame?
       */
      BankingAccountCreationFrame bankingAccountCreationFrame = new BankingAccountCreationFrame("securities");
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

  public void showWindow() {
    // create a new frame
    // JFrame customerFrame = new CustomerFrame(username);

    // init frame info
    this.setTitle( "Customer: " + username );
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

