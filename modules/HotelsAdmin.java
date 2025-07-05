package modules;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.sql.*;

public class HotelsAdmin extends JFrame {
    private JTextField nameField, addressField, phoneField, cityField;
    private DefaultListModel<String> hotelListModel;
    private JList<String> hotelList;
    private int[] hotelIds;  // to track hotel IDs

    public HotelsAdmin() {
        setTitle("Manage Hotels");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // Header
        JLabel title = new JLabel("Hotel Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Hotel"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(20);
        cityField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; formPanel.add(addressField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; formPanel.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1; formPanel.add(cityField, gbc);

        JButton addButton = new JButton("Add Hotel");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(addButton, gbc);
        add(formPanel, BorderLayout.WEST);

        // List and delete panel
        JPanel listPanel = new JPanel(new BorderLayout(10, 10));
        listPanel.setBorder(BorderFactory.createTitledBorder("Existing Hotels"));

        hotelListModel = new DefaultListModel<>();
        hotelList = new JList<>(hotelListModel);
        JScrollPane scrollPane = new JScrollPane(hotelList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Selected");
        listPanel.add(deleteButton, BorderLayout.SOUTH);

        add(listPanel, BorderLayout.CENTER);

        // Event handling
        addButton.addActionListener(e -> addHotel());
        deleteButton.addActionListener(e -> deleteHotel());

        loadHotels();
        setVisible(true);
    }

    private void addHotel() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String city = cityField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO hotels (name, address, phone, city) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phone);
            ps.setString(4, city);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Hotel added successfully!");
            nameField.setText(""); addressField.setText(""); phoneField.setText(""); cityField.setText("");
            loadHotels();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadHotels() {
        hotelListModel.clear();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM hotels";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            hotelIds = new int[100]; // supports up to 100 entries
            int i = 0;

            while (rs.next()) {
                hotelIds[i] = rs.getInt("id");
                hotelListModel.addElement(rs.getString("name") + " - " + rs.getString("city"));
                i++;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Load error: " + ex.getMessage());
        }
    }

    private void deleteHotel() {
        int selectedIndex = hotelList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a hotel to delete.");
            return;
        }

        int hotelId = hotelIds[selectedIndex];

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM hotels WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, hotelId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Hotel deleted.");
            loadHotels();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Delete error: " + ex.getMessage());
        }
    }
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelsAdmin());
    }
}

