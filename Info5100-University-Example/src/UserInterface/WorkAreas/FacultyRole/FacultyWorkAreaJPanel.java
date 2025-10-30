package UserInterface.WorkAreas.FacultyRole;
import info5100.university.example.College.College;
import info5100.university.example.Department.Department;

import info5100.university.example.Persona.UserAccount;
import javax.swing.*;
import java.awt.*;

/**
 * Faculty work area main panel
 */
public class FacultyWorkAreaJPanel extends JPanel {
    Department department;
    //UniversityBusiness business;
    UserAccount userAccount;
    JPanel cardPanel;
    
    public FacultyWorkAreaJPanel(Department department, UserAccount userAccount, JPanel cardPanel) {
        this.department = department;
        //this.business = business;
        this.userAccount = userAccount;
        this.cardPanel = cardPanel;
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 51, 102));
        titlePanel.setPreferredSize(new Dimension(0, 100));
        
        JLabel titleLabel = new JLabel("Faculty Work Area");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel usernameLabel = new JLabel("Welcome, " + userAccount.getUserLoginName());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(Box.createVerticalGlue());
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(usernameLabel);
        titlePanel.add(Box.createVerticalGlue());
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Button panel with grid layout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton manageCourseBtn = createStyledButton("Manage Courses", "View and manage your courses");
        manageCourseBtn.addActionListener(e -> openManageCourses());
        buttonPanel.add(manageCourseBtn, gbc);
        
        gbc.gridx = 1;
        JButton viewStudentsBtn = createStyledButton("View Students", "See students enrolled in your courses");
        viewStudentsBtn.addActionListener(e -> openViewStudents());
        buttonPanel.add(viewStudentsBtn, gbc);
        
        gbc.gridx = 2;
        JButton profileBtn = createStyledButton("My Profile", "View your faculty profile");
        profileBtn.addActionListener(e -> openMyProfile());
        buttonPanel.add(profileBtn, gbc);
        
        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton performanceReportBtn = createStyledButton("Performance Report", "Generate course performance reports");
        performanceReportBtn.setBackground(new Color(103, 58, 183));
        performanceReportBtn.addActionListener(e -> openPerformanceReport());
        buttonPanel.add(performanceReportBtn, gbc);
        
        gbc.gridx = 1;
        JButton tuitionInsightBtn = createStyledButton("Tuition Insights", "View tuition revenue statistics");
        tuitionInsightBtn.setBackground(new Color(0, 150, 136));
        tuitionInsightBtn.addActionListener(e -> openTuitionInsight());
        buttonPanel.add(tuitionInsightBtn, gbc);
        
        gbc.gridx = 2;
        JButton logoutBtn = createStyledButton("Logout", "Exit the system");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.addActionListener(e -> logout());
        buttonPanel.add(logoutBtn, gbc);
        
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton("<html><center><b>" + text + "</b><br><small>" + tooltip + "</small></center></html>");
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 86, 179), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        button.setPreferredSize(new Dimension(250, 120));
        button.setToolTipText(tooltip);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.getBackground().equals(new Color(0, 123, 255))) {
                    button.setBackground(new Color(0, 86, 179));
                } else if (button.getBackground().equals(new Color(220, 53, 69))) {
                    button.setBackground(new Color(180, 40, 55));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.getBackground().equals(new Color(0, 86, 179))) {
                    button.setBackground(new Color(0, 123, 255));
                } else if (button.getBackground().equals(new Color(180, 40, 55))) {
                    button.setBackground(new Color(220, 53, 69));
                }
            }
        });
        
        return button;
    }
    
    private void openManageCourses() {
        ManageCoursesJPanel panel = new ManageCoursesJPanel(department, userAccount, cardPanel);
        cardPanel.add(panel, "manageCourses");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "manageCourses");
    }
    
    private void openViewStudents() {
        ViewStudentsJPanel panel = new ViewStudentsJPanel(department, userAccount, cardPanel);
        cardPanel.add(panel, "viewStudents");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "viewStudents");
    }
    
    private void openMyProfile() {
        FacultyProfileJPanel panel = new FacultyProfileJPanel(department, userAccount, cardPanel);
        cardPanel.add(panel, "myProfile");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "myProfile");
    }
    
    private void openPerformanceReport() {
        CoursePerformanceReportJPanel panel = new CoursePerformanceReportJPanel(department, userAccount, cardPanel);
        cardPanel.add(panel, "performanceReport");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "performanceReport");
    }
    
    private void openTuitionInsight() {
        TuitionInsightJPanel panel = new TuitionInsightJPanel(department, userAccount, cardPanel);
        cardPanel.add(panel, "tuitionInsight");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "tuitionInsight");
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            ((CardLayout) cardPanel.getLayout()).show(cardPanel, "LoginScreen");
        }
    }
}

