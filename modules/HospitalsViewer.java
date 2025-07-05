package modules;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class HospitalsViewer extends JFrame {
    private DefaultListModel<String> hospitalListModel;
    private JList<String> hospitalList;

    // Default constructor (shows all hospitals)
    public HospitalsViewer() {
        this("All");
    }

    // Constructor for a specific city
    public HospitalsViewer(String city) {
        setTitle("Available Hospitals" + (city.equals("All") ? "" : " in " + city));
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Hospital Information" + (city.equals("All") ? "" : " - " + city), SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        hospitalListModel = new DefaultListModel<>();
        hospitalList = new JList<>(hospitalListModel);
        hospitalList.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(hospitalList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("List of Hospitals"));
        add(scrollPane, BorderLayout.CENTER);

        loadHospitals(city);
        setVisible(true);
    }

    private void loadHospitals(String city) {
        hospitalListModel.clear();

        try (Connection conn = DBConnection.getConnection()) {
            String query;
            PreparedStatement stmt;

            if (city.equals("All")) {
                query = "SELECT name, address, contact, city FROM hospitals";
                stmt = conn.prepareStatement(query);
            } else {
                query = "SELECT name, address, contact, city FROM hospitals WHERE city = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, city);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String hospital = String.format("Name: %s | City: %s\n  Address: %s\n  Contact: %s\n",
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("address"),
                        rs.getString("contact"));
                hospitalListModel.addElement(hospital);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading hospitals: " + e.getMessage());
        }
    }

    // Optional test main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HospitalsViewer("Hyderabad"));
    }
}
