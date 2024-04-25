package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class RecoverPasswordFrame extends JFrame{
  private Middleware mwInstance = Middleware.getInstance();
  private JTextField jtfUsername = new JTextField();
  private JLabel jlbMessage = new JLabel();


  public RecoverPasswordFrame() {
    JLabel jlbPrompt = new JLabel("Enter the username you want ti recover the password.");

    JLabel jlbUsername = new JLabel("Username:");
    JPanel usernamePanel = new JPanel();
    usernamePanel.setLayout(new GridLayout(0, 2));
    usernamePanel.add(jlbUsername);
    usernamePanel.add(jtfUsername);

    JButton jbtRecover = new JButton("Recover Password");

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(4, 0));
    mainPanel.add(jlbPrompt);
    mainPanel.add(usernamePanel);
    mainPanel.add(jbtRecover);
    mainPanel.add(jlbMessage);

    add(mainPanel);

    jbtRecover.addActionListener(new RecoverPasswordListener());
  }

  class RecoverPasswordListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String username = jtfUsername.getText();

      String password = mwInstance.getUsernameToPassword().get(username);

      if (password == null) {
        jlbMessage.setText("Invalid username.");
      } else {
        jlbMessage.setText("Password for " + username + ": " + password);
      }
    }
  }

  public static void showWindow() {
    // create a new frame
    JFrame recoverPasswordFrame = new RecoverPasswordFrame();

    // init frame info
    recoverPasswordFrame.setTitle( "Recover Password" );
    recoverPasswordFrame.setSize( 300, 200 );
    recoverPasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
    recoverPasswordFrame.setLocationRelativeTo(null); // Center the frame on the screen

    // turn it on 
    recoverPasswordFrame.setVisible(true);
  }
}
