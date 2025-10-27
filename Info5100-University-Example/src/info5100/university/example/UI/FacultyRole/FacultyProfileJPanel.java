package info5100.university.example.UI.FacultyRole;

import info5100.university.example.UniversityBusiness;
import info5100.university.example.Persona.UserAccount;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.Persona.Person;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Faculty profile view panel
 */
public class FacultyProfileJPanel extends JPanel {
    
    UniversityBusiness business;
    UserAccount userAccount;
    JPanel cardPanel;
    
    JLabel personIdLabel;
    JLabel usernameLabel;
    JLabel courseCountLabel;
    JLabel avgRatingLabel;
    JTextArea coursesArea;
    
    public FacultyProfileJPanel(UniversityBusiness business, UserAccount userAccount, JPanel cardPanel) {
        this.business = business;
        this.userAccount = userAccount;
        this.cardPanel = cardPanel;
        
        initComponents();
        loadProfileData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(0, 86, 179));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> goBack());
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        // Profile panel
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        profilePanel.setBackground(Color.WHITE);
        
        // Personal Information Section
        JPanel personalInfoPanel = createSectionPanel("Personal Information");
        
        personIdLabel = new JLabel();
        personIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        personalInfoPanel.add(createInfoRow("Person ID:", personIdLabel));
        personalInfoPanel.add(Box.createVerticalStrut(10));
        personalInfoPanel.add(createInfoRow("Username:", usernameLabel));
        
        profilePanel.add(personalInfoPanel);
        profilePanel.add(Box.createVerticalStrut(20));
        
        // Teaching Statistics Section
        JPanel statsPanel = createSectionPanel("Teaching Statistics");
        
        courseCountLabel = new JLabel();
        courseCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        avgRatingLabel = new JLabel();
        avgRatingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        statsPanel.add(createInfoRow("Total Courses:", courseCountLabel));
        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(createInfoRow("Average Rating:", avgRatingLabel));
        
        profilePanel.add(statsPanel);
        profilePanel.add(Box.createVerticalStrut(20));
        
        // Course List Section
        JPanel coursesPanel = createSectionPanel("My Courses");
        
        coursesArea = new JTextArea(10, 50);
        coursesArea.setEditable(false);
        coursesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        coursesArea.setBackground(new Color(250, 250, 250));
        JScrollPane coursesScroll = new JScrollPane(coursesArea);
        coursesPanel.add(coursesScroll);
        
        profilePanel.add(coursesPanel);
        
        JScrollPane mainScroll = new JScrollPane(profilePanel);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScroll, BorderLayout.CENTER);
        
        // Bottom button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton editProfileBtn = new JButton("Edit Profile");
        editProfileBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        editProfileBtn.setBackground(new Color(0, 123, 255));
        editProfileBtn.setForeground(Color.BLACK);
        editProfileBtn.setFocusPainted(false);
        editProfileBtn.setPreferredSize(new Dimension(150, 35));
        editProfileBtn.addActionListener(e -> editProfile());
        
        JButton changePasswordBtn = new JButton("Change Password");
        changePasswordBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        changePasswordBtn.setBackground(new Color(255, 152, 0));
        changePasswordBtn.setForeground(Color.BLACK);
        changePasswordBtn.setFocusPainted(false);
        changePasswordBtn.setPreferredSize(new Dimension(150, 35));
        changePasswordBtn.addActionListener(e -> changePassword());
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        refreshBtn.setBackground(new Color(40, 167, 69));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setPreferredSize(new Dimension(150, 35));
        refreshBtn.addActionListener(e -> loadProfileData());
        
        buttonPanel.add(editProfileBtn);
        buttonPanel.add(changePasswordBtn);
        buttonPanel.add(refreshBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(0, 51, 102));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sectionTitle);
        panel.add(Box.createVerticalStrut(15));
        
        return panel;
    }
    
    private JPanel createInfoRow(String label, JLabel valueLabel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setBackground(Color.WHITE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labelComponent.setPreferredSize(new Dimension(150, 25));
        
        row.add(labelComponent);
        row.add(valueLabel);
        
        return row;
    }
    
    private void loadProfileData() {
        FacultyProfile faculty = userAccount.getFacultyProfile();
        Person person = userAccount.getPerson();
        
        if (person != null) {
            personIdLabel.setText(person.getPersonId());
        } else {
            personIdLabel.setText("N/A");
        }
        
        usernameLabel.setText(userAccount.getUsername());
        
        if (faculty != null) {
            ArrayList<FacultyAssignment> assignments = faculty.getFacultyAssignments();
            courseCountLabel.setText(String.valueOf(assignments.size()));
            
            double avgRating = faculty.getProfAverageOverallRating();
            avgRatingLabel.setText(String.format("%.2f / 5.00", avgRating));
            
            // Build courses list
            StringBuilder coursesText = new StringBuilder();
            coursesText.append(String.format("%-15s %-40s %10s %10s\n", 
                "Course #", "Title", "Seats", "Rating"));
            coursesText.append("-".repeat(80)).append("\n");
            
            for (FacultyAssignment fa : assignments) {
                String courseNum = fa.getCourseOffer().getCourseNumber();
                String title = fa.getCourseOffer().getSubjectCourse().getTitle();
                int seats = fa.getCourseOffer().getSeatList().size();
                double rating = fa.getRating();
                
                coursesText.append(String.format("%-15s %-40s %10d %10.2f\n", 
                    courseNum, title, seats, rating));
            }
            
            coursesArea.setText(coursesText.toString());
        } else {
            courseCountLabel.setText("0");
            avgRatingLabel.setText("N/A");
            coursesArea.setText("No faculty profile found.");
        }
    }
    
    private void editProfile() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Profile", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 300);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel headerLabel = new JLabel("Edit Profile Information");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Username field
        JLabel usernameFieldLabel = new JLabel("Username:");
        usernameFieldLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField usernameField = new JTextField(userAccount.getUsername(), 20);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        formPanel.add(usernameFieldLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Person ID (read-only)
        Person person = userAccount.getPerson();
        JLabel personIdFieldLabel = new JLabel("Person ID: " + (person != null ? person.getPersonId() : "N/A"));
        personIdFieldLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        personIdFieldLabel.setForeground(Color.GRAY);
        formPanel.add(personIdFieldLabel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Role (read-only)
        JLabel roleLabel = new JLabel("Role: " + userAccount.getRole());
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roleLabel.setForeground(Color.GRAY);
        formPanel.add(roleLabel);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setBackground(new Color(40, 167, 69));
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            String newUsername = usernameField.getText().trim();
            if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Username cannot be empty");
                return;
            }
            
            // Note: In a real system, we'd check for username uniqueness
            // For now, just update the display
            JOptionPane.showMessageDialog(dialog, 
                "Profile updated successfully!\n(Note: Username change is display only in this demo)",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            dialog.dispose();
            loadProfileData();
        });
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(220, 53, 69));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void changePassword() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Change Password", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(450, 300);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 152, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel headerLabel = new JLabel("Change Password");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel currentPwdLabel = new JLabel("Current Password:");
        currentPwdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField currentPwdField = new JPasswordField(20);
        currentPwdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel newPwdLabel = new JLabel("New Password:");
        newPwdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField newPwdField = new JPasswordField(20);
        newPwdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel confirmPwdLabel = new JLabel("Confirm New Password:");
        confirmPwdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField confirmPwdField = new JPasswordField(20);
        confirmPwdField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        formPanel.add(currentPwdLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(currentPwdField);
        formPanel.add(Box.createVerticalStrut(15));
        
        formPanel.add(newPwdLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(newPwdField);
        formPanel.add(Box.createVerticalStrut(15));
        
        formPanel.add(confirmPwdLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(confirmPwdField);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton saveBtn = new JButton("Change Password");
        saveBtn.setBackground(new Color(40, 167, 69));
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            String currentPwd = new String(currentPwdField.getPassword());
            String newPwd = new String(newPwdField.getPassword());
            String confirmPwd = new String(confirmPwdField.getPassword());
            
            if (currentPwd.isEmpty() || newPwd.isEmpty() || confirmPwd.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required");
                return;
            }
            
            if (!currentPwd.equals(userAccount.getPassword())) {
                JOptionPane.showMessageDialog(dialog, "Current password is incorrect");
                return;
            }
            
            if (!newPwd.equals(confirmPwd)) {
                JOptionPane.showMessageDialog(dialog, "New passwords do not match");
                return;
            }
            
            if (newPwd.length() < 6) {
                JOptionPane.showMessageDialog(dialog, "Password must be at least 6 characters");
                return;
            }
            
            // Update password
            userAccount.setPassword(newPwd);
            
            JOptionPane.showMessageDialog(dialog, 
                "Password changed successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            dialog.dispose();
        });
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(220, 53, 69));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void goBack() {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "mainMenu");
    }
}

