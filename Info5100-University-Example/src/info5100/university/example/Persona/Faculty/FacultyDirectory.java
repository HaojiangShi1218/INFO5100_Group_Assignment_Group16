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
public class FacultyDirectory {

    Department department;
    ArrayList<FacultyProfile> teacherlist;

    public FacultyDirectory(Department d) {

        department = d;
        teacherlist = new ArrayList();

    }
    
    public void deleteFaculty (FacultyProfile fp) {
        teacherlist.remove(fp);
    }
    
    public FacultyProfile newFacultyProfile(Person p) {

        FacultyProfile sp = new FacultyProfile(p, department);
        teacherlist.add(sp);
        return sp;
    }
    
    public FacultyProfile getTopProfessor(){
        
        double bestratingsofar = 0.0;
        FacultyProfile BestProfSofar = null;
        for(FacultyProfile fp: teacherlist)
           if(fp.getProfAverageOverallRating()>bestratingsofar){
           bestratingsofar = fp.getProfAverageOverallRating();
           BestProfSofar = fp;
           }
        return BestProfSofar;
        
    }
    
    public ArrayList<FacultyProfile> searchFacultyByName(String facultyName) {
        if (facultyName == null) {
            return null;
        }
        ArrayList<FacultyProfile> results = new ArrayList<>();
        for (FacultyProfile fp: teacherlist) {
            if (fp == null) {
                continue;
            }
            String name = fp.getPerson().getName();
            if (name != null && name.contains(facultyName)) {
                results.add(fp);
            }
        }
        return results;
    }

    public FacultyProfile findTeachingFaculty(String id) {

        for (FacultyProfile sp : teacherlist) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
            return null; //not found after going through the whole list
         }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    
    
    public Department getDepartment() {
        return department;
    }

    public ArrayList<FacultyProfile> getTeacherlist() {
        return teacherlist;
    }
    
    
}
