// RestaurantsViewer.java
package modules;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RestaurantsViewer extends JFrame {
    JTable table;

    public RestaurantsViewer() {
        setTitle("Restaurants");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"ID", "Name", "Address", "Contact", "City"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM restaurants");
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact"),
                        rs.getString("city")
                };
                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching restaurants: " + ex.getMessage());
        }

        add(scrollPane);
        setVisible(true);
    }
      public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantsViewer::new);
    }
}
