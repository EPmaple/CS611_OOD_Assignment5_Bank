package frontend;

import javax.swing.*;

import account.*;
import role.AccountListener;
import role.Customer;
import role.AccountListener;
import utility.*;

import java.awt.event.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class BankingAccountFrame extends JFrame implements AccountListener, CurrencyModelListener{
  private Middleware mwInstance = Middleware.getInstance();
  CurrencyModel cmInstance = CurrencyModel.getInstance();
  JTextField jtfDeposit = new JTextField();
  JTextField jtfWithdraw = new JTextField();

  Customer customer;
  String accountType;
  // CheckingAccount currentAccount;
  JComboBox<String> jcbOptions;
  JTextField jtfTransfer = new JTextField();
  JLabel jlbBalance = new JLabel();
  JComboBox<String> jcbCurrencyOptions = cmInstance.createCurrencyComboBox();

  private void deregisterListeners() {
    mwInstance.removeAccountListener(this);
    cmInstance.removeCurrencyModelListener(this);
  }

  public BankingAccountFrame(Customer customer, String accountType) {
    mwInstance.addAccountListener(this);
    cmInstance.addCurrencyModelListener(this);
    this.customer = customer;
    this.accountType = accountType;

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          deregisterListeners();
      }
    });

    JLabel jlbDeposit = new JLabel("Enter amount to deposit: " +
    cmInstance.getCurrentCurrency());
    JButton jbtDeposit = new JButton("Confirm to deposit");
    JPanel depositPanel = new JPanel(new GridLayout(0, 3));
    depositPanel.add(jlbDeposit);
    depositPanel.add(jtfDeposit);
    depositPanel.add(jbtDeposit);

    JLabel jlbWithdraw = new JLabel("Enter amount to withdraw: " +
    cmInstance.getCurrentCurrency());
    JButton jbtWithdraw = new JButton("Confirm to withdraw");
    JPanel withdrawPanel = new JPanel(new GridLayout(0, 3));
    withdrawPanel.add(jlbWithdraw);
    withdrawPanel.add(jtfWithdraw);
    withdrawPanel.add(jbtWithdraw);

    JPanel header = new JPanel(new GridLayout(0, 3));
    header.add(jcbCurrencyOptions);
    header.add(jlbBalance);

    JPanel mainPanel = new JPanel(new GridLayout(4, 0));
    mainPanel.add(header);
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
      JLabel jlbTransfer = new JLabel("Enter amount to transfer: "+
      cmInstance.getCurrentCurrency());
      transferPanel = new JPanel(new GridLayout(0, 4));
      transferPanel.add(jcbOptions);
      transferPanel.add(jlbTransfer);
      transferPanel.add(jtfTransfer);
      transferPanel.add(jbtTransfer);
    }

    mainPanel.add(transferPanel);

    add(mainPanel);

    // associate buttons with listeners
    jbtDeposit.addActionListener(new DepositListener());
    jbtWithdraw.addActionListener(new WithdrawListener());
    jbtTransfer.addActionListener(new TransferListener());
    jcbCurrencyOptions.addActionListener(new ChangeCurrencyListener());

    // delete button
    JButton jbtDelete = new JButton("Delete Account");
    header.add(jbtDelete);
    jbtDelete.addActionListener(e -> {
      // Show a confirmation dialog
      int confirmed = JOptionPane.showConfirmDialog(rootPane,
      "Do you want to delete this account?",
      "Confirm Account Deletion",
      JOptionPane.YES_NO_OPTION,
      JOptionPane.WARNING_MESSAGE);

      if (confirmed == JOptionPane.YES_OPTION) {
        // Logic to delete the account
        if (customer.deleteCheckingAccount()) {
          JOptionPane.showMessageDialog(rootPane, "Account deleted successfully.");

          // Close this window
          deregisterListeners();
          this.dispose();
        } else {
          String msg = "You cannot delete your checking account because "+
          "you currently have a loan.";
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
  //       customer.deleteCheckingAccount();
  //       JOptionPane.showMessageDialog(rootPane, "Account deleted successfully.");

  //       // Close this window
  //       dispose();
  //     }
  //   }
  // }

  // ***************************************

  class ChangeCurrencyListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String selectedCurrency = jcbCurrencyOptions.getItemAt(jcbCurrencyOptions.getSelectedIndex());

      if (!selectedCurrency.equals(cmInstance.getCurrentCurrency())) {
        cmInstance.setCurrentCurrency(selectedCurrency);
      }
    }
  }

  class DepositListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        double deposite = Double.parseDouble(jtfDeposit.getText());
        // from current currency to dollar
        double depositInDollar = cmInstance.convertToCurrencyForStorage(deposite);

        if (deposite < 0) {
          String msg = "To deposit, please enter a valid positive number";
          JOptionPane.showMessageDialog(rootPane, msg);
        } else {

          if (accountType.equals(Constants.CHECKING)) {
            CheckingAccount checkingAccount = customer.getCheckingAccount();
            checkingAccount.transferIn(depositInDollar);
            String msg = "There is an update to your " + accountType + " account";
            // parentFrame = parentFrame.regenerateFrame(msg);
            regenerateFrame(msg);
            

          } else if (accountType.equals(Constants.SAVING)) {
            SavingAccount savingAccount = customer.getSavingAccount();
            savingAccount.transferIn(depositInDollar);
            String msg = "There is an update to your " + accountType + " account";
            // parentFrame = parentFrame.regenerateFrame(msg);
            regenerateFrame(msg);

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
        double withdrawInDollar = cmInstance.convertToCurrencyForStorage(withdraw);

        if (withdraw < 0) {
          String msg = "To withdraw, please enter a valid positive number";
          JOptionPane.showMessageDialog(rootPane, msg);
        } else {

          if (accountType.equals(Constants.CHECKING)) {
            CheckingAccount checkingAccount = customer.getCheckingAccount();
            checkingAccount.transferOut(withdrawInDollar);
            String msg = "There is an update to your " + accountType + " account";
            // parentFrame = parentFrame.regenerateFrame(msg);
            regenerateFrame(msg);

          } else if (accountType.equals(Constants.SAVING)) {
            SavingAccount savingAccount = customer.getSavingAccount();
            savingAccount.transferOut(withdrawInDollar);
            String msg = "There is an update to your " + accountType + " account";
            // parentFrame = parentFrame.regenerateFrame(msg);
            regenerateFrame(msg);

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
        double transferInDollar = cmInstance.convertToCurrencyForStorage(transferAmt);

        if (transferAmt < 0) {
          String msg = "To withdraw, please enter a valid positive number";
          JOptionPane.showMessageDialog(rootPane, msg);
        } else {

          if (accountType.equals(Constants.CHECKING)) {
            CheckingAccount checkingAccount = customer.getCheckingAccount();
            double caBalance = checkingAccount.getBalance();

            if (transferInDollar > caBalance) {
              String msg = "To withdraw, please enter a valid positive number";
              JOptionPane.showMessageDialog(rootPane, msg);
            } else {
              checkingAccount.transferOut(transferInDollar);
              String option = jcbOptions.getItemAt(jcbOptions.getSelectedIndex());

              if (option.equals(Constants.SAVING)) {
                customer.getSavingAccount().transferIn(transferInDollar);

              } else if (option.equals(Constants.STOCKING)) {
                customer.getStockAccount().transferIn(transferInDollar);
              }

              String msg = "There is an update to your " + accountType + " account";
              // parentFrame = parentFrame.regenerateFrame(msg);
              regenerateFrame(msg);
            }

          } else if (accountType.equals(Constants.SAVING)) {
            SavingAccount savingAccount = customer.getSavingAccount();
            double saBalance = savingAccount.getBalance();

            if (transferInDollar > saBalance) {
              String msg = "To withdraw, please enter a valid positive number";
              JOptionPane.showMessageDialog(rootPane, msg);
            } else {
              savingAccount.transferOut(transferInDollar);
              String option = jcbOptions.getItemAt(jcbOptions.getSelectedIndex());

              if (option.equals(Constants.CHECKING)) {
                customer.getCheckingAccount().transferIn(transferInDollar);

              } else if (option.equals(Constants.STOCKING)) {
                customer.getStockAccount().transferIn(transferInDollar);
              }

              String msg = "There is an update to your " + accountType + " account";
              // parentFrame = parentFrame.regenerateFrame(msg);
              regenerateFrame(msg);
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
      double balance = checkingAccount.getBalance();
      jlbBalance.setText("Current balance: " + cmInstance.convertToCurrentCurrency(balance));
      
      if (customer.has_saving_account()) {
        options.add(Constants.SAVING);
      }

      if (customer.has_stock_account()) {
        options.add(Constants.STOCKING);
      }

    } else if (accountType.equals(Constants.SAVING)) {
      SavingAccount savingAccount = customer.getSavingAccount();
      double balance = savingAccount.getBalance();
      jlbBalance.setText("Current balance: " + cmInstance.convertToCurrentCurrency(balance));

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
    this.setSize( 750, 400 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }

  public void accountUpdated(String customerName) {
    if (customerName.equals(customer.get_name())) {
      String msg = "There is a balance update to one of your accounts";
      regenerateFrame(msg);
    }
  }

  // public void accountUpdated(String accountType) {
  //   String msg = "There is an update to your " + accountType + " account";
  //   // parentFrame = parentFrame.regenerateFrame(msg);
  //   // customer.removeCustomerListener(this);
  //   regenerateFrame(msg);
  // }

  private void regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    mwInstance.removeAccountListener(this);
    cmInstance.removeCurrencyModelListener(this);
    this.dispose();
    BankingAccountFrame bankingAccountFrame = new BankingAccountFrame(customer, accountType);
    bankingAccountFrame.showWindow();
  }

  public void currencyUpdate() {
    String msg = "The currency in use has been changed, or the current" +
    " currency's conversion rate has been changed.";
    // cmInstance.removeCurrencyModelListener(this);
    regenerateFrame(msg);
  }
}
