/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.College;

import info5100.university.example.Department.Department;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class College {
    ArrayList<Department> departments;
    
    public College(String name){
        departments = new ArrayList();
    }
    
    public Department newDepartment(String  name) {

        Department dept = new Department(name);
        departments.add(dept);
        return dept;
    }

    public Department findDepartment(String name) {

        for (Department dept : departments) {

            if (dept.isMatch(name)) {
                return dept;
            }
        }
            return null; //not found after going through the whole list
         }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }
    
    
}
