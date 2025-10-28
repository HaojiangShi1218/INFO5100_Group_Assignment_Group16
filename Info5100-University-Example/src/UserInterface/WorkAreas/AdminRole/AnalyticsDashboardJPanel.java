/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.WorkAreas.AdminRole;


import info5100.university.example.College.College;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.Seat;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.AdminDirectory;
import info5100.university.example.Persona.AdminProfile;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Faculty.UserAccountDirectory;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.Registrar.RegistrarDirectory;
import info5100.university.example.Persona.Registrar.RegistrarProfile;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import info5100.university.example.Persona.UserAccount;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kal bugrara
 */
public class AnalyticsDashboardJPanel extends javax.swing.JPanel {

    /**
     * Creates new form ManageSuppliersJPanel
     */
    JPanel CardSequencePanel;
    Department department;
    College college;
    //UserAccount selecteduseraccount;


    public AnalyticsDashboardJPanel(College cc, Department dd, JPanel jp) {
        CardSequencePanel = jp;
        college = cc;
        department = dd;
        initComponents();
        //populateRoleCombo();
        refreshTable();

    }

    public void refreshTable() {
        int tUsers = tblUsers.getRowCount();
        for (int i = tUsers - 1; i >= 0; i--) {
            ((DefaultTableModel) tblUsers.getModel()).removeRow(i);
        }
        
        int tCourses = tblCourses.getRowCount();
        for (int i = tCourses - 1; i >= 0; i--) {
            ((DefaultTableModel) tblCourses.getModel()).removeRow(i);
        }
        
        int tStudents = tblStudents.getRowCount();
        for (int i = tStudents - 1; i >= 0; i--) {
            ((DefaultTableModel) tblStudents.getModel()).removeRow(i);
        }
        
        int tRev = tblRev.getRowCount();
        for (int i = tRev - 1; i >= 0; i--) {
            ((DefaultTableModel) tblRev.getModel()).removeRow(i);
        }

        UserAccountDirectory uad = department.getUseraccountdirectory();
        CourseSchedule cs = department.getCourseSchedule("Fall2020");
        int totalRev = department.calculateRevenuesBySemester("Fall2020");
        
        int adminCount = 0;
        int facultyCount = 0;
        int studentCount = 0;
        int registrarCount = 0;
        for (UserAccount ua: uad.getUserAccountList()) {
            if (ua.getAssociatedPersonProfile() instanceof AdminProfile) {
                adminCount++;
            }
            if (ua.getAssociatedPersonProfile() instanceof FacultyProfile) {
                facultyCount++;
            }
            if (ua.getAssociatedPersonProfile() instanceof StudentProfile) {
                studentCount++;
            }
            if (ua.getAssociatedPersonProfile() instanceof RegistrarProfile) {
                registrarCount++;
            }
        }
        Object[] rowUsers = new Object[4];
        rowUsers[0] = adminCount;
        rowUsers[1] = facultyCount;
        rowUsers[2] = registrarCount;
        rowUsers[3] = studentCount;
        ((DefaultTableModel) tblUsers.getModel()).addRow(rowUsers);
        
        Object[] rowStudents1 = new Object[5];
        Object[] rowStudents2 = new Object[5];
        
        int j =0;
        for (CourseOffer co: cs.getSchedule()) {
            rowStudents1[j] = co.getCourseNumber();
            int enrolledStudents = 0;
            for (Seat s: co.getSeatlist()) {
                if(s.isOccupied()) {
                    enrolledStudents++;
                }
            }
            rowStudents2[j] = enrolledStudents;
            j++;
        }
        ((DefaultTableModel) tblStudents.getModel()).addRow(rowStudents1);
        ((DefaultTableModel) tblStudents.getModel()).addRow(rowStudents2);
        
        Object[] rowCourses = new Object[2];
        rowCourses[0] = cs.getSchedule().size();
        rowCourses[1] = "N/A";
        ((DefaultTableModel) tblCourses.getModel()).addRow(rowCourses);
        
        Object[] totalRevO = {totalRev};
        ((DefaultTableModel) tblRev.getModel()).addRow(totalRevO);
                
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        spCourses = new javax.swing.JScrollPane();
        tblCourses = new javax.swing.JTable();
        spUsers = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spStudents = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        spRev = new javax.swing.JScrollPane();
        tblRev = new javax.swing.JTable();

        setLayout(null);

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack);
        btnBack.setBounds(40, 20, 80, 23);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Total Courses Offered Per Semester");
        add(jLabel1);
        jLabel1.setBounds(30, 210, 270, 17);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setText("University Level Analytics Dashboard");
        add(jLabel2);
        jLabel2.setBounds(170, 20, 400, 28);

        tblCourses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Semester 1", "Semester 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCourses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCoursesMousePressed(evt);
            }
        });
        spCourses.setViewportView(tblCourses);

        add(spCourses);
        spCourses.setBounds(30, 230, 670, 90);

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Admin", "Faculty", "Registrar", "Student"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblUsersMousePressed(evt);
            }
        });
        spUsers.setViewportView(tblUsers);

        add(spUsers);
        spUsers.setBounds(30, 70, 670, 90);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Total Active Users by Role");
        add(jLabel3);
        jLabel3.setBounds(30, 50, 200, 17);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Total Enrolled Students Per Course");
        add(jLabel4);
        jLabel4.setBounds(30, 370, 270, 17);

        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course 1", "Course 2", "Course 3", "Course 4", "Course 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblStudentsMousePressed(evt);
            }
        });
        spStudents.setViewportView(tblStudents);
        if (tblStudents.getColumnModel().getColumnCount() > 0) {
            tblStudents.getColumnModel().getColumn(4).setHeaderValue("Course 5");
        }

        add(spStudents);
        spStudents.setBounds(30, 390, 670, 90);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Total Revenue Summary");
        add(jLabel5);
        jLabel5.setBounds(30, 530, 270, 17);

        tblRev.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Total Revenue"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblRevMousePressed(evt);
            }
        });
        spRev.setViewportView(tblRev);

        add(spRev);
        spRev.setBounds(30, 550, 670, 90);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).show(CardSequencePanel, "Admin");
        CardSequencePanel.revalidate();
        CardSequencePanel.repaint();

    }//GEN-LAST:event_btnBackActionPerformed

    private void tblCoursesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCoursesMousePressed

        
            
    }//GEN-LAST:event_tblCoursesMousePressed

    private void tblUsersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsersMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUsersMousePressed

    private void tblStudentsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentsMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStudentsMousePressed

    private void tblRevMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRevMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblRevMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane spCourses;
    private javax.swing.JScrollPane spRev;
    private javax.swing.JScrollPane spStudents;
    private javax.swing.JScrollPane spUsers;
    private javax.swing.JTable tblCourses;
    private javax.swing.JTable tblRev;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTable tblUsers;
    // End of variables declaration//GEN-END:variables
    
//    private void populateRoleCombo() {
//        cmbRole.removeAllItems();
//        cmbRole.addItem("Student");
//        cmbRole.addItem("Faculty");
//        cmbRole.addItem("Admin");
//    }
}

