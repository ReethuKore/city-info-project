package modules;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class HotelsViewer extends JFrame {
    private DefaultListModel<String> hotelListModel;
    private JList<String> hotelList;

    public HotelsViewer() {
        setTitle("Available Hotels");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel title = new JLabel("Hotel Information", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Hotel list area
        hotelListModel = new DefaultListModel<>();
        hotelList = new JList<>(hotelListModel);
        hotelList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(hotelList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("List of Hotels"));

        add(scrollPane, BorderLayout.CENTER);

        loadHotels();

        setVisible(true);
    }

    private void loadHotels() {
        hotelListModel.clear();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT name, address, phone, city FROM hotels";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String info = String.format(
                        "Name: %s | City: %s\n  Address: %s\n  Phone: %s\n",
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
                hotelListModel.addElement(info);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading hotels: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelsViewer());
    }
}
