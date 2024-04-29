package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public abstract class BackButtonFrame extends JFrame{
  protected JFrame priorFrame;
  protected JPanel outerPanel;

  public BackButtonFrame(JFrame priorFrame) {
    this.priorFrame = priorFrame;

    // to create a back button in the upper left corner
    JButton jbtBack = new JButton("Back");

    outerPanel = new JPanel(new BorderLayout());
    JPanel innerPanel = new JPanel(new BorderLayout());

    innerPanel.add(jbtBack, BorderLayout.WEST);
    outerPanel.add(innerPanel, BorderLayout.NORTH);

    add(outerPanel);

    jbtBack.addActionListener(new BackListener());
  }

  class BackListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (priorFrame != null) {
        priorFrame.setVisible(true);
        dispose(); // dispose current frame
      }
    }
  }

  // public abstract void showWindow();
}
