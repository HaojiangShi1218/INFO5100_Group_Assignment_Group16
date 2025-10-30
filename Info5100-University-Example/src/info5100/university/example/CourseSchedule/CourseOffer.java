/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class CourseOffer {

    Course course;
    ArrayList<Seat> seatlist;
    FacultyAssignment facultyassignment;
    ArrayList<Assignment> assignments;
    ArrayList<AssignmentGrade> assignmentGrades;
    String syllabusPath;
    boolean enrollmentOpen;

    public CourseOffer(Course c) {
        course = c;
        seatlist = new ArrayList();
        assignments = new ArrayList();
        assignmentGrades = new ArrayList();
        syllabusPath = "";
        enrollmentOpen = true; // Default: enrollment is open
    }
     
    public void AssignAsTeacher(FacultyProfile fp) {

        facultyassignment = new FacultyAssignment(fp, this);
    }

    public FacultyProfile getFacultyProfile() {
        return (facultyassignment == null) ? null : facultyassignment.getFacultyProfile();
        //return facultyassignment.getFacultyProfile();
    }

    public String getCourseNumber() {
        return course.getCOurseNumber();
    }

    public void generatSeats(int n) {

        for (int i = 0; i < n; i++) {

            seatlist.add(new Seat(this, i));

        }

    }

    public Seat getEmptySeat() {

        for (Seat s : seatlist) {

            if (!s.isOccupied()) {
                return s;
            }
        }
        return null;
    }


    public SeatAssignment assignEmptySeat(CourseLoad cl) {

        Seat seat = getEmptySeat();
        if (seat == null) {
            return null;
        }
        SeatAssignment sa = seat.newSeatAssignment(cl); //seat is already linked to course offer
        cl.registerStudent(sa); //coures offer seat is now linked to student
        return sa;
    }

    public int getTotalCourseRevenues() {

        int sum = 0;

        for (Seat s : seatlist) {
            if (s.isOccupied() == true) {
                sum = sum + course.getCoursePrice();
            }

        }
        return sum;
    }
    public Course getSubjectCourse(){
        return course;
    }
    public int getCreditHours(){
        return course.getCredits();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<Seat> getSeatlist() {
        return seatlist;
    }
    
    public void setSyllabusPath(String path) {
        this.syllabusPath = path;
    }
    
    // Enrollment management
    public boolean isEnrollmentOpen() {
        return enrollmentOpen;
    }
    
    public void setEnrollmentOpen(boolean open) {
        this.enrollmentOpen = open;
    }
    
    public void openEnrollment() {
        this.enrollmentOpen = true;
    }
    
    public void closeEnrollment() {
        this.enrollmentOpen = false;
    }
    
    // Get enrolled students count
    public int getEnrolledCount() {
        int count = 0;
        for (Seat s : seatlist) {
            if (s.isOccupied()) {
                count++;
            }
        }
        return count;
    }
    
    // Get total seats
    public int getTotalSeats() {
        return seatlist.size();
    }
    
    // Assignment management
    public Assignment addAssignment(String assignmentId, String title, String description, int maxPoints, java.util.Date dueDate) {
        Assignment assignment = new Assignment(assignmentId, title, description, maxPoints, dueDate, this);
        assignments.add(assignment);
        
        // Create assignment grades for all enrolled students
        for (Seat seat : seatlist) {
            if (seat.isOccupied()) {
                assignmentGrades.add(new AssignmentGrade(assignment, seat));
            }
        }
        return assignment;
    }
    
    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }
    
    public ArrayList<AssignmentGrade> getAssignmentGrades() {
        return assignmentGrades;
    }
    
    public ArrayList<AssignmentGrade> getStudentGrades(Seat seat) {
        ArrayList<AssignmentGrade> grades = new ArrayList();
        for (AssignmentGrade grade : assignmentGrades) {
            if (grade.getSeat() == seat) {
                grades.add(grade);
            }
        }
        return grades;
    }
    
    public AssignmentGrade getAssignmentGrade(Assignment assignment, Seat seat) {
        for (AssignmentGrade grade : assignmentGrades) {
            if (grade.getAssignment() == assignment && grade.getSeat() == seat) {
                return grade;
            }
        }
        // If not found, create a new one
        AssignmentGrade newGrade = new AssignmentGrade(assignment, seat);
        assignmentGrades.add(newGrade);
        return newGrade;
    }
    
    
    

}
