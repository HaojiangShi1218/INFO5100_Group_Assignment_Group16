/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info5100.university.example.Persona.Registrar;

import info5100.university.example.Persona.Person;
/**
 *
 * @author xuanliliu
 */


public class RegistrarProfile {
    private final Person person;
    private String email;
    private String phone;
    private String officeHours;
    private String department;

    public RegistrarProfile(Person person) {
        if (person == null) throw new IllegalArgumentException("person cannot be null");
        this.person = person;
    }

    public Person getPerson() { return person; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getOfficeHours() { return officeHours; }
    public void setOfficeHours(String officeHours) { this.officeHours = officeHours; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "RegistrarProfile{" + person + "}";
    }
}
