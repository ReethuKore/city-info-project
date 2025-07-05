package modules;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HostelsAdmin extends JFrame implements ActionListener {
    JTextField nameField, addressField, contactField, cityField, deleteIdField;
    JButton addButton, deleteButton, refreshButton;
    JTable table;
    DefaultTableModel model;

    public HostelsAdmin() {
        setTitle("Hostel Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top: Add form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        nameField = new JTextField();
        addressField = new JTextField();
        contactField = new JTextField();
        cityField = new JTextField();
        addButton = new JButton("Add Hostel");
        addButton.addActionListener(this);

        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Hostel"));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("City:"));
        formPanel.add(cityField);
        formPanel.add(new JLabel());
        formPanel.add(addButton);

        // Center: Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Address", "Contact", "City"}, 0);
        table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Hostels List"));

        // Bottom: Delete
        JPanel deletePanel = new JPanel(new FlowLayout());
        deleteIdField = new JTextField(5);
        deleteButton = new JButton("Delete by ID");
        refreshButton = new JButton("Refresh");
        deleteButton.addActionListener(this);
        refreshButton.addActionListener(this);
        deletePanel.setBorder(BorderFactory.createTitledBorder("Delete Hostel"));
        deletePanel.add(new JLabel("ID:"));
        deletePanel.add(deleteIdField);
        deletePanel.add(deleteButton);
        deletePanel.add(refreshButton);

        // Add all panels
        add(formPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(deletePanel, BorderLayout.SOUTH);

        fetchHostels();

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addHostel();
        } else if (e.getSource() == deleteButton) {
            deleteHostel();
        } else if (e.getSource() == refreshButton) {
            fetchHostels();
        }
    }

    private void addHostel() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String contact = contactField.getText().trim();
        String city = cityField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || contact.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO hostels (name, address, contact, city) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, contact);
            ps.setString(4, city);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Hostel added successfully.");
                clearForm();
                fetchHostels();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add hostel.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteHostel() {
        String idText = deleteIdField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter an ID to delete.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM hostels WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idText));
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Hostel deleted.");
                deleteIdField.setText("");
                fetchHostels();
            } else {
                JOptionPane.showMessageDialog(this, "ID not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void fetchHostels() {
        model.setRowCount(0); // clear existing rows
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM hostels";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contact"),
                    rs.getString("city")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching hostels: " + ex.getMessage());
        }
    }

    private void clearForm() {
        nameField.setText("");
        addressField.setText("");
        contactField.setText("");
        cityField.setText("");
    }

    public static void main(String[] args) {
        new HostelsAdmin();
    }
}
