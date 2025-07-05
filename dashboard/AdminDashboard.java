package dashboard;

import modules.*;

import javax.swing.*;
import java.awt.*;


public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel header = new JLabel("Welcome Admin!", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(new Color(0, 102, 204));

        JPanel btnPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        String[] modules = { "Hotels", "Education", "Hostels", "ATMs", "Restaurants", "Hospitals" };
        for (String name : modules) {
            JButton button = new JButton("Manage " + name);
            button.addActionListener(e -> openModule(name));
            btnPanel.add(button);
        }

        add(header, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void openModule(String name) {
        switch (name.toLowerCase()) {
            case "hotels": new HotelsAdmin(); break;
            case "education": new EducationAdmin(); break;
            case "hostels": new HostelsAdmin(); break;
            case "atms": new ATMsAdmin(); break;
            case "restaurants": new RestaurantsAdmin(); break;
            case "hospitals": new HospitalsAdmin(); break;
            default: JOptionPane.showMessageDialog(this, "Module not found!");
        }
    }
}
