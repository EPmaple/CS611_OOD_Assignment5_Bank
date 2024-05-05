package frontend;

import bank.Bank;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CreateCustomerAccountFrame extends JFrame{
  private Bank bank = Bank.get_bank();

  private JTextField jtfUsername = new JTextField();
  private JPasswordField jtfPassword = new JPasswordField();
  private JPasswordField jtfRePassword = new JPasswordField();
  private JLabel jlbMessage = new JLabel();

  public CreateCustomerAccountFrame() {
    JLabel jlbUsername = new JLabel("Username:");
    JPanel usernamePanel = new JPanel();
    usernamePanel.setLayout(new GridLayout(0, 2));
    usernamePanel.add(jlbUsername);
    usernamePanel.add(jtfUsername);

    JLabel jlbPassword = new JLabel("Password:");
    JPanel passwordPanel = new JPanel();
    passwordPanel.setLayout(new GridLayout(0, 2));
    passwordPanel.add(jlbPassword);
    passwordPanel.add(jtfPassword);

    JLabel jlbRePassword = new JLabel("Re-Password:");
    JPanel rePasswordPanel = new JPanel();
    rePasswordPanel.setLayout(new GridLayout(0, 2));
    rePasswordPanel.add(jlbRePassword);
    rePasswordPanel.add(jtfRePassword);

    JButton jbtCreate = new JButton("Create Account");

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(5, 0));
    mainPanel.add(usernamePanel);
    mainPanel.add(passwordPanel);
    mainPanel.add(rePasswordPanel);
    mainPanel.add(jbtCreate);
    mainPanel.add(jlbMessage);

    add(mainPanel); // REMEMBER to add mainPanel!!!

    // associate events to button actions
    jbtCreate.addActionListener(new CreateAccountListener());
  }

  class CreateAccountListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String username = jtfUsername.getText();
      // jtfPassword.getPassword() returns char[], and is converted to String
      String password = new String(jtfPassword.getPassword());
      String rePassword = new String(jtfRePassword.getPassword());

      String msg = "";

      if (!password.equals(rePassword)) {
        jlbMessage.setText("Passwords do not match.");
        // System.out.println("Passwords do not match.");

      } else {
        if (bank.createUser(username, password)) {
          msg = "Account successfully created.";
          // showMessageDialog() is a blocking call, which pauses the execution
          // of the subsequent code until the dialog is dismissed by the user 
          JOptionPane.showMessageDialog(CreateCustomerAccountFrame.this, msg);
          dispose();

        } else {
          jlbMessage.setText("Failed to create account.");
          // System.out.println("Failed to create account.");
        }
      }
    }
  }

  public void showWindow() {
    // init frame info
    this.setTitle( "Create Customer Account" );
    this.setSize( 300, 200 );
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    this.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    this.setVisible(true);
  }
}
