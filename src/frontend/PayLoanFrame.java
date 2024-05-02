package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import role.Customer;

public class PayLoanFrame extends JFrame implements CurrencyModelListener{
  private Middleware mwInstance = Middleware.getInstance();
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  Customer customer;

  public PayLoanFrame(Customer customer) {
    this.customer = customer;
    cmInstance.addCurrencyModelListener(this);

    JLabel jlbLoan = new JLabel("Current loan: " + cmInstance.convertToCurrentCurrency(customer.get_loan_num()));

    JLabel jlbMsg = new JLabel("Enter the amount of loan to pay off: " + 
    cmInstance.getCurrentCurrency());

    JTextField jtfLoan = new JTextField();

    JButton jbtLoan = new JButton("Confirm to Pay Loan");
    jbtLoan.addActionListener(e -> {
      String failureMsg = "Please enter a valid positive number";
      try {
        double loan = Double.parseDouble(jtfLoan.getText());

        if (loan < 0 ) {
          JOptionPane.showMessageDialog(rootPane, failureMsg);

        } else {
          if (customer.pay_loan(loan)) {
            String successMsg = "You have successfully paid off " + cmInstance.convertToCurrentCurrency(loan);
            JOptionPane.showMessageDialog(this, successMsg);
            cmInstance.removeCurrencyModelListener(this);
            PayLoanFrame.this.dispose();
            mwInstance.notifyBalanceUpdated(customer.get_name());

          } else {
            failureMsg = "Including interest, your checking does not have" +
            " enough to pay off the specified amount";
            JOptionPane.showMessageDialog(rootPane, failureMsg);

          }
          customer.request_loan(loan);


        }

      } catch (NumberFormatException err) {
        JOptionPane.showMessageDialog(rootPane, failureMsg);
      }
    });

    JPanel mainPanel = new JPanel(new GridLayout(4,0));
    mainPanel.add(jlbLoan);
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

  private PayLoanFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    cmInstance.removeCurrencyModelListener(this);
    this.dispose();
    PayLoanFrame plFrame = new PayLoanFrame(customer);
    plFrame.showWindow();
    return plFrame;
  }

  public void showWindow() {
    this.setTitle( "Pay loan");
    this.setSize( 500, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
