package frontend;

import javax.swing.*;

import account.BalanceListener;
import account.*;
import utility.Read;
import role.Customer;
import java.util.List;
import java.util.ArrayList;

import java.awt.event.*;
import java.awt.*;

public class ManagerViewCustomersFrame extends JFrame implements CurrencyModelListener, BalanceListener{
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  private Middleware mwInstance = Middleware.getInstance();
  
  private void deregisterListeners() {
    cmInstance.removeCurrencyModelListener(this);
    mwInstance.removeBalanceListener(this);
  }

  private JComboBox<String> jcbCurrencyOptions = cmInstance.createCurrencyComboBox();

  public ManagerViewCustomersFrame() {
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          deregisterListeners();
      }
    });

    cmInstance.addCurrencyModelListener(this);
    mwInstance.addBalanceListener(this);

    String[][] data = convertCustomersToData();
    String columnHeaders[] = {"Name", "Password", "Checking", "Saving", "Securities", "Loan"};

    JTable jt = new JTable(data, columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(jcbCurrencyOptions, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);

    // jbtViewDetailsConfirm.addActionListener(new ViewDetailsListener());
    jcbCurrencyOptions.addActionListener(new ChangeCurrencyListener());
  }

  class ChangeCurrencyListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String selectedCurrency = jcbCurrencyOptions.getItemAt(jcbCurrencyOptions.getSelectedIndex());

      if (!selectedCurrency.equals(cmInstance.getCurrentCurrency())) {
        cmInstance.setCurrentCurrency(selectedCurrency);
      }
    }
  }
  
  private String[][] convertCustomersToData() {
    List<Customer> customers = Read.readUsers();

    String[][] data = new String[customers.size()][6];

    for (int i = 0; i < customers.size(); i++) {
      /*
       * name, check, saving, stock, loan
       */
      Customer customer = customers.get(i);

      data[i][0] = customer.get_name();
      data[i][1] = customer.get_password();

      if (customer.has_check_account()) {
        // CheckingAccount checkingAccount = customer.getCheckingAccount();
        String result = cmInstance.convertToCurrentCurrency(customer.getCheckingAccount().getBalance());
        data[i][2] = result;
      } else {
        data[i][2] = "N/A";
      }
      
      if (customer.has_saving_account()) {
        // SavingAccount savingAccount = customer.getSavingAccount();
        String result = cmInstance.convertToCurrentCurrency(customer.getSavingAccount().getBalance());
        data[i][3] = result;
      } else {
        data[i][3] = "N/A";
      }

      if (customer.has_stock_account()) {
        String result = cmInstance.convertToCurrentCurrency(customer.getStockAccount().getBalance());
        data[i][4] = result;
      } else {
        data[i][4] = "N/A";
      }

      if (customer.get_has_loan()) {
        String result = cmInstance.convertToCurrentCurrency(customer.get_loan_num());
        data[i][5] = result;
      } else {
        data[i][5] = "N/A";
      }
    }
    return data;
  }

  public void currencyUpdate() {
    String msg = "The currency in use has been changed, or the current" +
    " currency's conversion rate has been changed.";
    regenerateFrame(msg);
  }

  public void balanceUpdated(String customerName) {
    String msg = "A new transaction has occurred.";
    regenerateFrame(msg);
  }

  public ManagerViewCustomersFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    cmInstance.removeCurrencyModelListener(this);
    mwInstance.removeBalanceListener(this);
    this.dispose();
    ManagerViewCustomersFrame viewCustomersFrame = new ManagerViewCustomersFrame();
    viewCustomersFrame.showWindow();
    return viewCustomersFrame;
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "View Customers");
    this.setSize( 600, 500 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
