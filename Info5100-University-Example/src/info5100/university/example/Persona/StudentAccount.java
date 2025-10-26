/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info5100.university.example.Persona;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kal bugrara
 */
public class StudentAccount {
    private double balance;
    private List<PaymentRecord> paymentHistory;
    
    public StudentAccount() {
        this.balance = 0.0;
        this.paymentHistory = new ArrayList<>();
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void addCharge(double amount, String description) {
        if (amount > 0) {
            balance += amount;
            paymentHistory.add(new PaymentRecord(new Date(), description, amount, "Billed"));
        }
    }
    
    public boolean payTuition(double amount) {
        if (amount <= 0 || balance <= 0) {
            return false;
        }
        if (amount > balance) {
            amount = balance; 
        }
        balance -= amount;
        paymentHistory.add(new PaymentRecord(new Date(), "Tuition Payment", -amount, "Paid"));
        return true;
    }
    
    public void refund(double amount, String description) {
        if (amount > 0) {
            balance -= amount;
            paymentHistory.add(new PaymentRecord(new Date(), description, -amount, "Refund"));
        }
    }

    public List<PaymentRecord> getPaymentHistory() {
        return paymentHistory;
    }
    
    public static class PaymentRecord {
        private Date date;
        private String description;
        private double amount;
        private String type;

        public PaymentRecord(Date date, String description, double amount, String type) {
            this.date = date;
            this.description = description;
            this.amount = amount;
            this.type = type;
        }

        public Date getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }

        public double getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }
    }
}
