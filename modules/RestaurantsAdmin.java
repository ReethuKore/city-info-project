// 1. RestaurantsAdmin.java
package modules;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RestaurantsAdmin extends JFrame implements ActionListener {
    private JTextField nameField, cuisineField, addressField, cityField;
    private JButton addButton, deleteButton, viewButton;

    public RestaurantsAdmin() {
        setTitle("Manage Restaurants");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Add / Delete / View Restaurants");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Restaurant Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField();
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Cuisine Type:"), gbc);
        gbc.gridx = 1;
        cuisineField = new JTextField();
        panel.add(cuisineField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField();
        panel.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        cityField = new JTextField();
        panel.add(cityField, gbc);

        addButton = new JButton("Add Restaurant");
        deleteButton = new JButton("Delete by Name");
        viewButton = new JButton("View All Restaurants");

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
        if (e.getSource() == addButton) addRestaurant();
        else if (e.getSource() == deleteButton) deleteRestaurant();
        else if (e.getSource() == viewButton) new RestaurantsViewer();
    }

    private void addRestaurant() {
        String name = nameField.getText().trim();
        String cuisine = cuisineField.getText().trim();
        String address = addressField.getText().trim();
        String city = cityField.getText().trim();

        if (name.isEmpty() || cuisine.isEmpty() || address.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO restaurants (name, cuisine, address, city) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, cuisine);
            ps.setString(3, address);
            ps.setString(4, city);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Restaurant added successfully.");
            nameField.setText(""); cuisineField.setText(""); addressField.setText(""); cityField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteRestaurant() {
        String name = JOptionPane.showInputDialog(this, "Enter restaurant name to delete:");
        if (name == null || name.trim().isEmpty()) return;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM restaurants WHERE name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name.trim());
            int deleted = ps.executeUpdate();
            if (deleted > 0) JOptionPane.showMessageDialog(this, "Restaurant deleted.");
            else JOptionPane.showMessageDialog(this, "Restaurant not found.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantsAdmin::new);
    }
}
