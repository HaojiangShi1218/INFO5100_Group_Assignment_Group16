/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info5100.university.example.Persona.Registrar;


import info5100.university.example.Persona.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author xuanliliu
 */

public class RegistrarDirectory {
    private final List<RegistrarProfile> list = new ArrayList<>();

    public RegistrarProfile newRegistrar(Person p) {
        RegistrarProfile rp = new RegistrarProfile(p);
        list.add(rp);
        return rp;
    }
    
    public void deleteRegistrar (RegistrarProfile rp) {
        list.remove(rp);
    }
    
    public RegistrarProfile findByEmail(String email) {
        if (email == null) return null;
        for (RegistrarProfile r : list) {
            if (email.equalsIgnoreCase(r.getPerson().getEmail())) return r;
        }
        return null;
    }
    
    public RegistrarProfile findRegistrarID(String id) {

        for (RegistrarProfile sp : list) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
            return null; //not found after going through the whole list
         }

    public List<RegistrarProfile> getRegistrarList() {
        return Collections.unmodifiableList(list);
    }
}
