/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author kal bugrara
 */

public class Person {
    
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private static final Set<String> ISSUED =
            Collections.synchronizedSet(new HashSet<>());
    
    public Person() {
        this.id = generateUnique7DigitId();
    }
    
    public Person(String id) {
        setPersonId(id); // validates and reserves
    }
    
    public void setPersonId(String id) {
        if (id == null || !id.matches("\\d{7}")) {
            throw new IllegalArgumentException("ID must be exactly 7 digits");
        }
        synchronized (ISSUED) {
            // free previous id if present
            if (this.id != null) {
                ISSUED.remove(this.id);
            }
            if (!ISSUED.add(id)) {
                throw new IllegalArgumentException("ID already in use: " + id);
            }
            this.id = id;
        }
    }
    
    public void releaseId() {
        synchronized (ISSUED) {
            if (this.id != null) {
                ISSUED.remove(this.id);
                this.id = null;
            }
        }
    }
    
    private static String generateUnique7DigitId() {
        // 9,000,000 possibilities; retry a few times in the rare case of collision.
        for (int attempts = 0; attempts < 100; attempts++) {
            int n = ThreadLocalRandom.current().nextInt(1_000_000, 10_000_000);
            String candidate = Integer.toString(n);
            if (ISSUED.add(candidate)) {
                return candidate;
            }
        }
        throw new IllegalStateException("Could not allocate unique 7-digit ID after retries");
    }

    public String getPersonId() {
        return id;
    }

    public boolean isMatch(String id) {
        return this.id != null && this.id.equals(id);
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