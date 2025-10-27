/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

/**
 *
 * @author kal bugrara
 */
public class Person {
    
    String id;
    String name;
    String email;
    String phone;
    String address;
    
    public Person() {
        // Default constructor
    }
    
    public Person (String id){
        this.id = id;
    }
    
    public void setPersonId(String id) {
        this.id = id;
    }
    
    public String getPersonId(){
        return id;
    }
    
    public String getId() {
        return id;
    }

    public boolean isMatch(String id){
        if(getPersonId().equals(id)) return true;
        return false;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return getPersonId();
    }
    
}
