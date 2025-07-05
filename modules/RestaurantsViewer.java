package modules;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RestaurantsViewer extends JFrame {
    private DefaultListModel<String> restaurantListModel;
    private JList<String> restaurantList;

// Default constructor to show all cities
public RestaurantsViewer() {
    this("All");
}

    public RestaurantsViewer(String city) {
        setTitle("Available Restaurants in " + city);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Restaurant Information - " + city, SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        restaurantListModel = new DefaultListModel<>();
        restaurantList = new JList<>(restaurantListModel);
        restaurantList.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(restaurantList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("List of Restaurants"));
        add(scrollPane, BorderLayout.CENTER);

        loadRestaurants(city);
        setVisible(true);
    }

   private void loadRestaurants(String city) {
    restaurantListModel.clear();
    try (Connection conn = DBConnection.getConnection()) {
        String query;
        PreparedStatement stmt;
        if (city.equals("All")) {
            query = "SELECT name, address, contact, city FROM restaurants";
            stmt = conn.prepareStatement(query);
        } else {
            query = "SELECT name, address, contact, city FROM restaurants WHERE city = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, city);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String restaurant = String.format(
                "Name: %s | City: %s\n  Address: %s\n  Contact: %s\n",
                rs.getString("name"),
                rs.getString("city"),
                rs.getString("address"),
                rs.getString("contact")
            );
            restaurantListModel.addElement(restaurant);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading restaurants: " + e.getMessage());
    }
}


    // Test main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RestaurantsViewer("Hyderabad"));
    }
}
