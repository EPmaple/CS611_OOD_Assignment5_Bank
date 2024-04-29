package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DepositFrame extends JFrame{
  private JTextField jtfDeposite = new JTextField();
  private JLabel jlbMsg = new JLabel();

  public DepositFrame() {
    JLabel jlbDeposit = new JLabel("Deposit amount: ");

    JButton jbtDeposit = new JButton("Deposit");

    JPanel depositPanel = new JPanel(new GridLayout(0, 2));
    depositPanel.add(jlbDeposit, jtfDeposite);

    JPanel mainPanel = new JPanel(new GridLayout(3, 0));
    mainPanel.add(depositPanel);
    mainPanel.add(jbtDeposit);
    mainPanel.add(jlbMsg);
    
    add(mainPanel);

    jbtDeposit.addActionListener(new DepositListener());
  }

  class DepositListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        double depositeAmt = Double.parseDouble(jtfDeposite.getText());
        String msg = "$" + depositeAmt + " has been deposited";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();
        dispose();

      } catch (NumberFormatException err) {
        jlbMsg.setText("Please enter a number");
      }
    }
  }

  public void showWindow() {

    // init frame info
    this.setTitle( "Account Deposit" );
    this.setSize( 300, 200 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
