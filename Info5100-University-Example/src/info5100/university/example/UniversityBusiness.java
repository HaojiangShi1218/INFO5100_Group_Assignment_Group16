package info5100.university.example;

import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.Faculty.UserAccountDirectory;
import java.util.ArrayList;

/**
 * Central business object that manages all university entities
 */
public class UniversityBusiness {
    
    private String universityName;
    private PersonDirectory personDirectory;
    private UserAccountDirectory userAccountDirectory;
    private StudentDirectory studentDirectory;
    private FacultyDirectory facultyDirectory;
    private Department department;
    
    public UniversityBusiness(String name) {
        this.universityName = name;
        this.personDirectory = new PersonDirectory();
        this.userAccountDirectory = new UserAccountDirectory();
        this.studentDirectory = new StudentDirectory();
        this.facultyDirectory = new FacultyDirectory();
        this.department = new Department("Computer Science");
    }
    
    /**
     * Constructor that uses an existing department
     * @param department existing department from College
     */
    public UniversityBusiness(Department department) {
        this.universityName = "University";
        this.department = department;
        this.personDirectory = department.getPersonDirectory();
        this.userAccountDirectory = department.getUseraccountdirectory();
        this.studentDirectory = department.getStudentDirectory();
        this.facultyDirectory = department.getFacultyDirectory();
    }
    
    public String getUniversityName() {
        return universityName;
    }
    
    public PersonDirectory getPersonDirectory() {
        return personDirectory;
    }
    
    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }
    
    public StudentDirectory getStudentDirectory() {
        return studentDirectory;
    }
    
    public FacultyDirectory getFacultyDirectory() {
        return facultyDirectory;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public CourseCatalog getCourseCatalog() {
        return department.getCourseCatalog();
    }
    
    public CourseSchedule getCourseSchedule(String semester) {
        return department.getCourseSchedule(semester);
    }
    
    public CourseSchedule newCourseSchedule(String semester) {
        return department.newCourseSchedule(semester);
    }
    
    public ArrayList<CourseSchedule> getCourseSchedules() {
        return department.getCourseSchedules();
    }
}

