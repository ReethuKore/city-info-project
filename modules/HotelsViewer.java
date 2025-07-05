package modules;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class HotelsViewer extends JFrame {
    private DefaultListModel<String> hotelListModel;
    private JList<String> hotelList;

    // Constructor for viewing all hotels
    public HotelsViewer() {
        this("All");
    }

    // Constructor for viewing hotels in a specific city
    public HotelsViewer(String city) {
        setTitle("Available Hotels" + (city.equals("All") ? "" : " in " + city));
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Hotel Information" + (city.equals("All") ? "" : " - " + city), SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        hotelListModel = new DefaultListModel<>();
        hotelList = new JList<>(hotelListModel);
        hotelList.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(hotelList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("List of Hotels"));
        add(scrollPane, BorderLayout.CENTER);

        loadHotels(city);
        setVisible(true);
    }

    private void loadHotels(String city) {
        hotelListModel.clear();

        try (Connection conn = DBConnection.getConnection()) {
            String query;
            PreparedStatement stmt;

            if (city.equals("All")) {
                query = "SELECT name, address, phone, city FROM hotels";
                stmt = conn.prepareStatement(query);
            } else {
                query = "SELECT name, address, phone, city FROM hotels WHERE city = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, city);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String hotel = String.format("Name: %s | City: %s\n  Address: %s\n  Phone: %s\n",
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("address"),
                        rs.getString("phone"));
                hotelListModel.addElement(hotel);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading hotels: " + e.getMessage());
        }
    }

    // Test main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelsViewer("Hyderabad"));
    }
}
