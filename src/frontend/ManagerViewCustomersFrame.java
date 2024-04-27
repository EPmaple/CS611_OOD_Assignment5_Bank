package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ManagerViewCustomersFrame extends JFrame{
  private JTextField jtfViewDetails = new JTextField("Enter name of customer to view details");

  public ManagerViewCustomersFrame() {
    JButton jbtViewDetailsConfirm = new JButton("Confirm");
    JPanel viewPanel = new JPanel(new GridLayout(0, 2));
    viewPanel.add(jtfViewDetails);
    viewPanel.add(jbtViewDetailsConfirm);

    // ***************************

    String placeholderData[][] = {
      {"1", "Tony", "Created checking with $1"},
      {"2", "Tony", "Created savings with $2"}
    };
    String columnHeaders[] = {"ID", "NAME", "Transaction"};

    JTable jt = new JTable(placeholderData, columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(viewPanel, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);

    jbtViewDetailsConfirm.addActionListener(new ViewDetailsListener());
  }

  class ViewDetailsListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * First check if a stock of this name exists
       */
      String customerName = jtfViewDetails.getText();
      if (customerExists()) { // if a customer with this name does exist
        // open up update frame
      } else {
        String msg = "There does not exist a customer with the name "+
        customerName + ".";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();
      }
    }
  }

  private boolean customerExists() {
    return true;
  }
  

  public void showWindow() {
    // init frame info
    this.setTitle( "View Customers");
    this.setSize( 1000, 600 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
