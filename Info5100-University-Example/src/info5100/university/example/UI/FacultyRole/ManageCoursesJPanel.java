package info5100.university.example.UI.FacultyRole;

import info5100.university.example.UniversityBusiness;
import info5100.university.example.Persona.UserAccount;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseCatalog.Course;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Manage courses panel for faculty
 */
public class ManageCoursesJPanel extends JPanel {
    
    UniversityBusiness business;
    UserAccount userAccount;
    JPanel cardPanel;
    JTable coursesTable;
    DefaultTableModel tableModel;
    
    public ManageCoursesJPanel(UniversityBusiness business, UserAccount userAccount, JPanel cardPanel) {
        this.business = business;
        this.userAccount = userAccount;
        this.cardPanel = cardPanel;
        
        initComponents();
        loadCourses();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Top panel with title and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("My Courses");
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
        
        // Table
        String[] columnNames = {"Course Number", "Course Title", "Credits", "Seats Generated", "Rating"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        coursesTable = new JTable(tableModel);
        coursesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesTable.setRowHeight(30);
        coursesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        coursesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        coursesTable.getTableHeader().setBackground(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(coursesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton viewDetailsBtn = createButton("View Details");
        viewDetailsBtn.addActionListener(e -> viewCourseDetails());
        
        JButton updateRatingBtn = createButton("Update Rating");
        updateRatingBtn.addActionListener(e -> updateRating());
        
        JButton uploadSyllabusBtn = createButton("Upload Syllabus");
        uploadSyllabusBtn.setBackground(new Color(108, 117, 125));
        uploadSyllabusBtn.addActionListener(e -> uploadSyllabus());
        
        JButton toggleEnrollmentBtn = createButton("Toggle Enrollment");
        toggleEnrollmentBtn.setBackground(new Color(255, 193, 7));
        toggleEnrollmentBtn.setForeground(Color.BLACK);
        toggleEnrollmentBtn.addActionListener(e -> toggleEnrollment());
        
        JButton refreshBtn = createButton("Refresh");
        refreshBtn.addActionListener(e -> loadCourses());
        
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.add(updateRatingBtn);
        buttonPanel.add(uploadSyllabusBtn);
        buttonPanel.add(toggleEnrollmentBtn);
        buttonPanel.add(refreshBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(40, 167, 69));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }
    
    private void loadCourses() {
        tableModel.setRowCount(0);
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        if (faculty == null) {
            JOptionPane.showMessageDialog(this, "Faculty profile not found");
            return;
        }
        
        ArrayList<FacultyAssignment> assignments = faculty.getFacultyAssignments();
        
        if (assignments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No courses assigned to you yet");
            return;
        }
        
        for (FacultyAssignment fa : assignments) {
            CourseOffer offer = fa.getCourseOffer();
            Course course = offer.getSubjectCourse();
            
            Object[] row = {
                course.getCourseNumber(),
                course.getTitle(),
                course.getCredits(),
                offer.getSeatList().size(),
                String.format("%.2f", fa.getRating())
            };
            tableModel.addRow(row);
        }
    }
    
    private void viewCourseDetails() {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course first");
            return;
        }
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment selectedAssignment = faculty.getFacultyAssignments().get(selectedRow);
        
        // Remove any existing courseDetail panel first to avoid duplicates
        Component[] components = cardPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof CourseDetailJPanel) {
                cardPanel.remove(comp);
            }
        }
        
        // Create and add the new detail panel
        CourseDetailJPanel detailPanel = new CourseDetailJPanel(business, userAccount, selectedAssignment, cardPanel);
        cardPanel.add(detailPanel, "courseDetail");
        cardPanel.revalidate();
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "courseDetail");
    }
    
    private void updateRating() {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course first");
            return;
        }
        
        String ratingStr = JOptionPane.showInputDialog(this, "Enter course rating (0.0 - 5.0):");
        if (ratingStr == null) return;
        
        try {
            double rating = Double.parseDouble(ratingStr);
            if (rating < 0.0 || rating > 5.0) {
                JOptionPane.showMessageDialog(this, "Rating must be between 0.0 and 5.0");
                return;
            }
            
            FacultyProfile faculty = userAccount.getFacultyProfile();
            FacultyAssignment selectedAssignment = faculty.getFacultyAssignments().get(selectedRow);
            selectedAssignment.seProfRating(rating);
            
            JOptionPane.showMessageDialog(this, "Rating updated successfully!");
            loadCourses();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid rating value");
        }
    }
    
    private void uploadSyllabus() {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course first");
            return;
        }
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment selectedAssignment = faculty.getFacultyAssignments().get(selectedRow);
        CourseOffer courseOffer = selectedAssignment.getCourseOffer();
        
        // Use JFileChooser to select a file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Syllabus File");
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            courseOffer.setSyllabusPath(filePath);
            
            JOptionPane.showMessageDialog(this, 
                "Syllabus uploaded successfully!\nPath: " + filePath, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void toggleEnrollment() {
        int selectedRow = coursesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course first");
            return;
        }
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment selectedAssignment = faculty.getFacultyAssignments().get(selectedRow);
        CourseOffer courseOffer = selectedAssignment.getCourseOffer();
        Course course = courseOffer.getSubjectCourse();
        
        boolean currentStatus = courseOffer.isEnrollmentOpen();
        String statusMsg = currentStatus ? "OPEN" : "CLOSED";
        String newStatusMsg = currentStatus ? "CLOSE" : "OPEN";
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Course: " + course.getCourseNumber() + " - " + course.getTitle() + "\n" +
            "Current enrollment status: " + statusMsg + "\n\n" +
            "Do you want to " + newStatusMsg + " enrollment?", 
            "Toggle Enrollment", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            courseOffer.setEnrollmentOpen(!currentStatus);
            
            JOptionPane.showMessageDialog(this, 
                "Enrollment has been " + (!currentStatus ? "OPENED" : "CLOSED") + " for " + course.getCourseNumber(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void goBack() {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "mainMenu");
    }
}

