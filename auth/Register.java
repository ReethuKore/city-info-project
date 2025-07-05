package auth;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Register extends JFrame implements ActionListener {

    JLabel lblUsername, lblPassword, lblRole;
    JTextField txtUsername;
    JPasswordField txtPassword;
    JComboBox<String> roleCombo;
    JButton btnRegister, btnLogin;

    public Register() {
        setTitle("City Info - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // center window

        // Initialize components
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");
        lblRole = new JLabel("Role:");
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        roleCombo = new JComboBox<>(new String[]{"user", "admin"});
        btnRegister = new JButton("Register");
        btnLogin = new JButton("Login");

        btnRegister.addActionListener(this);
        btnLogin.addActionListener(this);

        // Use GridBagLayout for flexible UI
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblUsername, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblPassword, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(lblRole, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtUsername, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtPassword, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(roleCombo, gbc);

        // Buttons centered
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnLogin);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            registerUser();
        } else if (e.getSource() == btnLogin) {
            dispose();
            new Login();
        }
    }

    private void registerUser() {
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();
        String role = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registered successfully!");
                dispose();
                new Login();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
