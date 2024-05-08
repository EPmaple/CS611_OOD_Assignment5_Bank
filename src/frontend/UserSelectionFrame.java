package frontend;
import javax.swing.*;

import role.Manager;
import utility.*;
import role.*;

import java.util.List;

import java.awt.event.*;
import java.awt.*;

public class UserSelectionFrame extends JFrame{
  List<Customer> customers;
  public UserSelectionFrame(List<Customer> customers) {
    this.customers = customers;
    // create the buttons
    JButton jbtManager = new JButton("Manager");
    JButton jbtCustomer = new JButton("Customer");

    // create a grid panel to place the buttons in 
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0,2));
    panel.add(jbtManager);
    panel.add(jbtCustomer);

    // add the panel to the frame
    add(panel);

    // Associate events to each button action
    ManagerListener ml = new ManagerListener();
    jbtManager.addActionListener(ml);
    AccountListener cl = new AccountListener();
    jbtCustomer.addActionListener(cl);
  }

  class ManagerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      ManagerFrame managerFrame = new ManagerFrame(Manager.get_manager());
      managerFrame.showWindow();
    }
  }

  class AccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // System.out.println("Customer button clicked");
      CustomerLoginFrame customerLoginFrame = new CustomerLoginFrame();
      customerLoginFrame.showWindow();
      // CustomerWindow customerWindow = new CustomerWindow();
      // customerWindow.setVisible(true);
    }
  }

  // need to start by passing in an entire list of customers, so 
  // we won't recreate them and them lose reference
  public static void startGUI() {
    // create a new frame
    List<Customer> customers = Read.readUsers();
    JFrame userSelectionFrame = new UserSelectionFrame(customers);

    // init frame info
    userSelectionFrame.setTitle( "User Selection" );
    userSelectionFrame.setSize( 200, 150 );
    userSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE ); // may change this to DISPOSE_ON_CLOSE
    userSelectionFrame.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    userSelectionFrame.setVisible(true);
  }
}
