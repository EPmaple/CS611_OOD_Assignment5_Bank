package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DailyReportFrame extends JFrame{
  
  public DailyReportFrame() {
    String placeholderData[][] = {
      {"1", "Tony", "Created checking with $1"},
      {"2", "Tony", "Created savings with $2"}
    };
    String columnHeaders[] = {"ID", "NAME", "Transaction"};

    JTable jt = new JTable(placeholderData, columnHeaders);
    JScrollPane sp = new JScrollPane(jt);

    add(sp);
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Daily Reports on Transactions");
    this.setSize( 1000, 600 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
