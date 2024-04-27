package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class OpenPositionsFrame extends JFrame{
  private JTextField jtfName = new JTextField("Stock name");
  private JTextField jtfNumShares = new JTextField("Number of shares");

  public OpenPositionsFrame() {
    JLabel jlbSell = new JLabel("Enter name of the stock to sell, and the number of shares to sell.");

    JButton jbtSell = new JButton("Confirm");
    JPanel sellPanel = new JPanel(new GridLayout(0, 3));
    sellPanel.add(jtfName);
    sellPanel.add(jtfNumShares);
    sellPanel.add(jbtSell);

    JPanel header = new JPanel(new GridLayout(2, 0));
    header.add(jlbSell);
    header.add(sellPanel);

    // ***************************

    String placeholderData[][] = {
      {"1", "2", "3", "4", "5", "6"}
    };
    String columnHeaders[] = {"Symbol", "Name", "Entry Price", "Current Price", "Realized Profit", "Unrealized Profit"};

    JTable jt = new JTable(placeholderData, columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(header, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);

    jbtSell.addActionListener(new SellListener());
  }

  class SellListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // if stock of such a name exists, and that the securities
      // has enough balance to buy the specified number of shares
      String stockName = jtfName.getText();
      if (test()) { // there is no stock of this name
        String msg = "There does not exist a stock with the name "+
        stockName + ".";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();

      } else if (test()) {  // the number of shares is negative, OR
        // the number of shares to be sold is greater
        // than the number of shares he owns
        String msg = "You can only sell number of shares that are "+
        "greater than zero and less or equal to the number of shares "+
        "you own.";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();

      } else { // then we can sell

      }
    }
  }

  private boolean test() {
    return true;
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "View Current Open Positions");
    this.setSize( 1000, 600 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
