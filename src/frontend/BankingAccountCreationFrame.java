package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class BankingAccountCreationFrame extends JFrame{
  private String accountType;
  private JTextField jtfDeposit = new JTextField();
  private JLabel jlbMsg = new JLabel();

  public BankingAccountCreationFrame(String accountType) {
    this.accountType = accountType;

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
        String msg = accountType + " account has been created with "+
        "$" + depositeAmt;
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();
        dispose();

      } catch (NumberFormatException err) {
        jlbMsg.setText("Please enter a number");
      }
    }
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
