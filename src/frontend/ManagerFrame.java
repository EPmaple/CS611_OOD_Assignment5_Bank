package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ManagerFrame extends JFrame{
  public ManagerFrame() {
    JButton jbtViewCustomers = new JButton("View Customers");

    JButton jbtMaintainStocks = new JButton("Maintain Stocks");

    JButton jbtSetRates = new JButton("Set Currency Rates");

    JPanel mainPanel = new JPanel(new GridLayout(3, 0));
    mainPanel.add(jbtViewCustomers);
    mainPanel.add(jbtMaintainStocks);
    mainPanel.add(jbtSetRates);

    add(mainPanel);

    jbtViewCustomers.addActionListener(new ViewCustomersListener());
    jbtMaintainStocks.addActionListener(new MaintainStocksListener());
    jbtSetRates.addActionListener(new SetRatesListener());
  }

  class ViewCustomersListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      ManagerViewCustomersFrame mvcFrame = new ManagerViewCustomersFrame();
      mvcFrame.showWindow();
    }
  }

  class MaintainStocksListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      ManagerViewStocksFrame mvsFrame = new ManagerViewStocksFrame();
      mvsFrame.showWindow();
    }
  }

  class SetRatesListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      SetCurrencyRatesFrame scrFrame = new SetCurrencyRatesFrame();
      scrFrame.showWindow();
    }
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Manger");
    this.setSize( 500, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
