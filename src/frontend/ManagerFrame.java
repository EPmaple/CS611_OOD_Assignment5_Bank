package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import role.Manager;

public class ManagerFrame extends JFrame implements TimeModelListener{
  private Manager manager;
  private TimeModel timeInstance = TimeModel.getInstance();

  public ManagerFrame(Manager manager) {
    timeInstance.addTimeModelListener(this);
    this.manager = manager;

    JButton jbtViewCustomers = new JButton("View Customers");

    JButton jbtMaintainStocks = new JButton("Maintain Stocks");

    JButton jbtSetRates = new JButton("Set Currency Rates");

    JButton jbtViewDailyReports = new JButton("View Daily Reports");

    JPanel rightPanel = new JPanel(new GridLayout(4, 0));
    rightPanel.add(jbtViewCustomers);
    rightPanel.add(jbtMaintainStocks);
    rightPanel.add(jbtSetRates);
    rightPanel.add(jbtViewDailyReports);

    JPanel timePanel = timeInstance.createTimePanel();
    JLabel jlbMsg = new JLabel("Select action");
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(timePanel, BorderLayout.NORTH);
    topPanel.add(jlbMsg, BorderLayout.CENTER); 

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(rightPanel, BorderLayout.CENTER);

    add(mainPanel);

    jbtViewCustomers.addActionListener(new ViewCustomersListener());
    jbtMaintainStocks.addActionListener(new MaintainStocksListener());
    jbtSetRates.addActionListener(new SetRatesListener());
    jbtViewDailyReports.addActionListener(new ViewDailyReportsListener());
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

  class ViewDailyReportsListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      DailyReportFrame drFrame = new DailyReportFrame(manager);
      drFrame.showWindow();
    }
  }

  public void timeUpdate() {
    String msg = "There is a time update";
    regenerateFrame(msg);
  }

  public ManagerFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    timeInstance.removeTimeModelListener(this);
    this.dispose();
    ManagerFrame managerFrame = new ManagerFrame(manager);
    managerFrame.showWindow();
    return managerFrame;
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Manger");
    this.setSize( 300, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
