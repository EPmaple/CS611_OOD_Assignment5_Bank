package frontend;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class UserSelectionFrame extends JFrame{
  public UserSelectionFrame() {
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
    CustomerListener cl = new CustomerListener();
    jbtCustomer.addActionListener(cl);
  }

  class ManagerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      System.out.println("Manager button clicked");
    }
  }

  class CustomerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      System.out.println("Customer button clicked");
      CustomerLoginFrame customerLoginFrame = new CustomerLoginFrame();
      customerLoginFrame.showWindow();
      // CustomerWindow customerWindow = new CustomerWindow();
      // customerWindow.setVisible(true);
    }
  }

  public static void main( String[] args ) {
    // create a new frame
    JFrame userSelectionFrame = new UserSelectionFrame();

    // init frame info
    userSelectionFrame.setTitle( "User Selection" );
    userSelectionFrame.setSize( 200, 150 );
    userSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE ); // may change this to DISPOSE_ON_CLOSE
    userSelectionFrame.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    userSelectionFrame.setVisible(true);
  }
}


// import javax.swing.*;    
// import java.awt.event.*;    
// public class ComboBoxExample {    
// JFrame f;    
// ComboBoxExample(){    
//     f=new JFrame("ComboBox Example");   
//     final JLabel label = new JLabel();          
//     label.setHorizontalAlignment(JLabel.CENTER);  
//     label.setSize(400,100);  
//     JButton b=new JButton("Show");  
//     b.setBounds(200,100,75,20);  
//     String languages[]={"C","C++","C#","Java","PHP"};        
//     final JComboBox cb=new JComboBox(languages);    
//     cb.setBounds(50, 100,90,20);    
//     f.add(cb); f.add(label); f.add(b);    
//     f.setLayout(null);    
//     f.setSize(350,350);    
//     f.setVisible(true);       
//     b.addActionListener(new ActionListener() {  
//         public void actionPerformed(ActionEvent e) {       
// String data = "Programming language Selected: "   
//    + cb.getItemAt(cb.getSelectedIndex());  
// label.setText(data);  
// }  
// });           
// }    
// public static void main(String[] args) {    
//     new ComboBoxExample();         
// }    
// }    