/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.seatingarrangementapp;

/**
 *
 * @author nishana
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SeatingArrangementApp implements ActionListener {
    private static Map<String, String> userDatabase = new HashMap<>();
    private static Map<String, String> seatingArrangementDatabase = new HashMap<>();

    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;
    private JButton loginButton;
    private JButton createButton;
    private JButton arrangeSeatsButton;
    private JButton logoutButton;
    private JFrame adminFrame;
    private JFrame studentFrame;


    

    private JTextArea seatingArrangementTextArea;

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SeatingArrangementApp app = new SeatingArrangementApp();
            app.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Seating Arrangement Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("User Type:"));
        userTypeComboBox = new JComboBox<>(new String[]{"Admin", "Student"});
        panel.add(userTypeComboBox);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        createButton = new JButton("Create User");
        createButton.addActionListener(this);
        panel.add(createButton);

        

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

private void showAdminPage() {
    adminFrame = new JFrame("Seating Arrangement App - Admin Page");
    adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    adminFrame.setLayout(new BorderLayout());

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout());

    JLabel welcomeLabel = new JLabel("Welcome, Admin!");
    topPanel.add(welcomeLabel);

    logoutButton = new JButton("Logout");
    logoutButton.addActionListener(this);
    topPanel.add(logoutButton);

    adminFrame.add(topPanel, BorderLayout.NORTH);

    JTextArea seatingArrangementTextArea = new JTextArea();
    seatingArrangementTextArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(seatingArrangementTextArea);
    adminFrame.add(scrollPane, BorderLayout.CENTER);

    // Populate seating arrangement text area with student info
    StringBuilder seatingArrangementText = new StringBuilder();
    for (Map.Entry<String, String> entry : seatingArrangementDatabase.entrySet()) {
        String studentName = entry.getKey();
        String seatingArrangement = entry.getValue();
        seatingArrangementText.append(studentName).append(": ");
        seatingArrangementText.append(seatingArrangement).append("\n");
    }
    seatingArrangementTextArea.setText(seatingArrangementText.toString());

    arrangeSeatsButton = new JButton("Arrange Seats");
    arrangeSeatsButton.addActionListener(this);
    adminFrame.add(arrangeSeatsButton, BorderLayout.SOUTH);

    adminFrame.pack();
    adminFrame.setLocationRelativeTo(null);
    adminFrame.setVisible(true);
    frame.setVisible(false);
}


    private void showStudentPage(String studentUsername) {
        studentFrame = new JFrame("Seating Arrangement App - Student Page");
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + studentUsername + "!");
        topPanel.add(welcomeLabel);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        topPanel.add(logoutButton);

        studentFrame.add(topPanel, BorderLayout.NORTH);

        seatingArrangementTextArea = new JTextArea();
        seatingArrangementTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(seatingArrangementTextArea);
        studentFrame.add(scrollPane, BorderLayout.CENTER);

        studentFrame.pack();
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setVisible(true);
        frame.setVisible(false);
        showSeatingArrangement(studentUsername);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            login();
        } else if (e.getSource() == createButton) {
            createUser();
        } else if (e.getSource() == arrangeSeatsButton) {
            arrangeSeats();
        } else if (e.getSource()== logoutButton){
            logout();
        }
    }
    
 
    private void showSeatingArrangement(String studentUsername) {
        String seatingArrangement = seatingArrangementDatabase.get(studentUsername);
        if (seatingArrangement != null) {
            JOptionPane.showMessageDialog(frame, "Seating Arrangement for " + studentUsername + ":\n" + seatingArrangement);
        } else {
            JOptionPane.showMessageDialog(frame, "No seating arrangement found for " + studentUsername);
        }
    }

    private void arrangeSeats() {
        String studentUsername = JOptionPane.showInputDialog(frame, "Enter student username:");

        if (studentUsername != null  && !studentUsername.isEmpty()) {
            String seatingArrangement = JOptionPane.showInputDialog(frame, "Enter seating arrangement for " + studentUsername + ":");

            if (seatingArrangement != null && !seatingArrangement.isEmpty()) {
                seatingArrangementDatabase.put(studentUsername, seatingArrangement);
                JOptionPane.showMessageDialog(frame, "Seating arrangement successfully arranged for " + studentUsername);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid seating arrangement. Please try again.");
            }
        }
    }
    
    
       
//login 
private void login() {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());
    String userType = (String) userTypeComboBox.getSelectedItem();

    if (userDatabase.containsKey(username)) {
        String storedPassword = userDatabase.get(username);
        String storedUserType = userDatabase.get(username + "_Type");

        if (storedPassword.equals(password) && storedUserType != null && storedUserType.equals(userType)) {
            JOptionPane.showMessageDialog(frame, "Login successful!");

            if (userType.equals("Admin")) {
                // Admin logged in
                JOptionPane.showMessageDialog(frame, "Welcome, Admin!");
                showAdminPage();
            } else if (userType.equals("Student")) {
                // Student logged in
                JOptionPane.showMessageDialog(frame, "Welcome, " + username + "!");
                showStudentPage(username); // Pass the username parameter
            }
            return;
        }
    }

    JOptionPane.showMessageDialog(frame, "Invalid username, password, or user type. Please try again.");
}
//create user 
private void createUser() {
    String username = JOptionPane.showInputDialog(frame, "Enter new username:");

    if (username == null || username.isEmpty()) {
        return; // User canceled or entered an empty username
    }

    if (userDatabase.containsKey(username)) {
        JOptionPane.showMessageDialog(frame, "Username already exists. Please choose a different username.");
        return;
    }

    JPasswordField passwordField = new JPasswordField();
    Object[] fields = {"Enter password:", passwordField};
    int option = JOptionPane.showConfirmDialog(frame, fields, "Create User", JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        String password = new String(passwordField.getPassword());

        String[] options = {"Admin", "Student"};
        String userType = (String) JOptionPane.showInputDialog(frame, "Select User Type:", "Create User",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        createUser(username, password, userType);
        JOptionPane.showMessageDialog(frame, "User created successfully!");
    }
}
//create user in database
private void createUser(String username, String password, String userType) {
    userDatabase.put(username, password);
    userDatabase.put(username + "_Type", userType);
}


    
    private void logout() {
        
         if (adminFrame != null) {
        adminFrame.setVisible(false);
    }
    if (studentFrame != null) {
        studentFrame.setVisible(false);
    }
        createAndShowGUI();
        
    }
}
