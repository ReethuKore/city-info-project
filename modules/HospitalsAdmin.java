package modules;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalsAdmin extends JFrame implements ActionListener {
    JTextField nameField, addressField, contactField, cityField, deleteIdField;
    JButton addButton, deleteButton, refreshButton;
    JTable table;
    DefaultTableModel model;

    public HospitalsAdmin() {
        setTitle("Hospital Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        nameField = new JTextField();
        addressField = new JTextField();
        contactField = new JTextField();
        cityField = new JTextField();
        addButton = new JButton("Add Hospital");
        addButton.addActionListener(this);

        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Hospital"));
        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Address:")); formPanel.add(addressField);
        formPanel.add(new JLabel("Contact:")); formPanel.add(contactField);
        formPanel.add(new JLabel("City:")); formPanel.add(cityField);
        formPanel.add(new JLabel()); formPanel.add(addButton);

        // Table Section
        model = new DefaultTableModel(new String[]{"ID", "Name", "Address", "Contact", "City"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Hospital List"));

        // Delete Panel
        JPanel deletePanel = new JPanel(new FlowLayout());
        deleteIdField = new JTextField(5);
        deleteButton = new JButton("Delete by ID");
        refreshButton = new JButton("Refresh");
        deleteButton.addActionListener(this);
        refreshButton.addActionListener(this);
        deletePanel.setBorder(BorderFactory.createTitledBorder("Delete Hospital"));
        deletePanel.add(new JLabel("ID:"));
        deletePanel.add(deleteIdField);
        deletePanel.add(deleteButton);
        deletePanel.add(refreshButton);

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(deletePanel, BorderLayout.SOUTH);

        fetchHospitals();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) addHospital();
        else if (e.getSource() == deleteButton) deleteHospital();
        else if (e.getSource() == refreshButton) fetchHospitals();
    }

    private void addHospital() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String contact = contactField.getText().trim();
        String city = cityField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || contact.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO hospitals (name, address, contact, city) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, contact);
            ps.setString(4, city);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Hospital added successfully!");
            clearForm();
            fetchHospitals();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteHospital() {
        String id = deleteIdField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID to delete.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM hospitals WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Hospital deleted.");
                fetchHospitals();
                deleteIdField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Hospital not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void fetchHospitals() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM hospitals";
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading hospitals: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameField.setText("");
        addressField.setText("");
        contactField.setText("");
        cityField.setText("");
    }

    public static void main(String[] args) {
        new HospitalsAdmin();
    }
}
