/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;

/**
 *
 * @author kal bugrara
 */
public class SeatAssignment {
    float grade; //(Letter grade mappings: A=4.0, A-=3.7, B+=3.3, B=3.0, )
    Seat seat;
    boolean like; //true means like and false means not like
    CourseLoad courseload;
    
    public SeatAssignment(CourseLoad cl, Seat s){
        seat = s;
        courseload = cl;
    }
     
    public boolean getLike(){
        return like;
    }
    public void assignSeatToStudent(CourseLoad cl){
        courseload = cl;
    }
    
    public int getCreditHours(){
        return seat.getCourseCredits();
       
    }
    public Seat getSeat(){
        return seat;
    }
    public CourseOffer getCourseOffer(){
        
        return seat.getCourseOffer();
    }
    public Course getAssociatedCourse(){
        
        return getCourseOffer().getSubjectCourse();
    }
    public float GetCourseStudentScore(){
        return getCreditHours()*grade;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
    
    public String getGradeLetter() {
        float g = this.grade;
        if (g == 0) return "N/A";
        if (g >= 3.85) return "A";
        if (g >= 3.5)  return "A-";
        if (g >= 3.15) return "B+";
        if (g >= 2.85) return "B";
        if (g >= 2.5)  return "B-";
        if (g >= 2.15) return "C+";
        if (g >= 1.85) return "C";
        if (g >= 1.5)  return "C-";
        return "F";
    }
    
    
    
}
