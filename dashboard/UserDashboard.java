package dashboard;

import modules.*;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    public UserDashboard(String username) {
        setTitle("User Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(0, 153, 76));

        JPanel btnPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        String[] modules = { "Hotels", "Education", "Hostels", "ATMs", "Restaurants", "Hospitals" };
        for (String name : modules) {
            JButton button = new JButton("View " + name);
            button.addActionListener(e -> openModule(name));
            btnPanel.add(button);
        }

        add(welcomeLabel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void openModule(String name) {
        switch (name.toLowerCase()) {
            case "hotels": new HotelsViewer(); break;
            case "education": new EducationViewer(); break;
            case "hostels": new HostelsViewer(); break;
            case "atms": new ATMsViewer(); break;
            case "restaurants": new RestaurantsViewer(); break;
            case "hospitals": new HospitalsViewer(); break;
            default: JOptionPane.showMessageDialog(this, "Module not found!");
        }
    }
}
