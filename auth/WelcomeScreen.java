package auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeScreen extends JFrame implements ActionListener {

    JButton btnEnter;

    public WelcomeScreen() {
        setTitle("Welcome to CITYINFO");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout());

        JLabel lblWelcome = new JLabel("CITYINFO", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Verdana", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(0, 102, 204));

        btnEnter = new JButton("Enter");
        btnEnter.setFont(new Font("Arial", Font.PLAIN, 18));
        btnEnter.addActionListener(this);

        add(lblWelcome, BorderLayout.CENTER);
        add(btnEnter, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEnter) {
            dispose(); // Close welcome window
            new Register(); // Open registration window
        }
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}
