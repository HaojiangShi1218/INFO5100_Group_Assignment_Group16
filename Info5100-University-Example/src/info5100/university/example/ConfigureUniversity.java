package info5100.university.example;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.*;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.StudentProfile;
import info5100.university.example.Persona.UserAccount;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import java.util.Date;
import java.util.Calendar;

/**
 * Configure university with initial test data
 */
public class ConfigureUniversity {
    
    public static UniversityBusiness configure() {
        UniversityBusiness business = new UniversityBusiness("Northeastern University");
        
        // Create some persons
        Person p1 = business.getPersonDirectory().newPerson("P001");
        Person p2 = business.getPersonDirectory().newPerson("P002");
        Person p3 = business.getPersonDirectory().newPerson("P003");
        Person p4 = business.getPersonDirectory().newPerson("P004");
        
        // Create faculty profiles
        FacultyProfile faculty1 = business.getFacultyDirectory().newFacultyProfile(p1);
        FacultyProfile faculty2 = business.getFacultyDirectory().newFacultyProfile(p2);
        
        // Create student profiles
        StudentProfile student1 = business.getStudentDirectory().newStudentProfile(p3);
        StudentProfile student2 = business.getStudentDirectory().newStudentProfile(p4);
        
        // Create user accounts
        UserAccount facultyAccount1 = business.getUserAccountDirectory().newUserAccount(faculty1, "prof1", "password");
        
        UserAccount facultyAccount2 = business.getUserAccountDirectory().newUserAccount(faculty2, "prof2", "password");
        
        UserAccount studentAccount1 = business.getUserAccountDirectory().newUserAccount(student1, "student1", "password");
        
        UserAccount studentAccount2 = business.getUserAccountDirectory().newUserAccount(student2, "student2", "password");
        
        // Create courses
        Course course1 = business.getDepartment().newCourse("INFO5100", "Application Engineering", 4);
        Course course2 = business.getDepartment().newCourse("INFO6205", "Program Structure & Algorithms", 4);
        Course course3 = business.getDepartment().newCourse("INFO6150", "Web Design & User Experience", 4);
        Course course4 = business.getDepartment().newCourse("INFO7255", "Advanced Big Data Applications", 4);
        
        // Create course schedule for Fall 2024
        CourseSchedule schedule = business.newCourseSchedule("Fall2024");
        
        // Create course offers
        CourseOffer offer1 = schedule.newCourseOffer("INFO5100");
        CourseOffer offer2 = schedule.newCourseOffer("INFO6205");
        CourseOffer offer3 = schedule.newCourseOffer("INFO6150");
        CourseOffer offer4 = schedule.newCourseOffer("INFO7255");
        
        // Generate seats for each course
        if (offer1 != null) {
            offer1.generatSeats(30);
            faculty1.AssignAsTeacher(offer1);
        }
        
        if (offer2 != null) {
            offer2.generatSeats(25);
            faculty1.AssignAsTeacher(offer2);
        }
        
        if (offer3 != null) {
            offer3.generatSeats(20);
            faculty2.AssignAsTeacher(offer3);
        }
        
        if (offer4 != null) {
            offer4.generatSeats(15);
            faculty2.AssignAsTeacher(offer4);
        }
        
        // Register students for courses
        if (offer1 != null && offer2 != null) {
            CourseLoad load1 = student1.newCourseLoad("Fall2024");
            CourseLoad load2 = student2.newCourseLoad("Fall2024");
            
            // Register student1 for INFO5100 and INFO6205
            offer1.assignEmptySeat(load1);
            offer2.assignEmptySeat(load1);
            
            // Register student2 for INFO6150 and INFO7255
            if (offer3 != null) offer3.assignEmptySeat(load2);
            if (offer4 != null) offer4.assignEmptySeat(load2);
        }
        
        // Add sample assignments to courses
        Calendar cal = Calendar.getInstance();
        
        // INFO5100 assignments
        if (offer1 != null) {
            offer1.setEnrollmentOpen(true);
            cal.add(Calendar.DAY_OF_MONTH, 7);
            Assignment a1 = offer1.addAssignment("A1", "Homework 1: Java Basics", "Complete exercises 1-10", 100, cal.getTime());
            
            cal.add(Calendar.DAY_OF_MONTH, 7);
            Assignment a2 = offer1.addAssignment("A2", "Midterm Project", "Build a simple application", 200, cal.getTime());
            
            cal.add(Calendar.DAY_OF_MONTH, 14);
            Assignment a3 = offer1.addAssignment("A3", "Final Project", "Complete final project requirements", 300, cal.getTime());
            
            // Add some sample grades for student1
            for (Seat seat : offer1.getSeatList()) {
                if (seat.isOccupied()) {
                    AssignmentGrade grade1 = offer1.getAssignmentGrade(a1, seat);
                    grade1.grade(85, "Good work!");
                    
                    AssignmentGrade grade2 = offer1.getAssignmentGrade(a2, seat);
                    grade2.grade(170, "Excellent project!");
                    
                    AssignmentGrade grade3 = offer1.getAssignmentGrade(a3, seat);
                    grade3.grade(250, "Outstanding final project!");
                }
            }
        }
        
        // INFO6205 assignments
        if (offer2 != null) {
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 5);
            Assignment a1 = offer2.addAssignment("A1", "Algorithm Analysis", "Analyze time complexity", 100, cal.getTime());
            
            cal.add(Calendar.DAY_OF_MONTH, 10);
            Assignment a2 = offer2.addAssignment("A2", "Data Structures", "Implement key data structures", 150, cal.getTime());
            
            // Add some sample grades
            for (Seat seat : offer2.getSeatList()) {
                if (seat.isOccupied()) {
                    AssignmentGrade grade1 = offer2.getAssignmentGrade(a1, seat);
                    grade1.grade(92, "Very good analysis!");
                    
                    AssignmentGrade grade2 = offer2.getAssignmentGrade(a2, seat);
                    grade2.grade(135, "Great implementation!");
                }
            }
        }
        
        // INFO6150 assignments
        if (offer3 != null) {
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 3);
            Assignment a1 = offer3.addAssignment("A1", "HTML/CSS Assignment", "Build a responsive webpage", 100, cal.getTime());
            
            cal.add(Calendar.DAY_OF_MONTH, 10);
            Assignment a2 = offer3.addAssignment("A2", "UX Design Project", "Create user flows and wireframes", 200, cal.getTime());
            
            // Add some sample grades
            for (Seat seat : offer3.getSeatList()) {
                if (seat.isOccupied()) {
                    AssignmentGrade grade1 = offer3.getAssignmentGrade(a1, seat);
                    grade1.grade(88, "Nice design!");
                    
                    AssignmentGrade grade2 = offer3.getAssignmentGrade(a2, seat);
                    grade2.grade(175, "Excellent UX work!");
                }
            }
        }
        
        return business;
    }
}

