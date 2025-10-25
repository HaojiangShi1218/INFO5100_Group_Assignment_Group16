/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona.Faculty;

import info5100.university.example.Persona.*;
import info5100.university.example.Department.Department;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class UserAccountDirectory {

    Department department;
    ArrayList<UserAccount> studentlist;

    public UserAccountDirectory(Department d) {
        department = d;
        studentlist = new ArrayList();
    }
    
    // New constructor for use without Department
    public UserAccountDirectory() {
        department = null;
        studentlist = new ArrayList();
    }

    public UserAccount newUserAccount(Person p) {
        // Create a default user account for Person
        UserAccount sp = new UserAccount(p.getPersonId(), "password", "USER");
        sp.setPerson(p);
        studentlist.add(sp);
        return sp;
    }
    
    // New method to create user account with username, password, role
    public UserAccount newUserAccount(String username, String password, String role) {
        UserAccount ua = new UserAccount(username, password, role);
        studentlist.add(ua);
        return ua;
    }

    public UserAccount findStudent(String id) {
        for (UserAccount sp : studentlist) {
         //   if (sp.isMatch(id)) {
         //       return sp;
         //   }
        }
        return null; //not found after going through the whole list
    }
    
    // Authenticate user
    public UserAccount authenticateUser(String username, String password) {
        for (UserAccount account : studentlist) {
            if (account.authenticate(username, password)) {
                return account;
            }
        }
        return null;
    }
}
