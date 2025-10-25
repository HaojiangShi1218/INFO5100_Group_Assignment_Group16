package info5100.university.example.UI;

import info5100.university.example.UniversityBusiness;
import info5100.university.example.ConfigureUniversity;
import info5100.university.example.Persona.UserAccount;
import info5100.university.example.UI.FacultyRole.FacultyWorkAreaJPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Main application frame
 */
public class MainFrame extends JFrame {
    
    private UniversityBusiness business;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    public MainFrame() {
        // Initialize business
        business = ConfigureUniversity.configure();
        
        // Setup frame
        setTitle(business.getUniversityName() + " - Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Create card layout for switching panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Add login panel
        LoginPanel loginPanel = new LoginPanel();
        cardPanel.add(loginPanel, "login");
        
        add(cardPanel);
        
        // Show login panel
        cardLayout.show(cardPanel, "login");
    }
    
    /**
     * Inner class for login panel
     */
    class LoginPanel extends JPanel {
        private JTextField usernameField;
        private JPasswordField passwordField;
        
        public LoginPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            
            // Title
            JLabel titleLabel = new JLabel(business.getUniversityName());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            add(titleLabel, gbc);
            
            // Subtitle
            JLabel subtitleLabel = new JLabel("Management System Login");
            subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            gbc.gridy = 1;
            add(subtitleLabel, gbc);
            
            // Username
            gbc.gridwidth = 1;
            gbc.gridy = 2;
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(new JLabel("Username:"), gbc);
            
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            usernameField = new JTextField(20);
            add(usernameField, gbc);
            
            // Password
            gbc.gridy = 3;
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(new JLabel("Password:"), gbc);
            
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            passwordField = new JPasswordField(20);
            add(passwordField, gbc);
            
            // Login button
            gbc.gridy = 4;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JButton loginButton = new JButton("Login");
            loginButton.setPreferredSize(new Dimension(200, 40));
            loginButton.setBackground(new Color(0, 123, 255));
            loginButton.setForeground(Color.BLACK);
            loginButton.setFocusPainted(false);
            loginButton.addActionListener(e -> performLogin());
            add(loginButton, gbc);
            
            // Info label
            gbc.gridy = 5;
            JLabel infoLabel = new JLabel("<html><center>Test Accounts:<br>prof1/password (Faculty)<br>student1/password (Student)</center></html>");
            infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            infoLabel.setForeground(Color.GRAY);
            add(infoLabel, gbc);
            
            // Enter key to login
            passwordField.addActionListener(e -> performLogin());
        }
        
        private void performLogin() {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            UserAccount account = business.getUserAccountDirectory().authenticateUser(username, password);
            
            if (account == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Login successful, route to appropriate work area
            String role = account.getRole();
            
            if ("FACULTY".equals(role)) {
                showFacultyWorkArea(account);
            } else if ("STUDENT".equals(role)) {
                JOptionPane.showMessageDialog(this, "Student work area not implemented yet", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Unknown role: " + role, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showFacultyWorkArea(UserAccount account) {
        FacultyWorkAreaJPanel workArea = new FacultyWorkAreaJPanel(business, account, cardPanel);
        cardPanel.add(workArea, "faculty");
        cardLayout.show(cardPanel, "faculty");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

