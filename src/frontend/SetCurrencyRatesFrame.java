package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.util.Map;
import java.awt.*;

public class SetCurrencyRatesFrame extends JFrame implements SetRatesListener{
  private CurrencyModel cmInstance = CurrencyModel.getInstance();

  private JComboBox<String> jcbFromCurrency = cmInstance.createCurrencyComboBox();
  private JComboBox<String> jcbToCurrency = cmInstance.createCurrencyComboBox();
  private JTextField jtfRate = new JTextField();

  public SetCurrencyRatesFrame() {
    cmInstance.addSetRatesListener(this);

    JLabel jlbFrom = new JLabel("From");
    JLabel jlbTo = new JLabel("To");
    JLabel jlbRate = new JLabel("Rate");
    JLabel jlbEmpty = new JLabel();
    JPanel header = new JPanel(new GridLayout(0, 4));
    header.add(jlbFrom);
    header.add(jlbTo);
    header.add(jlbRate);
    header.add(jlbEmpty);

    JButton jbtConfirm = new JButton("Set Rate");
    JPanel inputPanel = new JPanel(new GridLayout(0, 4));
    inputPanel.add(jcbFromCurrency);
    inputPanel.add(jcbToCurrency);
    inputPanel.add(jtfRate);
    inputPanel.add(jbtConfirm);

    JPanel layoutNorth = new JPanel(new GridLayout(2,0));
    layoutNorth.add(header);
    layoutNorth.add(inputPanel);

    // String placeholderData[][] = {
    //   {"$", "¥", "7.20"},
    //   {"$", "€", "0.70"}
    // };
    String[][] data = convertRatesToData();
    String columnHeaders[] = {"From", "To", "Rate"};
    JTable jt = new JTable(data, columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(layoutNorth, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);

    jbtConfirm.addActionListener(new ConfirmListener());
  }

  class ConfirmListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String fromCurrency = jcbFromCurrency.getItemAt(jcbFromCurrency.getSelectedIndex());
      String toCurrency = jcbToCurrency.getItemAt(jcbToCurrency.getSelectedIndex());

      try {
        double rate = Double.parseDouble(jtfRate.getText());

        if (fromCurrency.equals(toCurrency)) {
          String msg = "You cannot set the conversion rate from one currency" +
          " to itself.";
          JOptionPane.showMessageDialog(rootPane, msg);
          // PopupFrame popup = new PopupFrame(msg);
          // popup.showWindow();
  
        } else if (rate < 0) {
          String msg = "The conversion rate cannot be zero or negative.";
          JOptionPane.showMessageDialog(rootPane, msg);
  
        } else { // then we can convert
          String msg = "Successfully set the conversion rate from " +
          fromCurrency + " to " + toCurrency + " to " + rate;
          JOptionPane.showMessageDialog(rootPane, msg);
  
          // logic for updating this and make it propagate through all windows
          cmInstance.setRate(fromCurrency, toCurrency, rate);
        }
        
      } catch (NumberFormatException err) {
        String msg = "Please enter a valid positive number";
        JOptionPane.showMessageDialog(rootPane, msg);
      }

    }
  }

  private String[][] convertRatesToData() {
    Map<String, Map<String, Double>> rates = cmInstance.getRates();

    // Calculate total number of entries
    int totalEntries = rates.values().stream()
    .mapToInt(map -> map.size())
    .sum();


    String[][] data = new String[totalEntries][3];
    int index = 0;

    for (Map.Entry<String, Map<String, Double>> entry : rates.entrySet()) {
      String currencyFrom = entry.getKey();
      Map<String, Double> innerMap = entry.getValue();

      for (Map.Entry<String, Double> innerEntry : innerMap.entrySet()) {
        String currencyTo = innerEntry.getKey();
        double rate = innerEntry.getValue();

        data[index][0] = currencyFrom;
        data[index][1] = currencyTo;
        data[index][2] = rate+"";

        index++;
      }
    }

    return data;
  }

  public void ratesUpdate() {
    String msg = "The currency in use has been changed, or the current" +
    " currency's conversion rate has been changed.";
    regenerateFrame(msg);
  }

  public SetCurrencyRatesFrame regenerateFrame(String msg) {
    JOptionPane.showMessageDialog(rootPane, msg);
    cmInstance.removeSetRatesListener(this);
    this.dispose();
    SetCurrencyRatesFrame setCurrencyRatesFrame = new SetCurrencyRatesFrame();
    setCurrencyRatesFrame.showWindow();
    return setCurrencyRatesFrame;
  }
  
  public void showWindow() {
    // init frame info
    this.setTitle( "Set Currency Rates");
    this.setSize( 300, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
