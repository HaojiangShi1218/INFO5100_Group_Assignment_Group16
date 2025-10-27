/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Employer;

import info5100.university.example.Persona.EmploymentHistory.Employment;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.Profile;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class EmployerProfile extends Profile {
//    String name;
    ArrayList<Employment> employments;
    
    public EmployerProfile(Person p) {

        super(p); 

    }
    
//    public boolean isMatch(String id){
//        if(name.equals(id)) return true;             //String is an object and can do equal matach
//        return false;
//    }
         
    @Override
    public String getRole(){
        return  "Employer";
    }
    
}
