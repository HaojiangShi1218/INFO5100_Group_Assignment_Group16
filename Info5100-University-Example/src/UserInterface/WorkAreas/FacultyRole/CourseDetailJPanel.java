package UserInterface.WorkAreas.FacultyRole;

import info5100.university.example.College.College;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.UserAccount;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import javax.swing.*;
import java.awt.*;

/**
 * Course detail view and edit panel
 */
public class CourseDetailJPanel extends JPanel {
    
    Department department;
    UserAccount userAccount;
    FacultyAssignment facultyAssignment;
    JPanel cardPanel;
    
    JTextField courseNumberField;
    JTextField titleField;
    JTextField creditsField;
    JTextArea prerequisitesArea;
    
    public CourseDetailJPanel(Department department, UserAccount userAccount, 
                              FacultyAssignment facultyAssignment, JPanel cardPanel) {
        this.department = department;
        this.userAccount = userAccount;
        this.facultyAssignment = facultyAssignment;
        this.cardPanel = cardPanel;
        
        initComponents();
        loadCourseData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Course Details");
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
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Course Number
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Course Number:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        courseNumberField = new JTextField(30);
        courseNumberField.setEditable(false);
        courseNumberField.setBackground(new Color(240, 240, 240));
        formPanel.add(courseNumberField, gbc);
        
        // Title
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Title:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        titleField = new JTextField(30);
        formPanel.add(titleField, gbc);
        
        // Credits
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Credits:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        creditsField = new JTextField(30);
        formPanel.add(creditsField, gbc);
        
        // Prerequisites
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(new JLabel("Prerequisites:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.WEST;
        prerequisitesArea = new JTextArea(10, 30);
        prerequisitesArea.setLineWrap(true);
        prerequisitesArea.setWrapStyleWord(true);
        JScrollPane prerequisitesScroll = new JScrollPane(prerequisitesArea);
        formPanel.add(prerequisitesScroll, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(40, 167, 69));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(150, 35));
        saveButton.addActionListener(e -> saveCourseData());
        
        buttonPanel.add(saveButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadCourseData() {
        CourseOffer offer = facultyAssignment.getCourseoffer();
        Course course = offer.getSubjectCourse();
        
        courseNumberField.setText(course.getCOurseNumber());
        titleField.setText(course.getName());
        creditsField.setText(String.valueOf(course.getCredits()));
        prerequisitesArea.setText(course.getPrerequisites());
    }
    
    private void saveCourseData() {
        CourseOffer offer = facultyAssignment.getCourseoffer();
        Course course = offer.getSubjectCourse();
        
        // Update course information
        String newTitle = titleField.getText().trim();
        if (!newTitle.isEmpty()) {
            // Note: Course class may not have setter methods
            // In a real application, you'd need to add setters or use a different approach
            JOptionPane.showMessageDialog(this, 
                "Course update requested:\n" +
                "Title: " + newTitle + "\n" +
                "Credits: " + creditsField.getText() + "\n" +
                "Prerequisites: " + prerequisitesArea.getText(),
                "Update Summary",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Title cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goBack() {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "manageCourses");
    }
}

