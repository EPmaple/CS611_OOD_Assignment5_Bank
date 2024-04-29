package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SetCurrencyRatesFrame extends JFrame{
  private String currency[] = {"¥", "$", "€"};
  private JComboBox<String> jcbFromCurrency = new JComboBox<String>(currency);
  private JComboBox<String> jcbToCurrency = new JComboBox<String>(currency);
  private JTextField jtfRate = new JTextField();

  public SetCurrencyRatesFrame() {
    JLabel jlbFrom = new JLabel("From");
    JLabel jlbTo = new JLabel("To");
    JLabel jlbRate = new JLabel("Rate");
    JLabel jlbEmpty = new JLabel();
    JPanel header = new JPanel(new GridLayout(0, 4));
    header.add(jlbFrom);
    header.add(jlbTo);
    header.add(jlbRate);
    header.add(jlbEmpty);

    JButton jbtConfirm = new JButton("Confirm");
    JPanel inputPanel = new JPanel(new GridLayout(0, 4));
    inputPanel.add(jcbFromCurrency);
    inputPanel.add(jcbToCurrency);
    inputPanel.add(jtfRate);
    inputPanel.add(jbtConfirm);

    JPanel layoutNorth = new JPanel(new GridLayout(2,0));
    layoutNorth.add(header);
    layoutNorth.add(inputPanel);

    String placeholderData[][] = {
      {"$", "¥", "7.20"},
      {"$", "€", "0.70"}
    };
    String columnHeaders[] = {"From", "To", "Rate"};
    JTable jt = new JTable(placeholderData, columnHeaders);
    // JPanel tablePanel = new JPanel(jt);
    JScrollPane sp = new JScrollPane(jt);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(layoutNorth, BorderLayout.NORTH);
    mainPanel.add(sp, BorderLayout.CENTER);

    add(mainPanel);
  }

  class ConfirmListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String fromCurrency = jcbFromCurrency.getItemAt(jcbFromCurrency.getSelectedIndex());
      String toCurrency = jcbToCurrency.getItemAt(jcbToCurrency.getSelectedIndex());
      double rate = Double.parseDouble(jtfRate.getText());

      if (fromCurrency.equals(toCurrency)) {
        String msg = "You cannot set the conversion rate from one currency" +
        " to itself.";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();

      } else if (rate < 0) {
        String msg = "The conversion rate cannot be zero or negative.";
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();

      } else { // then we can convert
        String msg = "Successfully set the conversion rate from " +
        fromCurrency + " to " + toCurrency + " to " + rate;
        PopupFrame popup = new PopupFrame(msg);
        popup.showWindow();

        // logic for updating this and make it propagate through all windows
      }
    }
  }
  
  public void showWindow() {
    // init frame info
    this.setTitle( "Set Currency Rates");
    this.setSize( 600, 600 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
