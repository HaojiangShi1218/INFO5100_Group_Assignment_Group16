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
public class FinancialReconciliationPanel extends javax.swing.JPanel {
    
    private final RegistrarDataStore ds = RegistrarDataStore.getInstance();

    
//    public static class PaymentRecord {
//    public final String term;
//    public final String dept;
//    public final String studentId;
//    public final String courseId;
//    public final int amount;   // fee
//    public boolean paid;
//
//    public PaymentRecord(String term, String dept, String studentId, String courseId, int amount, boolean paid){
//        this.term = term; this.dept = dept; this.studentId = studentId;
//        this.courseId = courseId; this.amount = amount; this.paid = paid;
//    }
//}
//    private final java.util.List<PaymentRecord> ledger = new java.util.ArrayList<>();
    //public java.util.List<PaymentRecord> getLedger() { return java.util.Collections.unmodifiableList(ledger); }
    private void info(String s){ javax.swing.JOptionPane.showMessageDialog(this, s, "Info", javax.swing.JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String s){ javax.swing.JOptionPane.showMessageDialog(this, s, "Warning", javax.swing.JOptionPane.WARNING_MESSAGE); }
    /**
     * Creates new form FinancialReconciliationPanel
     */
    public FinancialReconciliationPanel() {
        initComponents();
        reloadTermsFromDataStore();
        if (cmbTerm.getItemCount() == 0) 
        {
        cmbTerm.addItem("Fall 2025");
        cmbTerm.addItem("Spring 2026");
        }
        recalcAll();
    }
    
    private void refreshTuitionSummary() {
    java.util.Map<String, int[]> map = new java.util.HashMap<>(); // term -> [collected, unpaid]
    for (RegistrarDataStore.PaymentRecord pr : ds.getLedger()) {
        int[] arr = map.computeIfAbsent(pr.term, k -> new int[2]);
        if (pr.paid) arr[0] += pr.amount; else arr[1] += pr.amount;
    }
    DefaultTableModel m = (DefaultTableModel) tblTuitionSummary.getModel();
    m.setRowCount(0);
    m.setColumnIdentifiers(new String[]{"Term", "Total Collected", "Total Unpaid"});
    for (java.util.Map.Entry<String, int[]> e : map.entrySet()) {
        m.addRow(new Object[]{e.getKey(), e.getValue()[0], e.getValue()[1]});
    }
}

// 汇总：按院系
    private void refreshRevenueByDept() {
    class Agg { int collected, unpaidCnt, unpaidAmt; }
    java.util.Map<String, Agg> map = new java.util.HashMap<>(); // term#dept -> agg
    for (RegistrarDataStore.PaymentRecord pr : ds.getLedger()) {
        String key = pr.term + "#" + pr.dept;
        Agg a = map.computeIfAbsent(key, k -> new Agg());
        if (pr.paid) a.collected += pr.amount;
        else { a.unpaidCnt++; a.unpaidAmt += pr.amount; }
    }
    DefaultTableModel m = (DefaultTableModel) tblRevenueByDept.getModel();
    m.setRowCount(0);
    m.setColumnIdentifiers(new String[]{"Term","Department","Collected","Unpaid Count","Unpaid Amount"});
    for (java.util.Map.Entry<String, Agg> e : map.entrySet()) {
        String key = e.getKey();
        int p = key.indexOf('#');
        m.addRow(new Object[]{ key.substring(0,p), key.substring(p+1),
                               e.getValue().collected, e.getValue().unpaidCnt, e.getValue().unpaidAmt });
    }
}


private void recalcAll() {
    refreshTuitionSummary();
    refreshRevenueByDept();
}
//public void markAllPaidByTerm(String term) {
//    for (PaymentRecord pr : ledger) if (term.equals(pr.term)) pr.paid = true;
//    touch(); // 若你实现了 version 机制
//}
//public String enroll(String studentId, String courseId){
//    Offering o = offerings.get(courseId);
//    if (o == null) return "Course not found.";
//    if (!o.open) return "Course " + courseId + " is closed.";
//    if (o.enrolled >= o.capacity) return "Course " + courseId + " is full.";
//
//    // 去重：同一学生不能重复选同一课
//    java.util.Set<String> set = studentToCourses.computeIfAbsent(studentId, k -> new java.util.HashSet<>());
//    if (!set.add(courseId)) return "Student already enrolled.";
//
//    courseToStudents.computeIfAbsent(courseId, k -> new java.util.HashSet<>()).add(studentId);
//    o.enrolled++;
//
//    // —— 生成账单（未支付）
//    ledger.add(new PaymentRecord(o.term, o.dept, studentId, courseId, o.fee, false));
//
//    touch();
//    return "OK";
//}
//
//public String drop(String studentId, String courseId){
//    Offering o = offerings.get(courseId);
//    if (o == null) return "Course not found.";
//
//    java.util.Set<String> cs = studentToCourses.getOrDefault(studentId, java.util.Collections.emptySet());
//    if (!cs.contains(courseId)) return "Student not enrolled.";
//
//    cs.remove(courseId);
//    java.util.Set<String> ss = courseToStudents.getOrDefault(courseId, java.util.Collections.emptySet());
//    ss.remove(studentId);
//    if (o.enrolled > 0) o.enrolled--;
//
//    // —— 删除该学生这门课对应的未支付账单（简单策略：删第一条匹配未支付即可）
//    for (java.util.Iterator<PaymentRecord> it = ledger.iterator(); it.hasNext(); ) {
//        PaymentRecord pr = it.next();
//        if (!pr.paid && pr.studentId.equals(studentId) && pr.courseId.equals(courseId)) {
//            it.remove();
//            break;
//        }
//    }
//
//    touch();
//    return "OK";
//}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbTerm = new javax.swing.JComboBox<>();
        btnLoadFinance = new javax.swing.JButton();
        btnRecalc = new javax.swing.JButton();
        btnMarkAllPaid = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTuitionSummary = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRevenueByDept = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Fall 2025");

        jLabel2.setText("Term");

        cmbTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fall 2025", "Spring 2026" }));

        btnLoadFinance.setText("Load Finance Data");
        btnLoadFinance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadFinanceActionPerformed(evt);
            }
        });

        btnRecalc.setText("Recalculate");
        btnRecalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecalcActionPerformed(evt);
            }
        });

        btnMarkAllPaid.setText("Mark All Paid");
        btnMarkAllPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarkAllPaidActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(btnLoadFinance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(btnRecalc)
                .addGap(72, 72, 72)
                .addComponent(btnMarkAllPaid)
                .addGap(37, 37, 37))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadFinance)
                    .addComponent(btnRecalc)
                    .addComponent(btnMarkAllPaid)))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tblTuitionSummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Term", "Total", "Collected", "Total Unpaid"
            }
        ));
        jScrollPane1.setViewportView(tblTuitionSummary);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tution Summary", jPanel2);

        tblRevenueByDept.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Term", "Department", "Collected", "Unpaid Count", "Unpaid Amount"
            }
        ));
        jScrollPane2.setViewportView(tblRevenueByDept);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Revenue by Department", jPanel3);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadFinanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadFinanceActionPerformed
        // TODO add your handling code here:
//        ledger.clear();
//    // —— 示例账单：你可按需增删 —— 
//    // Fall 2025 / IS
//    ledger.add(new PaymentRecord("Fall 2025","Information Systems","S10001","INFO 5100", 3000, true));
//    ledger.add(new PaymentRecord("Fall 2025","Information Systems","S10002","INFO 5100", 3000, false));
//    ledger.add(new PaymentRecord("Fall 2025","Information Systems","S10003","INFO 5600", 3200, true));
//    // Fall 2025 / CS
//    ledger.add(new PaymentRecord("Fall 2025","Computer Science","S20001","CS 5001", 2800, false));
//    ledger.add(new PaymentRecord("Fall 2025","Computer Science","S20002","CS 5002", 2800, true));
//    // Spring 2026 / IS
//    ledger.add(new PaymentRecord("Spring 2026","Information Systems","S10001","INFO 6205", 3100, false));

    reloadTermsFromDataStore();  // ← 防止你刚在 Offering 加了新 Term
    recalcAll();
    info("Loaded finance snapshot from DataStore.");
    }//GEN-LAST:event_btnLoadFinanceActionPerformed

    private void btnRecalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecalcActionPerformed
        // TODO add your handling code here:
        recalcAll();
    }//GEN-LAST:event_btnRecalcActionPerformed

    private void btnMarkAllPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkAllPaidActionPerformed
        // TODO add your handling code here:
        Object sel = cmbTerm.getSelectedItem();
    if (sel == null) { warn("Please choose a term."); return; }
    String term = sel.toString();
    ds.markAllPaidByTerm(term);
    recalcAll();
    info("Marked all records as PAID for term: " + term);
    }//GEN-LAST:event_btnMarkAllPaidActionPerformed
    private void reloadTermsFromDataStore() {
    javax.swing.DefaultComboBoxModel<String> model = new javax.swing.DefaultComboBoxModel<>();
    for (String t : ds.getAllTerms()) {
        model.addElement(t);
    }
    cmbTerm.setModel(model);
    // 可选：如果没有任何 term，给一个占位
    if (model.getSize() == 0) model.addElement("No Terms");
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadFinance;
    private javax.swing.JButton btnMarkAllPaid;
    private javax.swing.JButton btnRecalc;
    private javax.swing.JComboBox<String> cmbTerm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblRevenueByDept;
    private javax.swing.JTable tblTuitionSummary;
    // End of variables declaration//GEN-END:variables
}
