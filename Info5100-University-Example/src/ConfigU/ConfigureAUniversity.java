package ConfigU;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.AdminDirectory;
import info5100.university.example.Persona.AdminProfile;
import info5100.university.example.Persona.Faculty.UserAccountDirectory;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import info5100.university.example.Persona.UserAccount;


/**
 *
 * @author kal bugrara
 */
public class ConfigureAUniversity {

    public static Department initialize() {
//        Business business = new Business("Information Systems");
//
//// Create Persons
//      PersonDirectory persondirectory = business.getPersonDirectory();
//// person representing sales organization        
//        Person person001 = persondirectory.newPerson("John Smith");
//        Person person002 = persondirectory.newPerson("Gina Montana");
//        Person person003 = persondirectory.newPerson("Adam Rollen");
// 
//        Person person005 = persondirectory.newPerson("Jim Dellon");
//        Person person006 = persondirectory.newPerson("Anna Shnider");
//        Person person007 = persondirectory.newPerson("Laura Brown");
//        Person person008 = persondirectory.newPerson("Jack While");
//        Person person009 = persondirectory.newPerson("Fidelity"); //we use this as customer
//
//// Create Admins to manage the business
//        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
//        EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(person001);
//        
//        StudentDirectory studentdirectory = business.getStudentDirectory();
//        StudentProfile studentprofile0 = studentdirectory.newStudentProfile(person003);
        


   
// Create User accounts that link to specific profiles
//        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
//        UserAccount ua3 = uadirectory.newUserAccount(employeeprofile0, "admin", "****"); /// order products for one of the customers and performed by a sales person
//        UserAccount ua4 = uadirectory.newUserAccount(studentprofile0, "adam", "****"); /// order products for one of the customers and performed by a sales person
        
        
        Department department = new Department("Information Systems");
        CourseCatalog coursecatalog = department.getCourseCatalog();
        
        Course course = coursecatalog.newCourse("app eng", "info 5100", 4);
        
        CourseSchedule courseschedule = department.newCourseSchedule("Fall2020");

        CourseOffer courseoffer = courseschedule.newCourseOffer("info 5100");
        if (courseoffer==null) return null;
        courseoffer.generatSeats(10);
        PersonDirectory pd = department.getPersonDirectory();
        Person person = pd.newPerson("0112303");
        StudentDirectory sd = department.getStudentDirectory();
        StudentProfile student = sd.newStudentProfile(person);
        CourseLoad courseload = student.newCourseLoad("Fall2020"); 
//        
        courseload.newSeatAssignment(courseoffer); //register student in class
        
// person representing sales organization        
        Person person001 = pd.newPerson("John Smith");
        Person person002 = pd.newPerson("Gina Montana");
        Person person003 = pd.newPerson("Adam Rollen");
 
        Person person005 = pd.newPerson("Jim Dellon");
        Person person006 = pd.newPerson("Anna Shnider");
        Person person007 = pd.newPerson("Laura Brown");
        Person person008 = pd.newPerson("Jack While");
        Person person009 = pd.newPerson("Fidelity"); //we use this as customer
        Person person010 = pd.newPerson("Meeten Cider");
        
        AdminDirectory adminDirectory = department.getAdminDirectory();
        AdminProfile adminProfile0 = adminDirectory.newAdminProfile(person001);
        
        UserAccountDirectory uadirectory = department.getUseraccountdirectory();
        UserAccount ua3 = uadirectory.newUserAccount(adminProfile0, "admin", "****"); /// order products for one of the customers and performed by a sales person
        //UserAccount ua4 = uadirectory.newUserAccount(studentprofile0, "adam", "****"); 
        
        int total = department.calculateRevenuesBySemester("Fall2020");
        System.out.print("Total: " + total);
        return department;
    }

}
