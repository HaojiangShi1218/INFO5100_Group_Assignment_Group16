/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.EmploymentHistory.EmploymentHistroy;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class StudentProfile extends Profile {

    Person person;
    Department department;
    Transcript transcript;
    EmploymentHistroy employmenthistory;

    public StudentProfile(Person p, Department d) {
        super(p);
        person = p;
        department = d;
        transcript = new Transcript(this);
        employmenthistory = new EmploymentHistroy();
    }

//    public boolean isMatch(String id) {
//        return person.getPersonId().equals(id);
//    }

    public Person getPerson() {
        return person;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public CourseLoad getCourseLoadBySemester(String semester) {

        return transcript.getCourseLoadBySemester(semester);
    }

    public CourseLoad getCurrentCourseLoad() {

        return transcript.getCurrentCourseLoad();
    }

    public CourseLoad newCourseLoad(String s) {

        return transcript.newCourseLoad(s);
    }

    public ArrayList<SeatAssignment> getCourseList() {

        return transcript.getCourseList();

    }
    
    public ArrayList<String> getAllTerms() {
        return transcript.getAllTerms();
    }
    
    public ArrayList<SeatAssignment> getCoursesByTerm(String term) {
        return transcript.getCoursesByTerm(term);
    }
    
    public ArrayList<SeatAssignment> getAllSeatAssignments() {
        return transcript.getCourseList();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    
    
    @Override
    public String getRole() {
        return "Faculty";
    }

    public boolean isMatch(String id) {
        return person.getPersonId().equals(id);
    }
}
