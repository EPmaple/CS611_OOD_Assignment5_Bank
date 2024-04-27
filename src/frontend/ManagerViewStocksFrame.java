package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ManagerViewStocksFrame extends JFrame{
  private JTextField jtfUpdate = new JTextField("Enter name of stock to update");
  private JTextField jtfDelete = new JTextField("Enter naem of stock to delete");


  public ManagerViewStocksFrame() {
    JButton jbtUpdateConfirm = new JButton("Confirm");
    JPanel updatePanel = new JPanel(new GridLayout(0, 2));
    updatePanel.add(jtfUpdate);
    updatePanel.add(jbtUpdateConfirm);

    JButton jbtDeleteConfirm = new JButton("Confirm");
    JPanel deletePanel = new JPanel(new GridLayout(0, 2));
    deletePanel.add(jtfDelete);
    deletePanel.add(jbtDeleteConfirm);

    JPanel header = new JPanel(new GridLayout(2, 0));
    header.add(updatePanel);
    header.add(deletePanel);

    // ***************************

    String placeholderData[][] = {
      {"1", "Tony", "Created checking with $1"},
      {"2", "Tony", "Created savings with $2"}
    };
    String columnHeaders[] = {"ID", "NAME", "Transaction"};

    JTable jt = new JTable(placeholderData, columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(header, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);

    jbtUpdateConfirm.addActionListener(new UpdateListener());
    jbtDeleteConfirm.addActionListener(new DeleteListener());
  }

  class UpdateListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      /*
       * First check if a stock of this name exists
       */
      String stockName = jtfUpdate.getText();
      if (stockExists()) {
        // open up update frame
      } else {
        String msg = "There does not exist a stock with the name "+
        stockName + ".";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();
      }
    }
  }

  class DeleteListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // String stockName = jtfUpdate.getText();
      // if (stockExists()) {
      //   // open up update frame
      // } else {
      //   String msg = "There does not exist a stock with the name "+
      //   stockName + ".";
      //   PopupFrame popup = new PopupFrame(msg);
      //   popup.showWindow();
      // }
    }
  }

  private boolean stockExists() {
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
