package frontend;

import javax.swing.*;

import account.*;
import role.Customer;
import utility.*;

import java.awt.event.*;
import java.awt.*;

public class BankingAccountCreationFrame extends JFrame{
  private String accountType;
  private JTextField jtfDeposit = new JTextField();
  private JLabel jlbMsg = new JLabel();
  private CustomerFrame parentFrame;
  private Customer customer;

  public BankingAccountCreationFrame(Customer customer, CustomerFrame parentFrame, String accountType) {
    this.parentFrame = parentFrame;
    this.accountType = accountType;
    this.customer = customer;

    JLabel jlbDeposit = new JLabel("Initial deposit: ");
    JButton jbtCreate = new JButton("Create Account");

    JPanel depositPanel = new JPanel();
    depositPanel.setLayout(new GridLayout(0, 2));
    depositPanel.add(jlbDeposit);
    depositPanel.add(jtfDeposit);

    JPanel mainPanel = new JPanel();
    depositPanel.setLayout(new GridLayout(3, 0));
    mainPanel.add(depositPanel);
    mainPanel.add(jbtCreate);
    mainPanel.add(jlbMsg);

    add(mainPanel);

    jbtCreate.addActionListener(new CreateBankingAccountListener());
  }

  class CreateBankingAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // create a popup window in the form of a JDialog frame
      // accountType account has been created with $deposit
      try {
        double depositeAmt = Double.parseDouble(jtfDeposit.getText());

        if (depositeAmt < 0 ) {
          jlbMsg.setText("Please enter a valid positive number");
          return;

        } else {
          initBankingAccount(depositeAmt);

        }

      } catch (NumberFormatException err) {
        jlbMsg.setText("Please enter a valid positive number");
      }
    }
  }

  private void initBankingAccount(double depositAmt) {
    if (accountType.equals(Constants.CHECKING)) {
      // create the account for the user
      customer.createCheckingAccount();

      // register the parent frame as a subject for notificaiton
      CheckingAccount checkingAccount = customer.getCheckingAccount();

      // then we make the deposit, and notifyBalanceUpdated will work
      // as intended with its list of listeners
      checkingAccount.transferIn(depositAmt);

      // now the parent frame can get the balance from the account
      // w/o errors
      // parentFrame.updateCheckingAccountButtonListener();

    } else if (accountType.equals(Constants.SAVING)) {
      // create the account for the user
      customer.createSavingAccount();

      // register the parent frame as a subject for notificaiton
      SavingAccount savingAccount = customer.getSavingAccount();

      // then we make the deposit, and notifyBalanceUpdated will work
      // as intended with its list of listeners
      savingAccount.transferIn(depositAmt);

      // now the parent frame can get the balance from the account
      // w/o errors
      // parentFrame.updateSavingAccountButtonListener();

    } else if (accountType.equals(Constants.STOCKING)) {


    } else {
      System.out.println("accountType passed into BankingAccountCreationFrame" +
      " is not supported: " + accountType);
    }

    // popup window for notification
    String msg = accountType + " account has been created with "+
    "$" + depositAmt;
    JOptionPane.showMessageDialog(rootPane, msg);
    // PopupFrame popup = new PopupFrame(msg);
    // popup.showWindow();
    // dispose the create account window 
    dispose();
  }

  public void showWindow() {
    // create a new frame
    // JFrame customerFrame = new CustomerFrame(username);

    // init frame info
    this.setTitle( "Creating a " + accountType + " account " );
    this.setSize( 300, 200 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
