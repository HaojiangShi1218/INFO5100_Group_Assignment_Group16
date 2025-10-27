package info5100.university.example.CourseSchedule;

import java.util.Date;

/**
 * Represents a grade for a student's assignment submission
 */
public class AssignmentGrade {
    
    private Assignment assignment;
    private Seat seat; // The student's seat in the course
    private int pointsEarned;
    private String feedback;
    private Date submittedDate;
    private Date gradedDate;
    
    public AssignmentGrade(Assignment assignment, Seat seat) {
        this.assignment = assignment;
        this.seat = seat;
        this.pointsEarned = 0; // Default ungraded
        this.feedback = "";
        this.submittedDate = null;
        this.gradedDate = null;
    }
    
    // Getters
    public Assignment getAssignment() {
        return assignment;
    }
    
    public Seat getSeat() {
        return seat;
    }
    
    public int getPointsEarned() {
        return pointsEarned;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public Date getSubmittedDate() {
        return submittedDate;
    }
    
    public Date getGradedDate() {
        return gradedDate;
    }
    
    public double getPercentage() {
        if (assignment.getMaxPoints() == 0) return 0;
        return (pointsEarned * 100.0) / assignment.getMaxPoints();
    }
    
    public boolean isGraded() {
        return gradedDate != null;
    }
    
    // Setters
    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
        this.gradedDate = new Date();
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }
    
    public void grade(int points, String feedback) {
        this.pointsEarned = Math.min(points, assignment.getMaxPoints());
        this.feedback = feedback;
        this.gradedDate = new Date();
    }
    
    @Override
    public String toString() {
        return assignment.getTitle() + ": " + pointsEarned + "/" + assignment.getMaxPoints();
    }
}

