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
 * Tuition insight panel for faculty
 */
public class TuitionInsightJPanel extends JPanel {
    
    UniversityBusiness business;
    UserAccount userAccount;
    JPanel cardPanel;
    JTable tuitionTable;
    DefaultTableModel tableModel;
    JLabel totalLabel;
    
    public TuitionInsightJPanel(UniversityBusiness business, UserAccount userAccount, JPanel cardPanel) {
        this.business = business;
        this.userAccount = userAccount;
        this.cardPanel = cardPanel;
        
        initComponents();
        loadTuitionData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Create a container for header and info panel
        JPanel northContainer = new JPanel(new BorderLayout());
        
        // Header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Tuition Revenue Insights");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(0, 86, 179));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.addActionListener(e -> goBack());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(245, 245, 245));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel infoLabel = new JLabel("<html><b>View tuition revenue collected from enrolled students in your courses.</b><br>" +
            "This shows the financial impact of your teaching assignments.</html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        infoPanel.add(infoLabel);
        
        // Add header and info to north container
        northContainer.add(headerPanel, BorderLayout.NORTH);
        northContainer.add(infoPanel, BorderLayout.CENTER);
        
        add(northContainer, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"Course Number", "Course Title", "Credits", "Tuition/Student", 
                                "Enrolled", "Total Seats", "Fill Rate", "Total Revenue"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tuitionTable = new JTable(tableModel);
        tuitionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tuitionTable.setRowHeight(35);
        tuitionTable.setFont(new Font("Arial", Font.PLAIN, 13));
        tuitionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tuitionTable.getTableHeader().setBackground(new Color(230, 230, 230));
        
        // Set column widths
        tuitionTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        tuitionTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        tuitionTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        tuitionTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        tuitionTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        tuitionTable.getColumnModel().getColumn(5).setPreferredWidth(90);
        tuitionTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        tuitionTable.getColumnModel().getColumn(7).setPreferredWidth(120);
        
        JScrollPane scrollPane = new JScrollPane(tuitionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom panel with total and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Total label
        totalLabel = new JLabel("Total Revenue: $0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setForeground(new Color(0, 128, 0));
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(new Color(245, 245, 245));
        totalPanel.add(totalLabel);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton refreshBtn = createButton("Refresh", new Color(40, 167, 69));
        refreshBtn.addActionListener(e -> loadTuitionData());
        
        JButton exportBtn = createButton("Export Report", new Color(0, 123, 255));
        exportBtn.addActionListener(e -> exportReport());
        
        JButton viewDetailsBtn = createButton("View Details", new Color(108, 117, 125));
        viewDetailsBtn.addActionListener(e -> viewCourseDetails());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(viewDetailsBtn);
        
        bottomPanel.add(totalPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }
    
    private void loadTuitionData() {
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
        
        int grandTotal = 0;
        
        for (FacultyAssignment fa : assignments) {
            CourseOffer offer = fa.getCourseOffer();
            Course course = offer.getSubjectCourse();
            
            int enrolled = offer.getEnrolledCount();
            int totalSeats = offer.getTotalSeats();
            int tuitionPerStudent = course.getCoursePrice();
            int totalRevenue = offer.getTotalCourseRevenues();
            double fillRate = totalSeats > 0 ? (enrolled * 100.0) / totalSeats : 0;
            
            grandTotal += totalRevenue;
            
            Object[] row = {
                course.getCourseNumber(),
                course.getTitle(),
                course.getCredits(),
                String.format("$%,d", tuitionPerStudent),
                enrolled,
                totalSeats,
                String.format("%.1f%%", fillRate),
                String.format("$%,d", totalRevenue)
            };
            tableModel.addRow(row);
        }
        
        totalLabel.setText(String.format("Total Revenue: $%,d", grandTotal));
    }
    
    private void viewCourseDetails() {
        int selectedRow = tuitionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course first");
            return;
        }
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment selectedAssignment = faculty.getFacultyAssignments().get(selectedRow);
        CourseOffer courseOffer = selectedAssignment.getCourseOffer();
        Course course = courseOffer.getSubjectCourse();
        
        // Show detailed dialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Course Tuition Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel headerLabel = new JLabel(course.getCourseNumber() + " - Tuition Details");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        int enrolled = courseOffer.getEnrolledCount();
        int totalSeats = courseOffer.getTotalSeats();
        int tuitionPerStudent = course.getCoursePrice();
        int totalRevenue = courseOffer.getTotalCourseRevenues();
        double fillRate = totalSeats > 0 ? (enrolled * 100.0) / totalSeats : 0;
        int potentialRevenue = totalSeats * tuitionPerStudent;
        int missedRevenue = potentialRevenue - totalRevenue;
        
        addDetailRow(contentPanel, "Course Title:", course.getTitle());
        addDetailRow(contentPanel, "Credits:", String.valueOf(course.getCredits()));
        addDetailRow(contentPanel, "Tuition per Credit Hour:", "$1,500");
        addDetailRow(contentPanel, "Tuition per Student:", String.format("$%,d", tuitionPerStudent));
        contentPanel.add(Box.createVerticalStrut(15));
        
        addDetailRow(contentPanel, "Total Seats:", String.valueOf(totalSeats));
        addDetailRow(contentPanel, "Enrolled Students:", String.valueOf(enrolled));
        addDetailRow(contentPanel, "Available Seats:", String.valueOf(totalSeats - enrolled));
        addDetailRow(contentPanel, "Fill Rate:", String.format("%.1f%%", fillRate));
        contentPanel.add(Box.createVerticalStrut(15));
        
        addDetailRow(contentPanel, "Current Revenue:", String.format("$%,d", totalRevenue));
        addDetailRow(contentPanel, "Potential Revenue (Full):", String.format("$%,d", potentialRevenue));
        addDetailRow(contentPanel, "Missed Revenue:", String.format("$%,d", missedRevenue));
        contentPanel.add(Box.createVerticalStrut(15));
        
        String enrollmentStatus = courseOffer.isEnrollmentOpen() ? "OPEN" : "CLOSED";
        addDetailRow(contentPanel, "Enrollment Status:", enrollmentStatus);
        
        dialog.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
        
        // Close button
        JPanel buttonPanel = new JPanel();
        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(new Color(108, 117, 125));
        closeBtn.setForeground(Color.BLACK);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeBtn);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void addDetailRow(JPanel panel, String label, String value) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Arial", Font.PLAIN, 14));
        
        rowPanel.add(labelComp, BorderLayout.WEST);
        rowPanel.add(valueComp, BorderLayout.EAST);
        
        panel.add(rowPanel);
        panel.add(Box.createVerticalStrut(8));
    }
    
    private void exportReport() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No data to export");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Tuition Report");
        fileChooser.setSelectedFile(new java.io.File("tuition_report.txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File file = fileChooser.getSelectedFile();
                java.io.FileWriter writer = new java.io.FileWriter(file);
                
                writer.write("═══════════════════════════════════════════════════════════════\n");
                writer.write("                  TUITION REVENUE REPORT\n");
                writer.write("═══════════════════════════════════════════════════════════════\n\n");
                writer.write("Instructor: " + userAccount.getUsername() + "\n");
                writer.write("Generated: " + new java.util.Date() + "\n\n");
                
                writer.write("Course Details:\n");
                writer.write("───────────────────────────────────────────────────────────────\n\n");
                
                FacultyProfile faculty = userAccount.getFacultyProfile();
                ArrayList<FacultyAssignment> assignments = faculty.getFacultyAssignments();
                int grandTotal = 0;
                
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    FacultyAssignment fa = assignments.get(i);
                    CourseOffer offer = fa.getCourseOffer();
                    Course course = offer.getSubjectCourse();
                    int revenue = offer.getTotalCourseRevenues();
                    grandTotal += revenue;
                    
                    writer.write(String.format("Course: %s - %s\n", 
                        course.getCourseNumber(), course.getTitle()));
                    writer.write(String.format("  Credits: %d\n", course.getCredits()));
                    writer.write(String.format("  Tuition per Student: $%,d\n", course.getCoursePrice()));
                    writer.write(String.format("  Enrolled: %d / %d\n", 
                        offer.getEnrolledCount(), offer.getTotalSeats()));
                    writer.write(String.format("  Revenue: $%,d\n\n", revenue));
                }
                
                writer.write("═══════════════════════════════════════════════════════════════\n");
                writer.write(String.format("TOTAL REVENUE: $%,d\n", grandTotal));
                writer.write("═══════════════════════════════════════════════════════════════\n");
                
                writer.close();
                
                JOptionPane.showMessageDialog(this, 
                    "Report exported successfully to:\n" + file.getAbsolutePath(),
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error exporting report: " + e.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void goBack() {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "mainMenu");
    }
}

