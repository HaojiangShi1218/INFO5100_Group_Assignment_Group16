/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.StudentProfile;
import info5100.university.example.workareas.Workarea;

/**
 *
 * @author kal bugrara
 */
public class UserAccount {
    Profile profile;
    String username;
    String password;
    
    public UserAccount (Profile profile, String un, String pw){
        username = un;
        password = pw;
        this.profile = profile;
    }

    public String getPersonId(){
        return profile.getPerson().getPersonId();
    }
    
    public String getUserLoginName(){
        return username;
    }

    public boolean isMatch(String id){
        if(getPersonId().equals(id)) return true;
        return false;
    }
    
    public boolean IsValidUser(String un, String pw){
        if (username.equalsIgnoreCase(un) && password.equals(pw)) return true;
        else return false;
    }
    
    public String getRole(){
        return profile.getRole();
    }
    
    public Profile getAssociatedPersonProfile(){
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public Person getPerson() {
        return profile.getPerson();
    }
    
    public FacultyProfile getFacultyProfile() {
        if (profile instanceof FacultyProfile) {
            return (FacultyProfile) profile;
        }
        return null;
    }
    
    public StudentProfile getStudentProfile() {
        if (profile instanceof StudentProfile) {
            return (StudentProfile) profile;
        }
        return null;
    }
    
    public AdminProfile getAdminProfile() {
        if (profile instanceof AdminProfile) {
            return (AdminProfile) profile;
        }
        return null;
    }
    
    public Profile getProfile() {
        return profile;
    }
    
    @Override
    public String toString(){
        return getUserLoginName();
    }
}
