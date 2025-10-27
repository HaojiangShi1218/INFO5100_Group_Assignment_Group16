package info5100.university.example.UI.FacultyRole;

import info5100.university.example.UniversityBusiness;
import info5100.university.example.Persona.UserAccount;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.CourseSchedule.*;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.StudentProfile;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * View and manage students panel for faculty
 */
public class ViewStudentsJPanel extends JPanel {
    
    UniversityBusiness business;
    UserAccount userAccount;
    JPanel cardPanel;
    JComboBox<String> courseComboBox;
    JTable studentsTable;
    DefaultTableModel tableModel;
    JLabel statsLabel;
    JButton viewTranscriptBtn;
    JButton gradeAssignmentBtn;
    JButton viewRankingsBtn;
    
    public ViewStudentsJPanel(UniversityBusiness business, UserAccount userAccount, JPanel cardPanel) {
        this.business = business;
        this.userAccount = userAccount;
        this.cardPanel = cardPanel;
        
        initComponents();
        loadCourses();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("View Students");
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
        
        // Center panel with course selector and stats
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // Course selector panel
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        selectorPanel.setBackground(Color.WHITE);
        selectorPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        courseComboBox = new JComboBox<>();
        courseComboBox.setPreferredSize(new Dimension(300, 30));
        courseComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        courseComboBox.addActionListener(e -> loadStudents());
        
        statsLabel = new JLabel("");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsLabel.setForeground(new Color(0, 102, 204));
        
        selectorPanel.add(courseLabel);
        selectorPanel.add(courseComboBox);
        selectorPanel.add(Box.createHorizontalStrut(20));
        selectorPanel.add(statsLabel);
        
        centerPanel.add(selectorPanel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"Student ID", "Name", "Percentage", "Letter Grade", "GPA", "Rank"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentsTable = new JTable(tableModel);
        studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentsTable.setRowHeight(30);
        studentsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        studentsTable.getTableHeader().setBackground(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        viewTranscriptBtn = createButton("View Transcript");
        viewTranscriptBtn.addActionListener(e -> viewStudentTranscript());
        
        gradeAssignmentBtn = createButton("Grade Assignments");
        gradeAssignmentBtn.setBackground(new Color(255, 152, 0));
        gradeAssignmentBtn.addActionListener(e -> gradeAssignments());
        
        viewRankingsBtn = createButton("View Rankings");
        viewRankingsBtn.setBackground(new Color(103, 58, 183));
        viewRankingsBtn.addActionListener(e -> viewRankings());
        
        JButton refreshBtn = createButton("Refresh");
        refreshBtn.setBackground(new Color(40, 167, 69));
        refreshBtn.addActionListener(e -> loadStudents());
        
        buttonPanel.add(viewTranscriptBtn);
        buttonPanel.add(gradeAssignmentBtn);
        buttonPanel.add(viewRankingsBtn);
        buttonPanel.add(refreshBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 35));
        return button;
    }
    
    private void loadCourses() {
        courseComboBox.removeAllItems();
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        if (faculty == null) {
            JOptionPane.showMessageDialog(this, "Faculty profile not found");
            return;
        }
        
        ArrayList<FacultyAssignment> assignments = faculty.getFacultyAssignments();
        
        if (assignments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No courses assigned");
            return;
        }
        
        for (FacultyAssignment fa : assignments) {
            CourseOffer offer = fa.getCourseOffer();
            Course course = offer.getSubjectCourse();
            String displayText = course.getCourseNumber() + " - " + course.getTitle();
            courseComboBox.addItem(displayText);
        }
    }
    
    private void loadStudents() {
        tableModel.setRowCount(0);
        
        int selectedIndex = courseComboBox.getSelectedIndex();
        if (selectedIndex == -1) {
            statsLabel.setText("");
            return;
        }
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment assignment = faculty.getFacultyAssignments().get(selectedIndex);
        CourseOffer courseOffer = assignment.getCourseOffer();
        
        // Calculate rankings
        ArrayList<GradeCalculator.StudentRank> rankings = GradeCalculator.rankStudents(courseOffer);
        
        // Calculate class GPA
        double classGPA = GradeCalculator.calculateClassGPA(courseOffer);
        double classAvg = GradeCalculator.calculateClassAverage(courseOffer);
        
        // Update stats label
        statsLabel.setText(String.format("Enrolled: %d/%d | Class GPA: %.2f | Class Avg: %.1f%%", 
            courseOffer.getEnrolledCount(), 
            courseOffer.getTotalSeats(),
            classGPA,
            classAvg));
        
        // Populate table
        for (GradeCalculator.StudentRank rank : rankings) {
            Seat seat = rank.seat;
            StudentProfile student = seat.getSeatAssignment().getStudentProfile();
            
            Object[] row = {
                student.getPerson().getId(),
                "Student " + student.getPerson().getId(),
                String.format("%.2f%%", rank.percentage),
                rank.letterGrade,
                String.format("%.2f", GradeCalculator.letterToGPA(rank.letterGrade)),
                rank.rank
            };
            tableModel.addRow(row);
        }
    }
    
    private void viewStudentTranscript() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student first");
            return;
        }
        
        int courseIndex = courseComboBox.getSelectedIndex();
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment assignment = faculty.getFacultyAssignments().get(courseIndex);
        CourseOffer courseOffer = assignment.getCourseOffer();
        
        // Get the student's seat
        ArrayList<GradeCalculator.StudentRank> rankings = GradeCalculator.rankStudents(courseOffer);
        Seat seat = rankings.get(selectedRow).seat;
        
        // Show transcript dialog
        showTranscriptDialog(seat, courseOffer);
    }
    
    private void showTranscriptDialog(Seat seat, CourseOffer courseOffer) {
        StudentProfile student = seat.getSeatAssignment().getStudentProfile();
        ArrayList<AssignmentGrade> grades = courseOffer.getStudentGrades(seat);
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Student Transcript", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel headerLabel = new JLabel("Student ID: " + student.getPerson().getId());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Assignment", "Points Earned", "Max Points", "Percentage", "Feedback"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (AssignmentGrade grade : grades) {
            Object[] row = {
                grade.getAssignment().getTitle(),
                grade.getPointsEarned(),
                grade.getAssignment().getMaxPoints(),
                String.format("%.1f%%", grade.getPercentage()),
                grade.getFeedback()
            };
            model.addRow(row);
        }
        
        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        // Footer with totals
        double percentage = GradeCalculator.calculatePercentage(grades, courseOffer.getAssignments());
        String letter = GradeCalculator.getLetterGrade(percentage);
        double gpa = GradeCalculator.letterToGPA(letter);
        
        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel footerLabel = new JLabel(String.format("Total: %.2f%% | Letter Grade: %s | GPA: %.2f", 
            percentage, letter, gpa));
        footerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        footerPanel.add(footerLabel);
        dialog.add(footerPanel, BorderLayout.SOUTH);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void gradeAssignments() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student first");
            return;
        }
        
        int courseIndex = courseComboBox.getSelectedIndex();
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment assignment = faculty.getFacultyAssignments().get(courseIndex);
        CourseOffer courseOffer = assignment.getCourseOffer();
        
        ArrayList<GradeCalculator.StudentRank> rankings = GradeCalculator.rankStudents(courseOffer);
        Seat seat = rankings.get(selectedRow).seat;
        
        showGradingDialog(seat, courseOffer);
    }
    
    private void showGradingDialog(Seat seat, CourseOffer courseOffer) {
        StudentProfile student = seat.getSeatAssignment().getStudentProfile();
        ArrayList<Assignment> assignments = courseOffer.getAssignments();
        
        if (assignments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No assignments found for this course");
            return;
        }
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Grade Assignments", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(700, 500);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 152, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel headerLabel = new JLabel("Grade Student: " + student.getPerson().getId());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);
        
        // Center panel with form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JComboBox<String> assignmentCombo = new JComboBox<>();
        for (Assignment a : assignments) {
            assignmentCombo.addItem(a.getTitle() + " (Max: " + a.getMaxPoints() + " pts)");
        }
        
        JTextField pointsField = new JTextField(10);
        JTextArea feedbackArea = new JTextArea(5, 30);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        
        // Update current grade when assignment changes
        assignmentCombo.addActionListener(e -> {
            int idx = assignmentCombo.getSelectedIndex();
            if (idx >= 0) {
                Assignment a = assignments.get(idx);
                AssignmentGrade grade = courseOffer.getAssignmentGrade(a, seat);
                pointsField.setText(String.valueOf(grade.getPointsEarned()));
                feedbackArea.setText(grade.getFeedback());
            }
        });
        
        // Trigger initial load
        if (assignmentCombo.getItemCount() > 0) {
            Assignment a = assignments.get(0);
            AssignmentGrade grade = courseOffer.getAssignmentGrade(a, seat);
            pointsField.setText(String.valueOf(grade.getPointsEarned()));
            feedbackArea.setText(grade.getFeedback());
        }
        
        formPanel.add(createFormRow("Assignment:", assignmentCombo));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createFormRow("Points Earned:", pointsField));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(new JLabel("Feedback:"));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(new JScrollPane(feedbackArea));
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton saveBtn = new JButton("Save Grade");
        saveBtn.setBackground(new Color(40, 167, 69));
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            try {
                int idx = assignmentCombo.getSelectedIndex();
                Assignment a = assignments.get(idx);
                int points = Integer.parseInt(pointsField.getText());
                String feedback = feedbackArea.getText();
                
                if (points < 0 || points > a.getMaxPoints()) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Points must be between 0 and " + a.getMaxPoints());
                    return;
                }
                
                AssignmentGrade grade = courseOffer.getAssignmentGrade(a, seat);
                grade.grade(points, feedback);
                
                JOptionPane.showMessageDialog(dialog, "Grade saved successfully!");
                dialog.dispose();
                loadStudents(); // Refresh the table
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid points value");
            }
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
    
    private JPanel createFormRow(String label, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel.setPreferredSize(new Dimension(150, 25));
        panel.add(jLabel);
        panel.add(component);
        return panel;
    }
    
    private void viewRankings() {
        int courseIndex = courseComboBox.getSelectedIndex();
        if (courseIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course first");
            return;
        }
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment assignment = faculty.getFacultyAssignments().get(courseIndex);
        CourseOffer courseOffer = assignment.getCourseOffer();
        Course course = courseOffer.getSubjectCourse();
        
        ArrayList<GradeCalculator.StudentRank> rankings = GradeCalculator.rankStudents(courseOffer);
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Class Rankings", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(700, 500);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(103, 58, 183));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel headerLabel = new JLabel(course.getCourseNumber() + " - Class Rankings");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Rank", "Student ID", "Percentage", "Letter Grade", "GPA"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (GradeCalculator.StudentRank rank : rankings) {
            StudentProfile student = rank.seat.getSeatAssignment().getStudentProfile();
            Object[] row = {
                rank.rank,
                student.getPerson().getId(),
                String.format("%.2f%%", rank.percentage),
                rank.letterGrade,
                String.format("%.2f", GradeCalculator.letterToGPA(rank.letterGrade))
            };
            model.addRow(row);
        }
        
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Footer
        double classGPA = GradeCalculator.calculateClassGPA(courseOffer);
        double classAvg = GradeCalculator.calculateClassAverage(courseOffer);
        
        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel footerLabel = new JLabel(String.format("Class Average: %.2f%% | Class GPA: %.2f | Total Students: %d", 
            classAvg, classGPA, rankings.size()));
        footerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        footerPanel.add(footerLabel);
        dialog.add(footerPanel, BorderLayout.SOUTH);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void goBack() {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "mainMenu");
    }
}
