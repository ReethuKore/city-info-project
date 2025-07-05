package dashboard;

import modules.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserDashboard extends JFrame {
    private String username;
    private JComboBox<String> cityCombo;

    public UserDashboard(String username) {
        this.username = username;
        setTitle("User Dashboard - " + username);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        JLabel title = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        // City Selection
        JLabel cityLabel = new JLabel("Select City:");
        String[] cities = {"Hyderabad", "Mumbai", "Delhi", "Bangalore", "Chennai"};
        cityCombo = new JComboBox<>(cities);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(cityLabel);
        topPanel.add(cityCombo);

        // Module Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        JButton btnHotels = new JButton("View Hotels");
        JButton btnHospitals = new JButton("View Hospitals");
        JButton btnHostels = new JButton("View Hostels");
        JButton btnEducation = new JButton("View Education");
        JButton btnRestaurants = new JButton("View Restaurants");
        JButton btnATMs = new JButton("View ATMs");

        // Add buttons
        buttonPanel.add(btnHotels);
        buttonPanel.add(btnHospitals);
        buttonPanel.add(btnHostels);
        buttonPanel.add(btnEducation);
        buttonPanel.add(btnRestaurants);
        buttonPanel.add(btnATMs);

        // Action Listeners
        btnHotels.addActionListener(e -> new HotelsViewer(getSelectedCity()));
        btnHospitals.addActionListener(e -> new HospitalsViewer(getSelectedCity()));
        btnHostels.addActionListener(e -> new HostelsViewer(getSelectedCity()));
        btnEducation.addActionListener(e -> new EducationViewer(getSelectedCity()));
        btnRestaurants.addActionListener(e -> new RestaurantsViewer(getSelectedCity()));
        btnATMs.addActionListener(e -> new ATMsViewer(getSelectedCity()));

        // Main Layout
        setLayout(new BorderLayout(10, 10));
        add(title, BorderLayout.NORTH);
        add(topPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String getSelectedCity() {
        return (String) cityCombo.getSelectedItem();
    }

    // Optional: For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserDashboard("TestUser"));
    }
}
