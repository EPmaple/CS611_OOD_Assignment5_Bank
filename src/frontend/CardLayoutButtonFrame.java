package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayoutButtonFrame extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JButton button1;
    private JButton button2;
    private boolean bool; // 控制显示哪个按钮

    public CardLayoutButtonFrame() {
        initializeUI();
    }

    private void initializeUI() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 创建按钮
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");

        // 添加按钮到卡片面板
        cardPanel.add(button1, "Button 1");
        cardPanel.add(button2, "Button 2");

        // 添加卡片面板到框架
        this.add(cardPanel, BorderLayout.CENTER);

        // 创建一个切换按钮的按钮
        JButton toggleButton = new JButton("Toggle");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bool = !bool; // 切换布尔值
                updateButtons();
            }
        });

        // 添加切换按钮到框架
        this.add(toggleButton, BorderLayout.SOUTH);

        // 设置框架属性
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Card Layout Example");
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
    }

    private void updateButtons() {
        if (bool) {
            cardLayout.show(cardPanel, "Button 1");
        } else {
            cardLayout.show(cardPanel, "Button 2");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CardLayoutButtonFrame().setVisible(true);
            }
        });
    }
}
