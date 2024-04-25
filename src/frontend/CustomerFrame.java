package frontend;

import javax.swing.*;
import java.awt.event.*;

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

  }

  class CreateCheckingAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * pass the account to this checking account frame?
       */
    }
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

   