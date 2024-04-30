package frontend;

import javax.swing.*;

import account.*;
import role.Customer;
import role.CustomerListener;
import utility.*;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class BankingAccountFrame extends JFrame implements CustomerListener{
  JTextField jtfDeposit = new JTextField();
  JTextField jtfWithdraw = new JTextField();

  Customer customer;
  String accountType;
  // CheckingAccount currentAccount;
  JComboBox<String> jcbOptions;
  CustomerFrame parentFrame;
  JTextField jtfTransfer = new JTextField();
  JLabel jlbBalance = new JLabel();

  public BankingAccountFrame(CustomerFrame parentFrame, Customer customer, String accountType) {
    customer.addCustomerListener(this);
    this.customer = customer;
    this.accountType = accountType;
    this.parentFrame = parentFrame;

    JLabel jlbDeposit = new JLabel("Enter amount to deposit");
    JButton jbtDeposit = new JButton("Confirm to deposit");
    JPanel depositPanel = new JPanel(new GridLayout(0, 3));
    depositPanel.add(jlbDeposit);
    depositPanel.add(jtfDeposit);
    depositPanel.add(jbtDeposit);

    JLabel jlbWithdraw = new JLabel("Enter amount to withdraw");
    JButton jbtWithdraw = new JButton("Confirm to withdraw");
    JPanel withdrawPanel = new JPanel(new GridLayout(0, 3));
    withdrawPanel.add(jlbWithdraw);
    withdrawPanel.add(jtfWithdraw);
    withdrawPanel.add(jbtWithdraw);

    JPanel mainPanel = new JPanel(new GridLayout(4, 0));
    mainPanel.add(jlbBalance);
    mainPanel.add(depositPanel);
    mainPanel.add(withdrawPanel);

    String[] options = getAvailableAccountsForTransfer();
    JPanel transferPanel;

    JLabel jlbMsg = new JLabel();
    JButton jbtTransfer = new JButton("Confirm to transfer");
    if (options.length == 0) {
      transferPanel = new JPanel(new GridLayout(1, 0));
      jlbMsg.setText("You currently do not have other acounts to transfer to");
      transferPanel.add(jlbMsg);

    } else {
      jcbOptions = new JComboBox<String>(options);
      JLabel jlbTransfer = new JLabel("Enter amount to transfer");
      transferPanel = new JPanel(new GridLayout(0, 4));
      transferPanel.add(jlbTransfer);
      transferPanel.add(jcbOptions);
      transferPanel.add(jtfTransfer);
      transferPanel.add(jbtTransfer);
    }

    mainPanel.add(transferPanel);

    add(mainPanel);

    // associate buttons with listeners
    jbtDeposit.addActionListener(new DepositListener());
    jbtWithdraw.addActionListener(new WithdrawListener());
    jbtTransfer.addActionListener(new TransferListener());
  }

  // ***************************************

  class DepositListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        double deposite = Double.parseDouble(jtfDeposit.getText());

        if (deposite < 0) {
          String msg = "To deposit, please enter a valid positive number";
          JOptionPane.showMessageDialog(rootPane, msg);
        } else {

          if (accountType.equals(Constants.CHECKING)) {
            CheckingAccount checkingAccount = customer.getCheckingAccount();
            checkingAccount.transferIn(deposite);
            String msg = "There is an update to your " + accountType + " account";
            parentFrame = parentFrame.regenerateFrame(msg);
            regenerateFrame(msg, parentFrame);
            

          } else if (accountType.equals(Constants.SAVING)) {
            SavingAccount savingAccount = customer.getSavingAccount();
            savingAccount.transferIn(deposite);
            String msg = "There is an update to your " + accountType + " account";
            parentFrame = parentFrame.regenerateFrame(msg);
            regenerateFrame(msg, parentFrame);

          }
        }

      } catch (NumberFormatException err) {
        String msg = "To deposit, please enter a valid positive number";
        JOptionPane.showMessageDialog(rootPane, msg);
      }
    }
  }

  class WithdrawListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        double withdraw = Double.parseDouble(jtfWithdraw.getText());

        if (withdraw < 0) {
          String msg = "To withdraw, please enter a valid positive number";
          JOptionPane.showMessageDialog(rootPane, msg);
        } else {

          if (accountType.equals(Constants.CHECKING)) {
            CheckingAccount checkingAccount = customer.getCheckingAccount();
            checkingAccount.transferOut(withdraw);
            String msg = "There is an update to your " + accountType + " account";
            parentFrame = parentFrame.regenerateFrame(msg);
            regenerateFrame(msg, parentFrame);

          } else if (accountType.equals(Constants.SAVING)) {
            SavingAccount savingAccount = customer.getSavingAccount();
            savingAccount.transferIn(withdraw);
            String msg = "There is an update to your " + accountType + " account";
            parentFrame = parentFrame.regenerateFrame(msg);
            regenerateFrame(msg, parentFrame);

          }
        }

      } catch (NumberFormatException err) {
        String msg = "To deposit, please enter a valid positive number";
        JOptionPane.showMessageDialog(rootPane, msg);
      }
    }
  }

  class TransferListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        double transferAmt = Double.parseDouble(jtfTransfer.getText());

        if (transferAmt < 0) {
          String msg = "To withdraw, please enter a valid positive number";
          JOptionPane.showMessageDialog(rootPane, msg);
        } else {

          if (accountType.equals(Constants.CHECKING)) {
            CheckingAccount checkingAccount = customer.getCheckingAccount();
            double caBalance = checkingAccount.getBalance();

            if (transferAmt > caBalance) {
              String msg = "To withdraw, please enter a valid positive number";
              JOptionPane.showMessageDialog(rootPane, msg);
            } else {
              checkingAccount.transferOut(transferAmt);
              String option = jcbOptions.getItemAt(jcbOptions.getSelectedIndex());

              if (option.equals(Constants.SAVING)) {
                customer.getSavingAccount().transferIn(transferAmt);

              } else if (option.equals(Constants.STOCKING)) {
                customer.getStockAccount().transferIn(transferAmt);
              }

              String msg = "There is an update to your " + accountType + " account";
              parentFrame = parentFrame.regenerateFrame(msg);
              regenerateFrame(msg, parentFrame);
            }

          } else if (accountType.equals(Constants.SAVING)) {
            SavingAccount savingAccount = customer.getSavingAccount();
            double saBalance = savingAccount.getBalance();

            if (transferAmt > saBalance) {
              String msg = "To withdraw, please enter a valid positive number";
              JOptionPane.showMessageDialog(rootPane, msg);
            } else {
              savingAccount.transferOut(transferAmt);
              String option = jcbOptions.getItemAt(jcbOptions.getSelectedIndex());

              if (option.equals(Constants.CHECKING)) {
                customer.getCheckingAccount().transferIn(transferAmt);

              } else if (option.equals(Constants.STOCKING)) {
                customer.getStockAccount().transferIn(transferAmt);
              }

              String msg = "There is an update to your " + accountType + " account";
              parentFrame = parentFrame.regenerateFrame(msg);
              regenerateFrame(msg, parentFrame);
            }

          }
        }

      } catch (NumberFormatException err) {
        String msg = "To deposit, please enter a valid positive number";
        JOptionPane.showMessageDialog(rootPane, msg);
      }
    }
  }


  private String[] getAvailableAccountsForTransfer() {
    List<String> options = new ArrayList<String>();
    if (accountType.equals(Constants.CHECKING)) {
      CheckingAccount checkingAccount = customer.getCheckingAccount();
      DecimalFormat df = new DecimalFormat("#0.00");
      String formatBalance = df.format(checkingAccount.getBalance());
      jlbBalance.setText("Current balance: " + formatBalance);
      
      if (customer.has_saving_account()) {
        options.add(Constants.SAVING);
      }

      if (customer.has_stock_account()) {
        options.add(Constants.STOCKING);
      }

    } else if (accountType.equals(Constants.SAVING)) {
      SavingAccount savingAccount = customer.getSavingAccount();
      DecimalFormat df = new DecimalFormat("#0.00");
      String formatBalance = df.format(savingAccount.getBalance());
      jlbBalance.setText("Current balance: " + formatBalance);

      if (customer.has_check_account()) {
        options.add(Constants.CHECKING);
      }

      if (customer.has_stock_account()) {
        options.add(Constants.STOCKING);
      }

    } else if (accountType.equals(Constants.STOCKING)) {
      if (customer.has_check_account()) {
        options.add(Constants.CHECKING);
      }

      if (customer.has_saving_account()) {
        options.add(Constants.SAVING);
      }

    }

    // convert ArrayList of Strings to an Array
    // new String[0] means create an array that is big enough
    // to hold all elements in the array list
    return options.toArray(new String[0]);
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Customer: " + customer.get_name() + "'s " +
    accountType + " account");
    this.setSize( 500, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }

  public void accountUpdated(String accountType) {
    String msg = "There is an update to your " + accountType + " account";
    parentFrame = parentFrame.regenerateFrame(msg);
    regenerateFrame(msg, parentFrame);
  }

  private void regenerateFrame(String msg, CustomerFrame frame) {
    JOptionPane.showMessageDialog(rootPane, msg);
    this.dispose();
    BankingAccountFrame bankingAccountFrame = new BankingAccountFrame(frame, customer, accountType);
    bankingAccountFrame.showWindow();
  }

}
