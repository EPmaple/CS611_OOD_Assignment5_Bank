package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class BalanceTransferFrame extends JFrame{
  private JComboBox<String> jcbFromAccount;
  private JComboBox<String> jcbToAccount;
  private JTextField jtfTransferAmt = new JTextField();
  
  public BalanceTransferFrame() {
    JLabel jlbSelectPrompt1 = new JLabel("Select account transferring from: ");
    JLabel jlbSelectPrompt2 = new JLabel("Select account transferring to: ");
    JLabel jlbSelectPrompt3 = new JLabel("Enter amount transferring: ");

    JButton jbtTransfer = new JButton("Transfer");

    String accounts[] = {"1", "2", "3"};
    jcbFromAccount = new JComboBox<>(accounts);

    jcbToAccount = new JComboBox<>(accounts);

    JPanel fromPanel = new JPanel(new GridLayout(0, 2));
    fromPanel.add(jlbSelectPrompt1);
    fromPanel.add(jcbFromAccount);

    JPanel toPanel = new JPanel(new GridLayout(0, 2));
    toPanel.add(jlbSelectPrompt2);
    toPanel.add(jcbToAccount);

    JPanel amtPanel = new JPanel(new GridLayout(0, 2));
    amtPanel.add(jlbSelectPrompt3);
    amtPanel.add(jtfTransferAmt);

    JPanel mainPanel = new JPanel(new GridLayout(4, 0));
    mainPanel.add(fromPanel);
    mainPanel.add(toPanel);
    mainPanel.add(amtPanel);
    mainPanel.add(jbtTransfer);
    
    add(mainPanel);

    jbtTransfer.addActionListener(new TransferListener());
  }

  class TransferListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * More to be done here
       */
      String fromAccount = jcbFromAccount.getItemAt(jcbFromAccount.getSelectedIndex());
      String toAccount = jcbToAccount.getItemAt(jcbFromAccount.getSelectedIndex());

      try {
        double transferAmt = Double.parseDouble(jtfTransferAmt.getText());
        String msg = "$" + transferAmt + " has been transferred";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();
        dispose();

      } catch (NumberFormatException err) {
        String msg = "Please enter a number";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();
      }
    }
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Transfer" );
    this.setSize( 500, 400 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
