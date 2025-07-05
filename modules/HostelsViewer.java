package modules;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HostelsViewer extends JFrame {
    JTable table;
    DefaultTableModel model;

    public HostelsViewer() {
        setTitle("View Hostels");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Address", "Contact", "City"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        loadHostels();

        setVisible(true);
    }

    private void loadHostels() {
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading hostels: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new HostelsViewer();
    }
}
