package modules;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ATMsAdmin extends JFrame implements ActionListener {
    private JTextField bankField, locationField, cityField;
    private JButton addButton, deleteButton, viewButton;

    public ATMsAdmin() {
        setTitle("Manage ATMs");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add / Delete / View ATMs");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Bank Name:"), gbc);
        gbc.gridx = 1;
        bankField = new JTextField();
        panel.add(bankField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        locationField = new JTextField();
        panel.add(locationField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        cityField = new JTextField();
        panel.add(cityField, gbc);

        addButton = new JButton("Add ATM");
        deleteButton = new JButton("Delete by Bank Name");
        viewButton = new JButton("View All ATMs");

        gbc.gridx = 0; gbc.gridy++;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        panel.add(deleteButton, gbc);
        gbc.gridx = 1; gbc.gridy++;
        panel.add(viewButton, gbc);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) addATM();
        else if (e.getSource() == deleteButton) deleteATM();
        else if (e.getSource() == viewButton) new ATMsViewer();
    }

    private void addATM() {
        String bank = bankField.getText().trim();
        String location = locationField.getText().trim();
        String city = cityField.getText().trim();

        if (bank.isEmpty() || location.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO atms (bank_name, location, city) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bank);
            ps.setString(2, location);
            ps.setString(3, city);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "ATM added successfully.");
            bankField.setText(""); locationField.setText(""); cityField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteATM() {
        String bank = JOptionPane.showInputDialog(this, "Enter bank name to delete:");
        if (bank == null || bank.trim().isEmpty()) return;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM atms WHERE bank_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bank.trim());
            int deleted = ps.executeUpdate();
            if (deleted > 0) JOptionPane.showMessageDialog(this, "ATM deleted.");
            else JOptionPane.showMessageDialog(this, "ATM not found.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATMsAdmin::new);
    }
}
