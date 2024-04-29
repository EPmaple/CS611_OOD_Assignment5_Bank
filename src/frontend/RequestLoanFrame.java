package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class RequestLoanFrame extends JFrame{
  public RequestLoanFrame() {
    JLabel jlbMsg = new JLabel("Yet to be implemented");

    JPanel mainPanel = new JPanel();
    mainPanel.add(jlbMsg);

    add(mainPanel); // add the panel to the frame
  }

  public void showWindow() {
    this.setTitle( "Requesting loan");
    this.setSize( 500, 300 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
