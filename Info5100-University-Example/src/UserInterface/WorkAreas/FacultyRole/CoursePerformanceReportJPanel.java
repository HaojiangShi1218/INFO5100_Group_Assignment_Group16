package UserInterface.WorkAreas.FacultyRole;
import info5100.university.example.College.College;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.UserAccount;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.CourseSchedule.*;
import info5100.university.example.CourseCatalog.Course;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Course performance report panel
 */
public class CoursePerformanceReportJPanel extends JPanel {
    
    Department department;
    UserAccount userAccount;
    FacultyProfile faculty;
    JPanel cardPanel;
    JComboBox<String> courseComboBox;
    JComboBox<String> semesterComboBox;
    JTextArea reportArea;
    JPanel chartPanel;
    
    public CoursePerformanceReportJPanel(Department department, UserAccount userAccount, JPanel cardPanel) {
        this.department = department;
        this.userAccount = userAccount;
        this.cardPanel = cardPanel;
        faculty = this.department.getFacultydirectory().findTeachingFaculty(this.userAccount.getPersonId());
        
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Course Performance Report");
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
        
        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        
        JLabel semesterLabel = new JLabel("Semester:");
        semesterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        semesterComboBox = new JComboBox<>();
        semesterComboBox.setPreferredSize(new Dimension(150, 30));
        semesterComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        semesterComboBox.addActionListener(e -> loadCourses());
        
        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        courseComboBox = new JComboBox<>();
        courseComboBox.setPreferredSize(new Dimension(300, 30));
        courseComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        courseComboBox.addActionListener(e -> generateReport());
        
        JButton generateBtn = new JButton("Generate Report");
        generateBtn.setBackground(new Color(40, 167, 69));
        generateBtn.setForeground(Color.BLACK);
        generateBtn.setFocusPainted(false);
        generateBtn.addActionListener(e -> generateReport());
        
        JButton exportBtn = new JButton("Export PDF");
        exportBtn.setBackground(new Color(220, 53, 69));
        exportBtn.setForeground(Color.BLACK);
        exportBtn.setFocusPainted(false);
        exportBtn.addActionListener(e -> exportReport());
        
        filterPanel.add(semesterLabel);
        filterPanel.add(semesterComboBox);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(courseLabel);
        filterPanel.add(courseComboBox);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(generateBtn);
        filterPanel.add(exportBtn);
        
        add(filterPanel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.SOUTH);
        
        // Main content with split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.5);
        
        // Report text area
        reportArea = new JTextArea();
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reportArea.setEditable(false);
        reportArea.setMargin(new Insets(15, 15, 15, 15));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Performance Statistics"));
        splitPane.setTopComponent(scrollPane);
        
        // Chart panel
        chartPanel = new JPanel();
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createTitledBorder("Grade Distribution"));
        splitPane.setBottomComponent(new JScrollPane(chartPanel));
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void loadData() {
        // Load semesters
        semesterComboBox.removeAllItems();
        ArrayList<CourseSchedule> schedules = department.getCourseSchedules();
        for (CourseSchedule schedule : schedules) {
            semesterComboBox.addItem(schedule.getSemester());
        }
        
        loadCourses();
    }
    
    private void loadCourses() {
        courseComboBox.removeAllItems();
        
        if (semesterComboBox.getSelectedItem() == null) return;
        
        
        
        ArrayList<FacultyAssignment> assignments = faculty.getFacultyassignments();
        for (FacultyAssignment fa : assignments) {
            CourseOffer offer = fa.getCourseoffer();
            Course course = offer.getSubjectCourse();
            String displayText = course.getCOurseNumber() + " - " + course.getName();
            courseComboBox.addItem(displayText);
        }
    }
    
    private void generateReport() {
        int courseIndex = courseComboBox.getSelectedIndex();
        if (courseIndex == -1) {
            reportArea.setText("Please select a course to generate report.");
            chartPanel.removeAll();
            chartPanel.revalidate();
            chartPanel.repaint();
            return;
        }
        
        
        FacultyAssignment assignment = faculty.getFacultyassignments().get(courseIndex);
        CourseOffer courseOffer = assignment.getCourseoffer();
        Course course = courseOffer.getSubjectCourse();
        
        // Generate report text
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("              COURSE PERFORMANCE REPORT\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        report.append("Course Information:\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        report.append(String.format("  Course Number:    %s\n", course.getCOurseNumber()));
        report.append(String.format("  Course Title:     %s\n", course.getName()));
        report.append(String.format("  Credits:          %d\n", course.getCredits()));
        report.append(String.format("  Semester:         %s\n", semesterComboBox.getSelectedItem()));
        report.append(String.format("  Instructor:       %s\n", userAccount.getUserLoginName()));
        report.append("\n");
        
        // Enrollment statistics
        int enrolled = courseOffer.getEnrolledCount();
        int totalSeats = courseOffer.getTotalSeats();
        double enrollmentRate = (enrolled * 100.0) / totalSeats;
        
        report.append("Enrollment Statistics:\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        report.append(String.format("  Total Seats:      %d\n", totalSeats));
        report.append(String.format("  Enrolled:         %d\n", enrolled));
        report.append(String.format("  Available:        %d\n", totalSeats - enrolled));
        report.append(String.format("  Enrollment Rate:  %.1f%%\n", enrollmentRate));
        report.append(String.format("  Enrollment Status:%s\n", courseOffer.isEnrollmentOpen() ? " OPEN" : " CLOSED"));
        report.append("\n");
        
        // Grade statistics
        double classAvg = GradeCalculator.calculateClassAverage(courseOffer);
        double classGPA = GradeCalculator.calculateClassGPA(courseOffer);
        
        report.append("Grade Statistics:\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        report.append(String.format("  Class Average:    %.2f%%\n", classAvg));
        report.append(String.format("  Class GPA:        %.2f / 4.0\n", classGPA));
        report.append("\n");
        
        // Grade distribution
        Map<String, Integer> distribution = GradeCalculator.getGradeDistribution(courseOffer);
        
        report.append("Grade Distribution:\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        report.append("  Grade    Count    Percentage    Bar Chart\n");
        report.append("  ─────    ─────    ──────────    ───────────────────────\n");
        
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            String grade = entry.getKey();
            int count = entry.getValue();
            double percentage = enrolled > 0 ? (count * 100.0) / enrolled : 0;
            int barLength = (int) (percentage / 2); // Scale down for display
            String bar = "█".repeat(Math.max(0, barLength));
            
            report.append(String.format("  %-7s  %5d    %6.1f%%      %s\n", 
                grade, count, percentage, bar));
        }
        report.append("\n");
        
        // Assignment statistics
        ArrayList<Assignment> assignments = courseOffer.getAssignments();
        if (!assignments.isEmpty()) {
            report.append("Assignment Statistics:\n");
            report.append("─────────────────────────────────────────────────────────────\n");
            report.append(String.format("  Total Assignments: %d\n\n", assignments.size()));
            
            for (Assignment a : assignments) {
                ArrayList<AssignmentGrade> allGrades = courseOffer.getAssignmentGrades();
                int gradedCount = 0;
                double totalPercentage = 0;
                
                for (AssignmentGrade ag : allGrades) {
                    if (ag.getAssignment() == a && ag.isGraded()) {
                        gradedCount++;
                        totalPercentage += ag.getPercentage();
                    }
                }
                
                double avgPercentage = gradedCount > 0 ? totalPercentage / gradedCount : 0;
                
                report.append(String.format("  %s:\n", a.getTitle()));
                report.append(String.format("    Max Points:  %d\n", a.getMaxPoints()));
                report.append(String.format("    Graded:      %d / %d\n", gradedCount, enrolled));
                report.append(String.format("    Average:     %.1f%%\n\n", avgPercentage));
            }
        }
        
        // Tuition revenue
        int tuitionRevenue = courseOffer.getTotalCourseRevenues();
        report.append("Financial Summary:\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        report.append(String.format("  Total Tuition Collected: $%,d\n", tuitionRevenue));
        report.append(String.format("  Per Student: $%,d\n", course.getCoursePrice()));
        report.append("\n");
        
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("Report generated on: " + new java.util.Date() + "\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        
        reportArea.setText(report.toString());
        reportArea.setCaretPosition(0);
        
        // Generate chart
        displayGradeChart(distribution, enrolled);
    }
    
    private void displayGradeChart(Map<String, Integer> distribution, int total) {
        chartPanel.removeAll();
        chartPanel.setLayout(new BorderLayout());
        
        JPanel barsPanel = new JPanel();
        barsPanel.setLayout(new GridLayout(1, distribution.size(), 10, 10));
        barsPanel.setBackground(Color.WHITE);
        barsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Find max for scaling
        int maxCount = 0;
        for (int count : distribution.values()) {
            maxCount = Math.max(maxCount, count);
        }
        
        final int finalMaxCount = maxCount; // Make final for inner class
        
        // Create bar for each grade
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            String grade = entry.getKey();
            int count = entry.getValue();
            double percentage = total > 0 ? (count * 100.0) / total : 0;
            
            final int finalCount = count; // Make final for inner class
            final String finalGrade = grade; // Make final for inner class
            
            JPanel barContainer = new JPanel(new BorderLayout());
            barContainer.setBackground(Color.WHITE);
            
            // Bar
            JPanel bar = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    
                    int height = getHeight();
                    int width = getWidth();
                    int barHeight = finalMaxCount > 0 ? (int) ((finalCount * height) / finalMaxCount) : 0;
                    
                    // Draw bar
                    Color barColor = getGradeColor(finalGrade);
                    g2d.setColor(barColor);
                    g2d.fillRect(0, height - barHeight, width, barHeight);
                    
                    // Draw border
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.drawRect(0, height - barHeight, width - 1, barHeight - 1);
                    
                    // Draw count
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 14));
                    String countStr = String.valueOf(finalCount);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(countStr);
                    g2d.drawString(countStr, (width - textWidth) / 2, height - barHeight - 5);
                }
            };
            bar.setPreferredSize(new Dimension(50, 200));
            barContainer.add(bar, BorderLayout.CENTER);
            
            // Label
            JLabel label = new JLabel(String.format("<html><center>%s<br>%.1f%%</center></html>", 
                grade, percentage), SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            barContainer.add(label, BorderLayout.SOUTH);
            
            barsPanel.add(barContainer);
        }
        
        chartPanel.add(barsPanel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    private Color getGradeColor(String grade) {
        switch (grade) {
            case "A": return new Color(76, 175, 80);
            case "A-": return new Color(139, 195, 74);
            case "B+": return new Color(205, 220, 57);
            case "B": return new Color(255, 235, 59);
            case "B-": return new Color(255, 193, 7);
            case "C+": return new Color(255, 152, 0);
            case "C": return new Color(255, 87, 34);
            case "C-": return new Color(244, 67, 54);
            case "D+": return new Color(233, 30, 99);
            case "D": return new Color(156, 39, 176);
            case "F": return new Color(103, 58, 183);
            default: return Color.GRAY;
        }
    }
    
    private void exportReport() {
        if (reportArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please generate a report first");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setSelectedFile(new java.io.File("course_report.txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File file = fileChooser.getSelectedFile();
                java.io.FileWriter writer = new java.io.FileWriter(file);
                writer.write(reportArea.getText());
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
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "faculty");
    }
}

