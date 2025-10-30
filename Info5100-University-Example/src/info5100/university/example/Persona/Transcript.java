/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author kal bugrara
 */
public class Transcript {

    StudentProfile student;
    HashMap<String, CourseLoad> courseloadlist;

    CourseLoad currentcourseload;

    public Transcript(StudentProfile sp) {
        student = sp;
        courseloadlist = new HashMap();

    }

    public int getStudentSatisfactionIndex() {
        //for each courseload 
        //get seatassigmnets; 
        //for each seatassignment add 1 if like =true;
        return 0;
    }

    public CourseLoad newCourseLoad(String sem) {

        currentcourseload = new CourseLoad(sem);
        courseloadlist.put(sem, currentcourseload);
        return currentcourseload;
    }

    public CourseLoad getCurrentCourseLoad() {

        return currentcourseload;

    }

    public CourseLoad getCourseLoadBySemester(String semester) {

        return courseloadlist.get(semester);

    }

    public float getStudentTotalScore() {

        float sum = 0;

        for (CourseLoad cl : courseloadlist.values()) {
            sum = sum + cl.getSemesterScore();

        }
        return sum;
    }
    //sat index means student rated their courses with likes;
    public int getStudentSatifactionIndex() {
        ArrayList<SeatAssignment> courseregistrations = getCourseList();
        int sum = 0;
        for (SeatAssignment sa : courseregistrations) {

            if (sa.getLike()) {
                sum = sum + 1;
            }
        }
        return sum;
    }
    //generate a list of all courses taken so far (seetassignments) 
    //from multiple semesters (course loads)
    //from seat assignments we will be able to access the course offers

    public ArrayList<SeatAssignment> getCourseList() {
        ArrayList temp2;
        temp2 = new ArrayList();

        for (CourseLoad cl : courseloadlist.values()) { //extract cl list as objects --ignore label
            temp2.addAll(cl.getSeatAssignments()); //merge one array list to another
        }

        return temp2;

    }
    
    public ArrayList<String> getAllTerms() {
        ArrayList<String> terms = new ArrayList<>(courseloadlist.keySet());
        return terms;
    }
    
    public ArrayList<SeatAssignment> getCoursesByTerm(String term) {
        CourseLoad cl = courseloadlist.get(term);
        if (cl == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(cl.getSeatAssignments());
    }
    
    /** Compute GPA for a specific term (e.g., "Fall2020"). */
    public double computeTermGpa(String term) {
        List<SeatAssignment> termCourses = student.getCoursesByTerm(term);
        double qp = 0.0;   // quality points
        double cr = 0.0;   // credits
        if (termCourses != null) {
            for (SeatAssignment sa : termCourses) {
                if (sa == null) continue;
                double gp = gradeToPoints(sa.getGradeLetter());
                qp += gp * sa.getCreditHours();
                cr += sa.getCreditHours();
            }
        }
        return cr == 0.0 ? 0.0 : qp / cr;
    }

    /** Compute cumulative GPA across all completed/recorded courses. */
    public double computeOverallGpa() {
        List<SeatAssignment> all = student.getAllSeatAssignments();
        double qp = 0.0, cr = 0.0;
        if (all != null) {
            for (SeatAssignment sa : all) {
                if (sa == null) continue;
                double gp = gradeToPoints(sa.getGradeLetter());
                qp += gp * sa.getCreditHours();
                cr += sa.getCreditHours();
            }
        }
        return cr == 0.0 ? 0.0 : qp / cr;
    }

    /** Derive academic standing for the given term using term & overall GPA. */
    public String getAcademicStanding(String term) {
        double termGpa = computeTermGpa(term);
        double overallGpa = computeOverallGpa();
        return getAcademicStanding(termGpa, overallGpa);
    }

    /** Core standing rule (kept public for reuse/testing). */
    public String getAcademicStanding(double termGpa, double overallGpa) {
        if (overallGpa < 3.0) {
            return "Academic Probation";
        } else if (termGpa < 3.0) {
            return "Academic Warning";
        } else {
            return "Good Standing";
        }
    }

    /** Grade → grade points. Extend if you support more letters. */
    private static double gradeToPoints(String grade) {
        if (grade == null) return 0.0;
        switch (grade) {
            case "A":  return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B":  return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C":  return 2.0;
            case "C-": return 1.7;
            case "F":  return 0.0;
            default:   return 0.0;
        }
    }

}
