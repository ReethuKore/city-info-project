package modules;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EducationAdmin extends JFrame implements ActionListener {
    private JTextField nameField, typeField, addressField, cityField;
    private JButton addButton, deleteButton, viewButton;

    public EducationAdmin() {
        setTitle("Manage Educational Institutions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use GridBagLayout for responsiveness
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add / Delete / View Education");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Institution Name:"), gbc);
        nameField = new JTextField();
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Type (School/College):"), gbc);
        typeField = new JTextField();
        gbc.gridx = 1;
        panel.add(typeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Address:"), gbc);
        addressField = new JTextField();
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("City:"), gbc);
        cityField = new JTextField();
        gbc.gridx = 1;
        panel.add(cityField, gbc);

        addButton = new JButton("Add Institution");
        deleteButton = new JButton("Delete by Name");
        viewButton = new JButton("View All");

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        panel.add(deleteButton, gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        panel.add(viewButton, gbc);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addInstitution();
        } else if (e.getSource() == deleteButton) {
            deleteInstitution();
        } else if (e.getSource() == viewButton) {
            new EducationViewer();
        }
    }

    private void addInstitution() {
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        String address = addressField.getText().trim();
        String city = cityField.getText().trim();

        if (name.isEmpty() || type.isEmpty() || address.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO education (name, type, address, city) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setString(3, address);
            ps.setString(4, city);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Institution added!");
            nameField.setText("");
            typeField.setText("");
            addressField.setText("");
            cityField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteInstitution() {
        String name = JOptionPane.showInputDialog(this, "Enter name of institution to delete:");
        if (name == null || name.trim().isEmpty()) return;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM education WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name.trim());

            int deleted = ps.executeUpdate();
            if (deleted > 0) {
                JOptionPane.showMessageDialog(this, "Deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No matching institution found.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EducationAdmin::new);
    }
}
