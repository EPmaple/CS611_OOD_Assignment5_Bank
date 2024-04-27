package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ManagerViewStocksFrame extends JFrame{
  private JTextField jtfUpdate = new JTextField("Enter name of stock");
  private JTextField jtfUpdatePrice = new JTextField("Enter price of stock");

  private JTextField jtfDelete = new JTextField("Enter name of stock to delete");

  private JTextField jtfAddStockSymbol = new JTextField("Stock symbol");
  private JTextField jtfAddStockName = new JTextField("Stock name");
  private JTextField jtfAddStockPrice = new JTextField("Stock price");

  private JComboBox<String> jcbPanelOptions;
  private CardLayout cardLayout = new CardLayout();
  private JPanel cardPanel;

  public ManagerViewStocksFrame() {
    // to create the panel responsible for updating the price of a
    // stock
    JLabel jlbUpdate = new JLabel("Enter the name, and the price of the stock you want to update it to");
    JPanel updateMsgPanel = new JPanel();
    updateMsgPanel.add(jlbUpdate);

    JButton jbtUpdateConfirm = new JButton("Confirm");
    JPanel updateActionPanel = new JPanel(new GridLayout(0, 3));
    updateActionPanel.add(jtfUpdate);
    updateActionPanel.add(jtfUpdatePrice);
    updateActionPanel.add(jbtUpdateConfirm);

    JPanel updatePanel = new JPanel(new GridLayout(2, 0));
    updatePanel.add(updateMsgPanel);
    updatePanel.add(updateActionPanel);

    // *************************
    // creating a panel responsible for deleting an existing stock
    JButton jbtDeleteConfirm = new JButton("Confirm");
    JPanel deletePanel = new JPanel(new GridLayout(0, 2));
    deletePanel.add(jtfDelete);
    deletePanel.add(jbtDeleteConfirm);

    // *************************
    // creating a panel responsible for adding a new stock of a 
    // specified price to the stock market
    JLabel jlbAdd = new JLabel("Enter the symbol, name, and price of the stock you want to add.");
    JPanel addMsgPanel = new JPanel();
    addMsgPanel.add(jlbAdd);

    JButton jbtAddStockConfirm = new JButton("Confirm");
    JPanel addActionPanel = new JPanel(new GridLayout(0, 4));
    addActionPanel.add(jtfAddStockSymbol);
    addActionPanel.add(jtfAddStockName);
    addActionPanel.add(jtfAddStockPrice);
    addActionPanel.add(jbtAddStockConfirm);

    JPanel addPanel = new JPanel(new GridLayout(2, 0));
    addPanel.add(addMsgPanel);
    addPanel.add(addActionPanel);

    // Combobox for selecting panels
    String[] panelOptions = {"Update", "Add", "Delete"};
    jcbPanelOptions = new JComboBox<>(panelOptions);

    // setting up the cardlayout for choosing actions to be done
    // by the manager
    cardPanel = new JPanel(cardLayout);
    cardPanel.add(updatePanel, "Update");
    cardPanel.add(addPanel, "Add");
    cardPanel.add(deletePanel, "Delete");

    JPanel header = new JPanel(new GridLayout(0, 2));
    header.add(jcbPanelOptions);
    header.add(cardPanel);

    // ***************************

    String placeholderData[][] = {
      {"1", "2", "3", "4"},
      {"2", "Tony", "Created savings with $2", "8"}
    };
    String columnHeaders[] = {"Symbol", "Name", "Current Price", "Percent Fluctuation"};

    JTable jt = new JTable(placeholderData, columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(header, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);

    // ***************************

    jcbPanelOptions.addActionListener(new PanelOptionsListener());

    jbtUpdateConfirm.addActionListener(new UpdateListener());
    jbtDeleteConfirm.addActionListener(new DeleteListener());
    jbtAddStockConfirm.addActionListener(new AddStockListener());
  }

  // an actionlistener for the combobox to switch between panels
  // based on combobox selection
  class PanelOptionsListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String panelName = jcbPanelOptions.getItemAt(jcbPanelOptions.getSelectedIndex());
      cardLayout.show(cardPanel, panelName);
    }
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

  class AddStockListener implements ActionListener {
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
