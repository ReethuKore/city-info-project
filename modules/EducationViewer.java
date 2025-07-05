package modules;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EducationViewer extends JFrame {
    private DefaultListModel<String> eduListModel;
    private JList<String> eduList;

    public EducationViewer() {
        setTitle("Educational Institutions");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Education Information", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        eduListModel = new DefaultListModel<>();
        eduList = new JList<>(eduListModel);
        eduList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(eduList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Institutions"));
        add(scrollPane, BorderLayout.CENTER);

        loadEducationData();

        setVisible(true);
    }

    private void loadEducationData() {
        eduListModel.clear();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT name, type, address, city FROM education";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String info = String.format(
                        "Name: %s | Type: %s | City: %s\n  Address: %s\n",
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("city"),
                        rs.getString("address")
                );
                eduListModel.addElement(info);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EducationViewer());
    }
}
