/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseCatalog;

/**
 *
 * @author kal bugrara
 */
public class Course {

    String number;
    String name;
    int credits;
    int price = 1500; //per credit hour
    String description;
    String prerequisites;

    public Course(String n, String numb, int ch) {
        name = n;
        number = numb;
        credits = ch;
        description = "";
        prerequisites = "";
    }

    public String getCOurseNumber() {
        return number;
    }

    public int getCoursePrice() {
        return price * credits;

    }

    public int getCredits() {
        return credits;
    }
    
    public String getCourseNumber() {
        return number;
    }
    
    public String getTitle() {
        return name;
    }
    
    public String getPrerequisites() {
        return prerequisites != null ? prerequisites : "";
    }
    
    public String getDescription() {
        return description != null ? description : "";
    }
    
    public void setTitle(String name) {
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }
}