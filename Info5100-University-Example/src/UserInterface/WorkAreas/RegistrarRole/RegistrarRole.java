/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info5100.university.example.workareas.RegistrarRole;
import info5100.university.example.workareas.Workarea;
import javax.swing.JPanel;
/**
 *
 * @author xuanliliu
 */
public class RegistrarRole {
    public JPanel createWorkArea(JPanel container /* 可加需要的依赖 */) {
        return new RegistrarWorkAreaPanel();
    }
    @Override
    public String toString() { return "RegistrarRole"; }
}