package modules;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class HostelsViewer extends JFrame {
    private DefaultListModel<String> hostelListModel;
    private JList<String> hostelList;

    // Default constructor (shows all hostels)
    public HostelsViewer() {
        this("All");
    }

    // Constructor for viewing hostels in a specific city
    public HostelsViewer(String city) {
        setTitle("Available Hostels" + (city.equals("All") ? "" : " in " + city));
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Hostel Information" + (city.equals("All") ? "" : " - " + city), SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        hostelListModel = new DefaultListModel<>();
        hostelList = new JList<>(hostelListModel);
        hostelList.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(hostelList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("List of Hostels"));
        add(scrollPane, BorderLayout.CENTER);

        loadHostels(city);
        setVisible(true);
    }

    private void loadHostels(String city) {
        hostelListModel.clear();

        try (Connection conn = DBConnection.getConnection()) {
            String query;
            PreparedStatement stmt;

            if (city.equals("All")) {
                query = "SELECT name, address, contact, city FROM hostels";
                stmt = conn.prepareStatement(query);
            } else {
                query = "SELECT name, address, contact, city FROM hostels WHERE city = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, city);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String hostel = String.format("Name: %s | City: %s\n  Address: %s\n  Contact: %s\n",
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("address"),
                        rs.getString("contact"));
                hostelListModel.addElement(hostel);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading hostels: " + e.getMessage());
        }
    }

    // Test main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HostelsViewer("Hyderabad"));
    }
}
