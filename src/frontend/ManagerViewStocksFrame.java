package frontend;

import javax.swing.*;

import role.Manager;
import stock.*;
import utility.Read;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.awt.*;

import java.util.List;

public class ManagerViewStocksFrame extends JFrame implements StockListener{
  private Middleware mwInstance = Middleware.getInstance();
  private Manager manager = Manager.get_manager();
  private CurrencyModel cmInstance = CurrencyModel.getInstance();
  private JTextField jtfUpdateStock = new JTextField("Enter name of stock");
  private JTextField jtfUpdatePrice = new JTextField("Enter price of stock");

  private JTextField jtfDeleteStock = new JTextField("Enter name of stock to delete");

  private JTextField jtfAddStockName = new JTextField("Stock name");
  private JTextField jtfAddStockPrice = new JTextField("Stock price");

  private JComboBox<String> jcbPanelOptions;
  private CardLayout cardLayout = new CardLayout();
  private JPanel cardPanel;

  public ManagerViewStocksFrame() {
    mwInstance.addStockListener(this);
    // to create the panel responsible for updating the price of a
    // stock
    JLabel jlbUpdate = new JLabel("Enter the name of the stock to update, and the corresponding price");
    JPanel updateMsgPanel = new JPanel();
    updateMsgPanel.add(jlbUpdate);

    JButton jbtUpdateConfirm = createUpdateButton();
    JPanel updateActionPanel = new JPanel(new GridLayout(0, 3));
    updateActionPanel.add(jtfUpdateStock);
    updateActionPanel.add(jtfUpdatePrice);
    updateActionPanel.add(jbtUpdateConfirm);

    JPanel updatePanel = new JPanel(new GridLayout(2, 0));
    updatePanel.add(updateMsgPanel);
    updatePanel.add(updateActionPanel);

    // *************************
    // creating a panel responsible for deleting an existing stock
    JButton jbtDeleteConfirm = createDeleteButton();
    JPanel deletePanel = new JPanel(new GridLayout(0, 2));
    deletePanel.add(jtfDeleteStock);
    deletePanel.add(jbtDeleteConfirm);

    // *************************
    // creating a panel responsible for adding a new stock of a 
    // specified price to the stock market
    JLabel jlbAdd = new JLabel("Enter the name, and price of the stock you want to add.");
    JPanel addMsgPanel = new JPanel();
    addMsgPanel.add(jlbAdd);

    JButton jbtAddStockConfirm = createAddStockButton();
    JPanel addActionPanel = new JPanel(new GridLayout(0, 3));
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

    String columnHeaders[] = {"Name", "Current Price", "Price Change"};

    JTable jt = new JTable(convertStocksToData(), columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(header, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);

    // ***************************

    jcbPanelOptions.addActionListener(new PanelOptionsListener());
  }

  private String[][] convertStocksToData() {
    List<Stock> stocks = Read.readStock();

    String[][] data = new String[stocks.size()][3];
    for (int i = 0; i < stocks.size(); i++) {
      Stock stock = stocks.get(i);

      if (stock.isOnSale()) {
        data[i][0] = stock.getName();
        double price = stock.getPrice();
        data[i][1] = cmInstance.convertToCurrentCurrency(price);
        data[i][2] = getStockPriceChange(stock);
      }
    }
    return data;
  }

  private String getStockPriceChange(Stock stock) {
    List<Double> prices = stock.getHistoryPrice();

    if (prices.size() <= 1) {
        return "-";
    }

    // Get the last price and the one before it to calculate the change
    double lastPrice = prices.get(prices.size() - 1);
    double previousPrice = prices.get(prices.size() - 2);

    if (previousPrice == 0) {
        return "Previous price is zero, cannot calculate percentage change";
    }

    double percentageChange = ((lastPrice - previousPrice) / previousPrice) * 100;

    // Format the percentage change to two decimal places
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(percentageChange) + "%";
  }

  // an actionlistener for the combobox to switch between panels
  // based on combobox selection
  class PanelOptionsListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String panelName = jcbPanelOptions.getItemAt(jcbPanelOptions.getSelectedIndex());
      cardLayout.show(cardPanel, panelName);
    }
  }

  private JButton createUpdateButton() {
    JButton jbtUpdateConfirm = new JButton("Confirm to Update");
    jbtUpdateConfirm.addActionListener(e -> {
      String stockName = jtfUpdateStock.getText();
      String stockPriceStr = jtfUpdatePrice.getText();

      try {
        double parsedPrice = Double.parseDouble(stockPriceStr);
        double stockPrice = cmInstance.convertToCurrencyForStorage(parsedPrice);

        if (stockPrice <= 0) {
          String msg = "Please enter a valid positive number to be the price of a stock";
          JOptionPane.showMessageDialog(rootPane, msg);

        } else {
          if (manager.changeStockPrice(stockName, stockPrice)) {
            String msg = "You've successfully updated the price of " +
            stockName + " to be " + cmInstance.convertToCurrentCurrency(stockPrice);
            JOptionPane.showMessageDialog(rootPane, msg);

          } else {
            String msg = "Please make sure you've correctly enter the name of the stock you want to update";
            JOptionPane.showMessageDialog(rootPane, msg);
          }
        }

      } catch (NumberFormatException err) {
        String msg = "Please enter a valid positive number to be the price of a stock";
        JOptionPane.showMessageDialog(rootPane, msg);
      }
    });
    return jbtUpdateConfirm;
  }

  private JButton createDeleteButton() {
    JButton jbtDeleteConfirm = new JButton("Confirm to Delete");
    jbtDeleteConfirm.addActionListener(e -> {
      String stockName = jtfDeleteStock.getText();
      if (manager.changeStockStatus(stockName, false)) {
        // we were able to "delete" the stock by setting the onsale
        // status of the stock to be false
        String msg = "The stock " + stockName + " has been deleted";
        JOptionPane.showMessageDialog(rootPane, msg);
      } else {
        String msg = "Please make sure you've correctly enter the name of the stock you want to delete";
        JOptionPane.showMessageDialog(rootPane, msg);
      }
    });
    return jbtDeleteConfirm;
  }

  private JButton createAddStockButton() {
    JButton jbtAddStockConfirm = new JButton("Confirm to Add");
    jbtAddStockConfirm.addActionListener(e -> {
      String stockName = jtfAddStockName.getText();
      String stockPriceStr = jtfAddStockPrice.getText();

      try {
        double parsedPrice = Double.parseDouble(stockPriceStr);
        double stockPrice = cmInstance.convertToCurrencyForStorage(parsedPrice);

        if (stockPrice <= 0) {
          String msg = "Please enter a valid positive number to be the price of a stock";
          JOptionPane.showMessageDialog(rootPane, msg);

        } else {
          /*
           * 1.) either the stock already exists with an onsale status of
           * false
           * 2.) this is a new stock be added 
           */
          Stock stock = Read.getStock(stockName);
          if (stock != null) {
            manager.changeStockStatus(stockName, true);
            manager.changeStockPrice(stockName, stockPrice);

          } else {
            manager.createStock(stockName, stockPriceStr);
          }
        }

      } catch (NumberFormatException err) {
        String msg = "Please enter a valid positive number to be the price of a stock";
        JOptionPane.showMessageDialog(rootPane, msg);
      }
    });
    return jbtAddStockConfirm;
  }

  public void stockUpdated() {
    String msg = "There is an update to the stock market.";
    regenerateFrame(msg);
  }

  private ManagerViewStocksFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    mwInstance.removeStockListener(this);
    this.dispose();
    ManagerViewStocksFrame mvsFrame = new ManagerViewStocksFrame();
    mvsFrame.showWindow();
    return mvsFrame;
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Manager View of Stocks");
    this.setSize( 1000, 600 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
