package modules;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EducationViewer extends JFrame {
    private DefaultListModel<String> educationListModel;
    private JList<String> educationList;

    // Default constructor – shows all education entries
    public EducationViewer() {
        this("All");
    }

    // City-specific constructor – filters by city
    public EducationViewer(String city) {
        setTitle("Educational Institutions" + (city.equals("All") ? "" : " in " + city));
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Education Info" + (city.equals("All") ? "" : " - " + city), SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(heading, BorderLayout.NORTH);

        educationListModel = new DefaultListModel<>();
        educationList = new JList<>(educationListModel);
        educationList.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(educationList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("List of Institutions"));
        add(scrollPane, BorderLayout.CENTER);

        loadEducation(city);
        setVisible(true);
    }

    private void loadEducation(String city) {
        educationListModel.clear();

        try (Connection conn = DBConnection.getConnection()) {
            String query;
            PreparedStatement stmt;

            if (city.equals("All")) {
                query = "SELECT name, type, address, city FROM education";
                stmt = conn.prepareStatement(query);
            } else {
                query = "SELECT name, type, address, city FROM education WHERE city = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, city);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String edu = String.format("Name: %s | Type: %s | City: %s\n  Address: %s\n",
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("city"),
                        rs.getString("address"));
                educationListModel.addElement(edu);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading education data: " + e.getMessage());
        }
    }

    // Test main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EducationViewer("Hyderabad"));
    }
}
