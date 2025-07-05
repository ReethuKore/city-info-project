package modules;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ATMsViewer extends JFrame {
    private DefaultListModel<String> atmListModel;
    private JList<String> atmList;

    // Default constructor - show all ATMs
    public ATMsViewer() {
        this("All");
    }

    // Constructor to filter by city
    public ATMsViewer(String city) {
        setTitle("Available ATMs" + (city.equals("All") ? "" : " in " + city));
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("ATM Locations" + (city.equals("All") ? "" : " - " + city), SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        atmListModel = new DefaultListModel<>();
        atmList = new JList<>(atmListModel);
        atmList.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(atmList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("List of ATMs"));
        add(scrollPane, BorderLayout.CENTER);

        loadATMs(city);
        setVisible(true);
    }

    private void loadATMs(String city) {
        atmListModel.clear();

        try (Connection conn = DBConnection.getConnection()) {
            String query;
            PreparedStatement stmt;

            if (city.equals("All")) {
                query = "SELECT bank_name, location, city FROM atms";
                stmt = conn.prepareStatement(query);
            } else {
                query = "SELECT bank_name, location, city FROM atms WHERE city = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, city);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String atm = String.format("Bank: %s | City: %s\n  Location: %s\n",
                        rs.getString("bank_name"),
                        rs.getString("city"),
                        rs.getString("location"));
                atmListModel.addElement(atm);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading ATMs: " + e.getMessage());
        }
    }

    // Test main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMsViewer("Hyderabad"));
    }
}
