/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import info5100.university.example.Persona.Registrar.RegistrarDirectory;
import info5100.university.example.Persona.Registrar.RegistrarProfile;

/**
 *
 * @author kal bugrara
 */
public class Info5001UniversityExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Department department = new Department("Information Systems");
        CourseCatalog coursecatalog = department.getCourseCatalog();
        
        Course course = coursecatalog.newCourse("app eng", "info 5100", 4);
        
        CourseSchedule courseschedule = department.newCourseSchedule("Fall2020");

        CourseOffer courseoffer = courseschedule.newCourseOffer("info 5100");
        if (courseoffer==null)return;
        courseoffer.generatSeats(10);
        PersonDirectory pd = department.getPersonDirectory();
        Person person = pd.newPerson("0112303");
        StudentDirectory sd = department.getStudentDirectory();
        StudentProfile student = sd.newStudentProfile(person);
        CourseLoad courseload = student.newCourseLoad("Fall2020"); 
//        
        courseload.newSeatAssignment(courseoffer); //register student in class
        
        int total = department.calculateRevenuesBySemester("Fall2020");
        System.out.print("Total: " + total);
        
        
        // ---------------- Registrar seed (minimal) ----------------
// 放在 main(...) 里你之前的示例逻辑之后（打印 total 之前或之后均可）：
RegistrarDirectory registrarDirectory = new RegistrarDirectory();

// 1) 创建 Registrar 的 Person（用现有 PersonDirectory）
Person regPerson = pd.newPerson("REG-0001");  // Person 只有 id

// 2) 建立 Registrar 档案
RegistrarProfile rp = registrarDirectory.newRegistrar(regPerson);
rp.setEmail("registrar@example.edu");
rp.setPhone("617-000-0000");
rp.setOfficeHours("Mon 10:00-12:00");
rp.setDepartment("Registrar Office");

// 3) 打印验证（使用 getPersonId()，不要使用 setName）
System.out.println("\nSeeded Registrar: " + regPerson.getPersonId() + " / " + rp.getEmail());


// ---- TEMP preview: open Registrar UI without login ----
javax.swing.SwingUtilities.invokeLater(() -> {
    javax.swing.JFrame f = new javax.swing.JFrame("Registrar — Work Area Preview");
    f.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    f.setContentPane(new info5100.university.example.workareas.RegistrarRole.RegistrarWorkAreaPanel());
    f.setSize(1000, 650);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
});
// -------------------------------------------------------



    }

}
