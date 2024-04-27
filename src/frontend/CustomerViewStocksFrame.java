package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CustomerViewStocksFrame extends JFrame{
  private JLabel jlbSecuritiesBalance = new JLabel();
  private JTextField jtfName = new JTextField("Stock name");
  private JTextField jtfNumShares = new JTextField("Number of shares");

  public CustomerViewStocksFrame() {
    jlbSecuritiesBalance.setText("Securities account currently has $xx.");

    JLabel jlbBuy = new JLabel("Enter name of the stock to buy, and the number of shares to buy.");

    JButton jbtBuy = new JButton("Confirm");
    JPanel buyPanel = new JPanel(new GridLayout(0, 3));
    buyPanel.add(jtfName);
    buyPanel.add(jtfNumShares);
    buyPanel.add(jbtBuy);

    JPanel header = new JPanel(new GridLayout(3, 0));
    header.add(jlbSecuritiesBalance);
    header.add(jlbBuy);
    header.add(buyPanel);

    // ***************************

    String placeholderData[][] = {
      {"1", "2", "3", "4"}
    };
    String columnHeaders[] = {"Symbol", "Name", "Current Price", "Percent Fluctuation"};

    JTable jt = new JTable(placeholderData, columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(header, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);

    jbtBuy.addActionListener(new BuyListener());
  }

  class BuyListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // if stock of such a name exists, and that the securities
      // has enough balance to buy the specified number of shares
      String stockName = jtfName.getText();
      if (test()) { // there is no stock of this name
        String msg = "There does not exist a stock with the name "+
        stockName + ".";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();

      } else if (test()) { // the number of shares is negative
        String msg = "You can only buy positive number of shares.";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();

      } else if (test()) { // securities does not have enough balance
        // to buy the specified number of shares
        String msg = "Your securities account does not have enough " +
        "balance to buy the specified number of shares.";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();

      } else { // then we can buy

      }
    }
  }

  private boolean test() {
    return true;
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "View Stocks");
    this.setSize( 1000, 600 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
