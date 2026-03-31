package com.dlms.view;

import com.dlms.dao.UserDAO;
import com.dlms.model.User;
import com.dlms.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;     // ← Add this line
import java.sql.Connection;
public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;

    public LoginFrame() {
        setTitle("DLMS - Login");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("Digital Learning Management System");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        btnLogin = new JButton("Login");
        panel.add(btnLogin, gbc);

        gbc.gridx = 1;
        btnExit = new JButton("Exit");
        panel.add(btnExit, gbc);

        add(panel);

        // Login Action
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password!");
                return;
            }

            System.out.println("Trying to login with: " + username + " / " + password);

            // Test DB Connection first
            try {
                Connection testConn = DBConnection.getConnection();
                System.out.println("✅ Database Connection Successful!");
                testConn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Connection Failed!\n" + ex.getMessage());
                return;
            }

            UserDAO userDAO = new UserDAO();
            User loggedUser = userDAO.login(username, password);

            if (loggedUser != null) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + loggedUser.getFullName());
                dispose();
                new DashboardFrame(loggedUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                System.out.println("❌ Login returned null for user: " + username);
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }
}