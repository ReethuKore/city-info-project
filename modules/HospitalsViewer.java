package modules;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HospitalsViewer extends JFrame {
    JTable table;
    DefaultTableModel model;

    public HospitalsViewer() {
        setTitle("Hospitals in City");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Address", "Contact", "City"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        fetchHospitals();
        setVisible(true);
    }

    private void fetchHospitals() {
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

    public static void main(String[] args) {
        new HospitalsViewer();
    }
}
