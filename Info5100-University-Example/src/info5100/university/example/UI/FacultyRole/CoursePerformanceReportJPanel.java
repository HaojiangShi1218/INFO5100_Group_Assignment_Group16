package info5100.university.example.UI.FacultyRole;

import info5100.university.example.UniversityBusiness;
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
    
    UniversityBusiness business;
    UserAccount userAccount;
    JPanel cardPanel;
    JComboBox<String> courseComboBox;
    JComboBox<String> semesterComboBox;
    JTextArea reportArea;
    JPanel chartPanel;
    
    public CoursePerformanceReportJPanel(UniversityBusiness business, UserAccount userAccount, JPanel cardPanel) {
        this.business = business;
        this.userAccount = userAccount;
        this.cardPanel = cardPanel;
        
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 800)); // Set minimum size
        
        // ============= TOP SECTION: Header + Filter =============
        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        
        // Header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel titleLabel = new JLabel("Course Performance Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(51, 122, 183));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setPreferredSize(new Dimension(90, 32));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 96, 144), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        backButton.addActionListener(e -> goBack());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        topSection.add(headerPanel);
        
        // Filter panel with controls
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(new Color(245, 247, 250));
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel semesterLabel = new JLabel("Semester:");
        semesterLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        semesterComboBox = new JComboBox<>();
        semesterComboBox.setPreferredSize(new Dimension(140, 32));
        semesterComboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        semesterComboBox.addActionListener(e -> loadCourses());
        
        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        courseComboBox = new JComboBox<>();
        courseComboBox.setPreferredSize(new Dimension(280, 32));
        courseComboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        courseComboBox.addActionListener(e -> generateReport());
        
        JButton generateBtn = new JButton("Generate Report");
        generateBtn.setBackground(new Color(40, 167, 69));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("Arial", Font.BOLD, 12));
        generateBtn.setFocusPainted(false);
        generateBtn.setPreferredSize(new Dimension(140, 32));
        generateBtn.addActionListener(e -> generateReport());
        
        JButton exportBtn = new JButton("Export TXT");
        exportBtn.setBackground(new Color(220, 53, 69));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setFont(new Font("Arial", Font.BOLD, 12));
        exportBtn.setFocusPainted(false);
        exportBtn.setPreferredSize(new Dimension(110, 32));
        exportBtn.addActionListener(e -> exportReport());
        
        filterPanel.add(semesterLabel);
        filterPanel.add(semesterComboBox);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(courseLabel);
        filterPanel.add(courseComboBox);
        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(generateBtn);
        filterPanel.add(exportBtn);
        topSection.add(filterPanel);
        
        add(topSection, BorderLayout.NORTH);
        
        // ============= CENTER SECTION: Split Pane =============
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(null);
        
        // Report text area (top)
        reportArea = new JTextArea();
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setEditable(false);
        reportArea.setMargin(new Insets(10, 15, 10, 15));
        reportArea.setLineWrap(false);
        reportArea.setWrapStyleWord(false);
        
        JScrollPane reportScrollPane = new JScrollPane(reportArea);
        reportScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Performance Statistics",
            0, 0, new Font("Arial", Font.BOLD, 13)
        ));
        splitPane.setTopComponent(reportScrollPane);
        
        // Chart panel (bottom)
        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(Color.WHITE);
        
        JScrollPane chartScrollPane = new JScrollPane(chartPanel);
        chartScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            "Grade Distribution Chart",
            0, 0, new Font("Arial", Font.BOLD, 13)
        ));
        chartScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chartScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        splitPane.setBottomComponent(chartScrollPane);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void loadData() {
        // Load semesters
        semesterComboBox.removeAllItems();
        ArrayList<CourseSchedule> schedules = business.getCourseSchedules();
        for (CourseSchedule schedule : schedules) {
            semesterComboBox.addItem(schedule.getSemester());
        }
        
        loadCourses();
    }
    
    private void loadCourses() {
        courseComboBox.removeAllItems();
        
        if (semesterComboBox.getSelectedItem() == null) return;
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        if (faculty == null) return;
        
        ArrayList<FacultyAssignment> assignments = faculty.getFacultyAssignments();
        for (FacultyAssignment fa : assignments) {
            CourseOffer offer = fa.getCourseOffer();
            Course course = offer.getSubjectCourse();
            String displayText = course.getCourseNumber() + " - " + course.getTitle();
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
        
        FacultyProfile faculty = userAccount.getFacultyProfile();
        FacultyAssignment assignment = faculty.getFacultyAssignments().get(courseIndex);
        CourseOffer courseOffer = assignment.getCourseOffer();
        Course course = courseOffer.getSubjectCourse();
        
        // Generate report text
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("              COURSE PERFORMANCE REPORT\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        report.append("Course Information:\n");
        report.append("─────────────────────────────────────────────────────────────\n");
        report.append(String.format("  Course Number:    %s\n", course.getCourseNumber()));
        report.append(String.format("  Course Title:     %s\n", course.getTitle()));
        report.append(String.format("  Credits:          %d\n", course.getCredits()));
        report.append(String.format("  Semester:         %s\n", semesterComboBox.getSelectedItem()));
        report.append(String.format("  Instructor:       %s\n", userAccount.getUsername()));
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
        
        // Create a centered container for bars
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setBackground(Color.WHITE);
        
        JPanel barsPanel = new JPanel();
        barsPanel.setLayout(new GridLayout(1, distribution.size(), 15, 0));
        barsPanel.setBackground(Color.WHITE);
        barsPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Find max for scaling
        int maxCount = 0;
        for (int count : distribution.values()) {
            maxCount = Math.max(maxCount, count);
        }
        
        final int finalMaxCount = Math.max(1, maxCount); // Prevent division by zero
        
        // Create bar for each grade
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            String grade = entry.getKey();
            int count = entry.getValue();
            double percentage = total > 0 ? (count * 100.0) / total : 0;
            
            final int finalCount = count;
            final String finalGrade = grade;
            
            JPanel barContainer = new JPanel(new BorderLayout(0, 8));
            barContainer.setBackground(Color.WHITE);
            
            // Bar with custom painting
            JPanel bar = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    int height = getHeight();
                    int width = getWidth();
                    int barHeight = (int) ((finalCount * height * 0.85) / finalMaxCount);
                    int y = height - barHeight;
                    
                    // Draw bar with gradient
                    Color barColor = getGradeColor(finalGrade);
                    GradientPaint gradient = new GradientPaint(
                        0, y, barColor,
                        0, height, barColor.darker()
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(5, y, width - 10, barHeight, 8, 8);
                    
                    // Draw border
                    g2d.setColor(barColor.darker().darker());
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(5, y, width - 10, barHeight, 8, 8);
                    
                    // Draw count on top of bar
                    if (barHeight > 25) {
                        g2d.setColor(Color.WHITE);
                        g2d.setFont(new Font("Arial", Font.BOLD, 16));
                        String countStr = String.valueOf(finalCount);
                        FontMetrics fm = g2d.getFontMetrics();
                        int textWidth = fm.stringWidth(countStr);
                        g2d.drawString(countStr, (width - textWidth) / 2, y + 20);
                    } else {
                        // If bar too small, draw count above
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(new Font("Arial", Font.BOLD, 14));
                        String countStr = String.valueOf(finalCount);
                        FontMetrics fm = g2d.getFontMetrics();
                        int textWidth = fm.stringWidth(countStr);
                        g2d.drawString(countStr, (width - textWidth) / 2, y - 5);
                    }
                }
            };
            bar.setPreferredSize(new Dimension(80, 280));
            bar.setBackground(Color.WHITE);
            barContainer.add(bar, BorderLayout.CENTER);
            
            // Label panel
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
            labelPanel.setBackground(Color.WHITE);
            
            JLabel gradeLabel = new JLabel(finalGrade);
            gradeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            gradeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel percentLabel = new JLabel(String.format("%.1f%%", percentage));
            percentLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            percentLabel.setForeground(Color.GRAY);
            percentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            labelPanel.add(gradeLabel);
            labelPanel.add(percentLabel);
            barContainer.add(labelPanel, BorderLayout.SOUTH);
            
            barsPanel.add(barContainer);
        }
        
        centerContainer.add(barsPanel);
        chartPanel.add(centerContainer, BorderLayout.CENTER);
        
        // Add legend
        JLabel legendLabel = new JLabel(
            "<html><center><i>Interactive bar chart showing grade distribution. " +
            "Total Students: " + total + "</i></center></html>"
        );
        legendLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        legendLabel.setForeground(Color.GRAY);
        legendLabel.setHorizontalAlignment(SwingConstants.CENTER);
        legendLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        chartPanel.add(legendLabel, BorderLayout.SOUTH);
        
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
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "mainMenu");
    }
}

