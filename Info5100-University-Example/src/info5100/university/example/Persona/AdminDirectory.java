/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;


import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class AdminDirectory {

    ArrayList<AdminProfile> adminlist;

    public AdminDirectory() {

        adminlist = new ArrayList();

    }

    public AdminProfile newAdminProfile(Person p) {

        AdminProfile ap = new AdminProfile(p);
        adminlist.add(ap);
        return ap;
    }

    public AdminProfile findAdmin(String id) {

        for (AdminProfile ap : adminlist) {

            if (ap.isMatch(id)) {
                return ap;
            }
        }
            return null; //not found after going through the whole list
         }
    
}
