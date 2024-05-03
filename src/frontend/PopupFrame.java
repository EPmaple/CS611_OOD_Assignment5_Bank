package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PopupFrame extends JFrame{
  JLabel jlbMsg = new JLabel();
  
  public PopupFrame(String msg) {
    jlbMsg.setText(msg);

    JButton jbtOk = new JButton("Ok");

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(2, 0));
    mainPanel.add(jlbMsg);
    mainPanel.add(jbtOk);

    add(mainPanel);

    jbtOk.addActionListener(new OKListener());
  }

  class OKListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      dispose();
    }
  }

  public void showWindow() {

    // init frame info
    // this.setTitle( "Creating a " + accountType + ": " );
    this.setSize( 300, 200 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}