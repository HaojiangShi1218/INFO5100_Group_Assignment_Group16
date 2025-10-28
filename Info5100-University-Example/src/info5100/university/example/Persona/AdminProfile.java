/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import info5100.university.example.Department.Department;


/**
 *
 * @author kal bugrara
 */
public class AdminProfile extends Profile {
    private Person person;
    Department department;

    public AdminProfile(Person p) {

        super(p);
        person = p;

    }
    @Override
    public String getRole(){
        return  "Admin";
    }
    public boolean isMatch(String id) {
        return person.getPersonId().equals(id);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    

}