package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import role.Customer;

public class RequestLoanFrame extends JFrame implements CurrencyModelListener{
  private Middleware mwInstance = Middleware.getInstance();
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  Customer customer;

  public RequestLoanFrame(Customer customer) {
    this.customer = customer;
    cmInstance.addCurrencyModelListener(this);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          deregisterListeners();
      }
    });

    JLabel jlbMsg = new JLabel("Enter amount to loan: " + 
    cmInstance.getCurrentCurrency());

    JTextField jtfLoan = new JTextField();

    JButton jbtLoan = new JButton("Confirm to Request Loan");
    jbtLoan.addActionListener(e -> {
      String failureMsg = "Please enter a valid positive number";
      try {
        double loan = Double.parseDouble(jtfLoan.getText());

        if (loan < 0 ) {
          JOptionPane.showMessageDialog(rootPane, failureMsg);

        } else {
          customer.request_loan(loan);
          String successMsg = "You have successfully acquired a loan of " + cmInstance.convertToCurrentCurrency(loan);
          JOptionPane.showMessageDialog(rootPane, successMsg);
          cmInstance.removeCurrencyModelListener(this);
          RequestLoanFrame.this.dispose();
          mwInstance.notifyBalanceUpdated(customer.get_name());

        }

      } catch (NumberFormatException err) {
        JOptionPane.showMessageDialog(rootPane, failureMsg);
      }
    });

    JPanel mainPanel = new JPanel(new GridLayout(3,0));
    mainPanel.add(jlbMsg);
    mainPanel.add(jtfLoan);
    mainPanel.add(jbtLoan);

    add(mainPanel); // add the panel to the frame
  }

  public void currencyUpdate() {
    String msg = "The currency in use has been changed, or the current" +
    " currency's conversion rate has been changed.";
    regenerateFrame(msg);
  }

  private RequestLoanFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    cmInstance.removeCurrencyModelListener(this);
    this.dispose();
    RequestLoanFrame rlFrame = new RequestLoanFrame(customer);
    rlFrame.showWindow();
    return rlFrame;
  }

  public void showWindow() {
    this.setTitle( "Requesting loan");
    this.setSize( 500, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }

  private void deregisterListeners() {
    cmInstance.removeCurrencyModelListener(this);
  }
}
