package info5100.university.example.CourseSchedule;

import java.util.Date;

/**
 * Represents an assignment for a course
 */
public class Assignment {
    
    private String assignmentId;
    private String title;
    private String description;
    private int maxPoints;
    private Date dueDate;
    private CourseOffer courseOffer;
    
    public Assignment(String assignmentId, String title, String description, int maxPoints, Date dueDate, CourseOffer courseOffer) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.maxPoints = maxPoints;
        this.dueDate = dueDate;
        this.courseOffer = courseOffer;
    }
    
    // Getters
    public String getAssignmentId() {
        return assignmentId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getMaxPoints() {
        return maxPoints;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public CourseOffer getCourseOffer() {
        return courseOffer;
    }
    
    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    @Override
    public String toString() {
        return title + " (" + maxPoints + " pts)";
    }
}

