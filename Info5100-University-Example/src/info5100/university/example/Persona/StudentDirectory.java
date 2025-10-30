/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import info5100.university.example.Department.Department;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class StudentDirectory {

    Department department;
    ArrayList<StudentProfile> studentlist;

    public StudentDirectory(Department d) {

        department = d;
        studentlist = new ArrayList();

    }

    public StudentProfile newStudentProfile(Person p) {

        StudentProfile sp = new StudentProfile(p, department);
        studentlist.add(sp);
        return sp;
    }
    
    public void deleteStudent(StudentProfile sp) {
        studentlist.remove(sp);
    }
    
    public ArrayList<StudentProfile> searchStudentByName(String studentName) {
        if (studentName == null) {
            return null;
        }
        ArrayList<StudentProfile> results = new ArrayList<>();
        for (StudentProfile sp: studentlist) {
            if (sp == null) {
                continue;
            }
            String name = sp.getPerson().getName();
            if (name != null && name.contains(studentName)) {
                results.add(sp);
            }
        }
        return results;
    }

    public StudentProfile findStudentID(String id) {

        for (StudentProfile sp : studentlist) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
            return null; //not found after going through the whole list
         }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ArrayList<StudentProfile> getStudentlist() {
        return studentlist;
    }

    public void setStudentlist(ArrayList<StudentProfile> studentlist) {
        this.studentlist = studentlist;
    }
    
    
}
