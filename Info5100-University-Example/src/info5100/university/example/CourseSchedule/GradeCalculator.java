package info5100.university.example.CourseSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Utility class for grade calculations
 */
public class GradeCalculator {
    
    /**
     * Calculate total points earned by a student across all assignments
     */
    public static int calculateTotalPoints(ArrayList<AssignmentGrade> grades) {
        int total = 0;
        for (AssignmentGrade grade : grades) {
            if (grade.isGraded()) {
                total += grade.getPointsEarned();
            }
        }
        return total;
    }
    
    /**
     * Calculate maximum possible points across all assignments
     */
    public static int calculateMaxPoints(ArrayList<Assignment> assignments) {
        int total = 0;
        for (Assignment assignment : assignments) {
            total += assignment.getMaxPoints();
        }
        return total;
    }
    
    /**
     * Calculate percentage grade for a student
     */
    public static double calculatePercentage(ArrayList<AssignmentGrade> grades, ArrayList<Assignment> assignments) {
        int totalEarned = calculateTotalPoints(grades);
        int maxPoints = calculateMaxPoints(assignments);
        
        if (maxPoints == 0) return 0.0;
        return (totalEarned * 100.0) / maxPoints;
    }
    
    /**
     * Convert percentage to letter grade
     * A: 93-100, A-: 90-92, B+: 87-89, B: 83-86, B-: 80-82
     * C+: 77-79, C: 73-76, C-: 70-72, D+: 67-69, D: 60-66, F: 0-59
     */
    public static String getLetterGrade(double percentage) {
        if (percentage >= 93) return "A";
        else if (percentage >= 90) return "A-";
        else if (percentage >= 87) return "B+";
        else if (percentage >= 83) return "B";
        else if (percentage >= 80) return "B-";
        else if (percentage >= 77) return "C+";
        else if (percentage >= 73) return "C";
        else if (percentage >= 70) return "C-";
        else if (percentage >= 67) return "D+";
        else if (percentage >= 60) return "D";
        else return "F";
    }
    
    /**
     * Convert letter grade to GPA points
     */
    public static double letterToGPA(String letterGrade) {
        switch (letterGrade) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }
    
    /**
     * Calculate class average GPA
     */
    public static double calculateClassGPA(CourseOffer courseOffer) {
        ArrayList<Seat> seats = courseOffer.getSeatlist();
        double totalGPA = 0.0;
        int count = 0;
        
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                ArrayList<AssignmentGrade> grades = courseOffer.getStudentGrades(seat);
                double percentage = calculatePercentage(grades, courseOffer.getAssignments());
                String letter = getLetterGrade(percentage);
                totalGPA += letterToGPA(letter);
                count++;
            }
        }
        
        return count > 0 ? totalGPA / count : 0.0;
    }
    
    /**
     * Calculate average percentage for the class
     */
    public static double calculateClassAverage(CourseOffer courseOffer) {
        ArrayList<Seat> seats = courseOffer.getSeatlist();
        double totalPercentage = 0.0;
        int count = 0;
        
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                ArrayList<AssignmentGrade> grades = courseOffer.getStudentGrades(seat);
                totalPercentage += calculatePercentage(grades, courseOffer.getAssignments());
                count++;
            }
        }
        
        return count > 0 ? totalPercentage / count : 0.0;
    }
    
    /**
     * Get grade distribution (count of each letter grade)
     */
    public static java.util.Map<String, Integer> getGradeDistribution(CourseOffer courseOffer) {
        java.util.Map<String, Integer> distribution = new java.util.LinkedHashMap<>();
        String[] grades = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
        
        // Initialize
        for (String grade : grades) {
            distribution.put(grade, 0);
        }
        
        // Count
        ArrayList<Seat> seats = courseOffer.getSeatlist();
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                ArrayList<AssignmentGrade> studentGrades = courseOffer.getStudentGrades(seat);
                double percentage = calculatePercentage(studentGrades, courseOffer.getAssignments());
                String letter = getLetterGrade(percentage);
                distribution.put(letter, distribution.get(letter) + 1);
            }
        }
        
        return distribution;
    }
    
    /**
     * Rank students by total grade percentage
     */
    public static class StudentRank {
        public Seat seat;
        public double percentage;
        public String letterGrade;
        public int rank;
        
        public StudentRank(Seat seat, double percentage, String letterGrade) {
            this.seat = seat;
            this.percentage = percentage;
            this.letterGrade = letterGrade;
        }
    }
    
    public static ArrayList<StudentRank> rankStudents(CourseOffer courseOffer) {
        ArrayList<StudentRank> rankings = new ArrayList<>();
        ArrayList<Seat> seats = courseOffer.getSeatlist();
        
        // Calculate percentages for all students
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                ArrayList<AssignmentGrade> grades = courseOffer.getStudentGrades(seat);
                double percentage = calculatePercentage(grades, courseOffer.getAssignments());
                String letter = getLetterGrade(percentage);
                rankings.add(new StudentRank(seat, percentage, letter));
            }
        }
        
        // Sort by percentage descending
        Collections.sort(rankings, new Comparator<StudentRank>() {
            @Override
            public int compare(StudentRank r1, StudentRank r2) {
                return Double.compare(r2.percentage, r1.percentage);
            }
        });
        
        // Assign ranks
        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).rank = i + 1;
        }
        
        return rankings;
    }
}

