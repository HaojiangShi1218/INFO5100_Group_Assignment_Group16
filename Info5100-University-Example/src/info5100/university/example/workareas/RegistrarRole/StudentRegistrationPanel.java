/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package info5100.university.example.workareas.RegistrarRole;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import info5100.university.example.workareas.RegistrarRole.shared.RegistrarDataStore;



/**
 *
 * @author xuanliliu
 */
public class StudentRegistrationPanel extends javax.swing.JPanel {
        
        // ===== In-memory state =====
//private final java.util.Map<String, Integer> capacityByCourse   = new java.util.HashMap<>();
//private final java.util.Map<String, Integer> enrolledByCourse   = new java.util.HashMap<>();
//private final java.util.Map<String, java.util.Set<String>> courseToStudents = new java.util.HashMap<>();
//private final java.util.Map<String, java.util.Set<String>> studentToCourses = new java.util.HashMap<>();

// 原始学生表数据（用于搜索过滤时恢复）
//private final java.util.List<Object[]> allStudents = new java.util.ArrayList<>();

    private final java.util.List<Object[]> allStudents = new java.util.ArrayList<Object[]>();
    private final RegistrarDataStore ds = RegistrarDataStore.getInstance();


    /**
     * Creates new form StudentRegistrationPanel
     */
    public StudentRegistrationPanel() {
        initComponents();
        // 初始化学生表（可用你已有的数据替换）
DefaultTableModel ms = (DefaultTableModel) tblStudents.getModel();
ms.setRowCount(0);
ms.setColumnIdentifiers(new String[]{"Student ID","Name"});
Object[][] seedStudents = {
    {"S10001","Alice Wang"},
    {"S10002","Bob Kim"},
    {"S10003","Carol Li"}
};
for (Object[] row : seedStudents) {
    ms.addRow(row);
    allStudents.add(row); // 保存原始数据用于搜索恢复
}
tblStudents.setDefaultEditor(Object.class, null);
tblCourseList.setDefaultEditor(Object.class, null);
tblStudents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
tblCourseList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

// 种子课程（只在 DataStore 为空时注入一次）
ds.seedDemoDataIfEmpty();

// 课程表从 DataStore 读
DefaultTableModel mo = (DefaultTableModel) tblCourseList.getModel();
mo.setRowCount(0);
mo.setColumnIdentifiers(new String[]{"Course ID","Title","Term","Enrolled"});
for (RegistrarDataStore.Offering o : ds.getAllOfferings()) {
    mo.addRow(new Object[]{o.courseId, o.title, o.term, o.enrolled});
}

//// 初始化课程表
//DefaultTableModel mo = (DefaultTableModel) tblCourseList.getModel();
//mo.setRowCount(0);
//mo.setColumnIdentifiers(new String[]{"Course ID","Title","Term","Enrolled"});
//mo.addRow(new Object[]{"INFO 5100","Application Engineering","Fall 2025",18});
//mo.addRow(new Object[]{"INFO 5600","DBMS","Fall 2025",35});
//mo.addRow(new Object[]{"INFO 6205","Program Design Paradigm","Spring 2026",9});
//
//// 容量 & 当前已选（与表格中的 Enrolled 对齐）
//capacityByCourse.put("INFO 5100", 30);
//capacityByCourse.put("INFO 5600", 35);
//capacityByCourse.put("INFO 6205", 25);
//
//enrolledByCourse.put("INFO 5100", 18);
//enrolledByCourse.put("INFO 5600", 35);
//enrolledByCourse.put("INFO 6205", 9);
// 种子数据（只在 DataStore 为空时填一次）

// ... 你原来的构造器末尾
this.addComponentListener(new java.awt.event.ComponentAdapter() {
    @Override
    public void componentShown(java.awt.event.ComponentEvent e) {
        reloadCoursesFromDataStore();
    }
});

    }
    
    private String getSelectedStudentId() {
    int r = tblStudents.getSelectedRow();
    if (r < 0) return null;
    Object v = tblStudents.getValueAt(r, 0);
    return v == null ? null : v.toString();
}

private String getSelectedCourseId() {
    int r = tblCourseList.getSelectedRow();
    if (r < 0) return null;
    Object v = tblCourseList.getValueAt(r, 0);
    return v == null ? null : v.toString();
}

//private int getCapacity(String courseId) {
//    return capacityByCourse.getOrDefault(courseId, 0);
//}
//private int getEnrolled(String courseId) {
//    return enrolledByCourse.getOrDefault(courseId, 0);
//}
//private void setEnrolled(String courseId, int val) {
//    enrolledByCourse.put(courseId, Math.max(val, 0));
//    // 同步到表格 Enrolled 列
//    DefaultTableModel m = (DefaultTableModel) tblCourseList.getModel();
//    for (int i=0;i<m.getRowCount();i++){
//        if (courseId.equals(String.valueOf(m.getValueAt(i,0)))) {
//            m.setValueAt(val, i, 3); // 第4列 Enrolled
//            break;
//        }
//    }
//}
//
//private java.util.Set<String> studentSetForCourse(String courseId) {
//    return courseToStudents.computeIfAbsent(courseId, k -> new java.util.HashSet<>());
//}
//private java.util.Set<String> courseSetForStudent(String studentId) {
//    return studentToCourses.computeIfAbsent(studentId, k -> new java.util.HashSet<>());
//}

private void info(String s){ JOptionPane.showMessageDialog(this, s, "Info", JOptionPane.INFORMATION_MESSAGE); }
private void warn(String s){ JOptionPane.showMessageDialog(this, s, "Warning", JOptionPane.WARNING_MESSAGE); }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    private void reloadCoursesFromDataStore() {
    DefaultTableModel mo = (DefaultTableModel) tblCourseList.getModel();
    mo.setRowCount(0);
    mo.setColumnIdentifiers(new String[]{"Course ID","Title","Term","Enrolled"});
    for (RegistrarDataStore.Offering o : ds.getAllOfferings()) {
        mo.addRow(new Object[]{o.courseId, o.title, o.term, o.enrolled});
    }
}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearchStudent = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnEnroll = new javax.swing.JButton();
        btnDrop = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCourseList = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Search Student By ID/Name");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnEnroll.setText("Enroll");
        btnEnroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnrollActionPerformed(evt);
            }
        });

        btnDrop.setText("Drop");
        btnDrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDropActionPerformed(evt);
            }
        });

        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(btnEnroll)
                .addGap(32, 32, 32)
                .addComponent(btnReload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(btnDrop))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearchStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnroll)
                    .addComponent(btnDrop)
                    .addComponent(btnReload)))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setResizeWeight(0.5);

        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Student ID", "Name"
            }
        ));
        jScrollPane1.setViewportView(tblStudents);

        jSplitPane1.setLeftComponent(jScrollPane1);

        tblCourseList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Course ID", "Title", "Term", "Enrolled"
            }
        ));
        jScrollPane2.setViewportView(tblCourseList);

        jSplitPane1.setRightComponent(jScrollPane2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnrollActionPerformed
        // TODO add your handling code here:
        String sid = getSelectedStudentId();
String cid = getSelectedCourseId();
if (sid == null) { warn("Please select a student in the left table."); return; }
if (cid == null) { warn("Please select a course in the right table."); return; }

String r = ds.enroll(sid, cid);
if (!"OK".equals(r)) {
    warn(r);
    return;
}

// 成功后刷新右表该课程的 Enrolled 显示
DefaultTableModel m = (DefaultTableModel) tblCourseList.getModel();
for (int i=0; i<m.getRowCount(); i++) {
    if (cid.equals(String.valueOf(m.getValueAt(i, 0)))) {
        RegistrarDataStore.Offering o = ds.getOfferingById(cid);
        m.setValueAt(o.enrolled, i, 3);
        break;
    }
}
info("Enrolled student " + sid + " to " + cid + " successfully.");

    }//GEN-LAST:event_btnEnrollActionPerformed

    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDropActionPerformed
       String sid = getSelectedStudentId();
String cid = getSelectedCourseId();
if (sid == null) { warn("Please select a student in the left table."); return; }
if (cid == null) { warn("Please select a course in the right table."); return; }

String r = ds.drop(sid, cid);
if (!"OK".equals(r)) {
    warn(r);
    return;
}

// 刷新右表人数
DefaultTableModel m = (DefaultTableModel) tblCourseList.getModel();
for (int i=0; i<m.getRowCount(); i++) {
    if (cid.equals(String.valueOf(m.getValueAt(i, 0)))) {
        RegistrarDataStore.Offering o = ds.getOfferingById(cid);
        m.setValueAt(o.enrolled, i, 3);
        break;
    }
}
info("Dropped student " + sid + " from " + cid + ".");

    }//GEN-LAST:event_btnDropActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String q = txtSearchStudent.getText();
    DefaultTableModel m = (DefaultTableModel) tblStudents.getModel();
    m.setRowCount(0);
    if (q == null || q.trim().isEmpty()) {
        // 为空 → 恢复所有
        for (Object[] r : allStudents) m.addRow(r);
        return;
    }
    String needle = q.trim().toLowerCase();
    for (Object[] r : allStudents) {
        String id = String.valueOf(r[0]).toLowerCase();
        String name = String.valueOf(r[1]).toLowerCase();
        if (id.contains(needle) || name.contains(needle)) {
            m.addRow(r);
        }
    }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        // TODO add your handling code here:
        reloadCoursesFromDataStore();
    }//GEN-LAST:event_btnReloadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDrop;
    private javax.swing.JButton btnEnroll;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tblCourseList;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField txtSearchStudent;
    // End of variables declaration//GEN-END:variables
}
