package info5100.university.example.workareas.RegistrarRole.shared;

import java.util.*;

/** 全局共享的内存数据仓库（单例），供四个面板联动使用（Java 8 兼容） */
public class RegistrarDataStore {

    // ====== 模型 ======
    public static class Offering {
        public String term, dept, courseId, title, faculty, room, time;
        public int capacity, enrolled;
        public boolean open;
        public int fee; // 学费（每门课），便于财务联动

        public Offering(String term, String dept, String courseId, String title,
                        String faculty, String room, String time,
                        int capacity, int enrolled, boolean open, int fee) {
            this.term = term; this.dept = dept; this.courseId = courseId; this.title = title;
            this.faculty = faculty; this.room = room; this.time = time;
            this.capacity = capacity; this.enrolled = enrolled; this.open = open;
            this.fee = fee;
        }
    }

    public static class PaymentRecord {
        public String term;
        public String dept;
        public String studentId;
        public String courseId;
        public int amount;
        public boolean paid;

        public PaymentRecord(String term, String dept, String studentId, String courseId, int amount, boolean paid) {
            this.term = term; this.dept = dept; this.studentId = studentId; this.courseId = courseId;
            this.amount = amount; this.paid = paid;
        }
    }

    // ====== 单例 ======
    private static final RegistrarDataStore INSTANCE = new RegistrarDataStore();
    public static RegistrarDataStore getInstance() { return INSTANCE; }
    private RegistrarDataStore() {}

    // ====== 存储结构 ======
    // 课程：courseId -> Offering
    private final Map<String, Offering> offerings = new HashMap<String, Offering>();
    // 选课关系
    private final Map<String, Set<String>> courseToStudents = new HashMap<String, Set<String>>(); // courseId -> studentIds
    private final Map<String, Set<String>> studentToCourses = new HashMap<String, Set<String>>(); // studentId -> courseIds
    // 账本
    private final List<PaymentRecord> ledger = new ArrayList<PaymentRecord>();

    // ====== 查询入口 ======
    public Collection<Offering> getAllOfferings() { return offerings.values(); }
    public Offering getOfferingById(String cid) { return offerings.get(cid); }
    public List<PaymentRecord> getLedger() { return ledger; }
    public Set<String> getStudentsOfCourse(String cid) {
        Set<String> s = courseToStudents.get(cid);
        return s == null ? Collections.<String>emptySet() : new HashSet<String>(s);
    }
    public Set<String> getCoursesOfStudent(String sid) {
        Set<String> s = studentToCourses.get(sid);
        return s == null ? Collections.<String>emptySet() : new HashSet<String>(s);
    }

    // ====== Course Offering 维护 ======
    public void putOrUpdateOffering(Offering o) {
        offerings.put(o.courseId, o);
        if (!courseToStudents.containsKey(o.courseId)) {
            courseToStudents.put(o.courseId, new HashSet<String>());
        }
        // enrolled 以真实关系为准（如果需要可重新计算）
        o.enrolled = getStudentsOfCourse(o.courseId).size();
    }
    public void assignFaculty(String cid, String faculty) {
        Offering o = offerings.get(cid);
        if (o != null) o.faculty = faculty;
    }
    public void toggleOpen(String cid) {
        Offering o = offerings.get(cid);
        if (o != null) o.open = !o.open;
    }
    public void updateCapacity(String cid, int newCap) {
        Offering o = offerings.get(cid);
        if (o != null && newCap >= 0) o.capacity = newCap;
    }
    // RegistrarDataStore.java
public java.util.Set<String> getAllTerms() {
    java.util.LinkedHashSet<String> terms = new java.util.LinkedHashSet<>();
    // 来自课程
    for (Offering o : offerings.values()) {
        if (o.term != null && !o.term.isEmpty()) terms.add(o.term);
    }
    // 来自账单（以防有历史账单但当前无开课）
    for (PaymentRecord pr : ledger) {
        if (pr.term != null && !pr.term.isEmpty()) terms.add(pr.term);
    }
    return terms;
}


    // ====== Enrollment API（带容量/open 校验，自动记账）======
    public String enroll(String studentId, String courseId) {
        Offering o = offerings.get(courseId);
        if (o == null) return "Course " + courseId + " not found.";
        if (!o.open) return "Course " + courseId + " is closed.";
        if (getCoursesOfStudent(studentId).contains(courseId)) return "Student already enrolled.";
        if (o.enrolled >= o.capacity) return "Course is full. Capacity = " + o.capacity + ".";

        // 建立关系
        Set<String> ss = courseToStudents.get(courseId);
        if (ss == null) { ss = new HashSet<String>(); courseToStudents.put(courseId, ss); }
        ss.add(studentId);
        Set<String> cc = studentToCourses.get(studentId);
        if (cc == null) { cc = new HashSet<String>(); studentToCourses.put(studentId, cc); }
        cc.add(courseId);

        // 更新人数
        o.enrolled = ss.size();

        // 记账（UNPAID）
        ledger.add(new PaymentRecord(o.term, o.dept, studentId, courseId, o.fee, false));
        return "OK";
    }

    public String drop(String studentId, String courseId) {
        Offering o = offerings.get(courseId);
        if (o == null) return "Course " + courseId + " not found.";
        if (!getCoursesOfStudent(studentId).contains(courseId)) return "Student is not enrolled in this course.";

        // 解除关系
        Set<String> ss = courseToStudents.get(courseId);
        if (ss != null) ss.remove(studentId);
        Set<String> cc = studentToCourses.get(studentId);
        if (cc != null) cc.remove(courseId);

        // 更新人数
        o.enrolled = (ss == null ? 0 : ss.size());

        // 退款/作废：这里给一个简单策略：找到该生该课的“未支付账单”，移除；如果都已支付，则追加一条负数退款记录
        boolean removedUnpaid = false;
        for (int i = ledger.size()-1; i >= 0; i--) {
            PaymentRecord pr = ledger.get(i);
            if (!pr.paid && pr.studentId.equals(studentId) && pr.courseId.equals(courseId)) {
                ledger.remove(i);
                removedUnpaid = true;
                break;
            }
        }
        if (!removedUnpaid) {
            // 追加退款记录（负数）
            ledger.add(new PaymentRecord(o.term, o.dept, studentId, courseId, -o.fee, true));
        }
        return "OK";
    }

    // ====== Finance 帮助方法 ======
    public int markAllPaidByTerm(String term) {
        int changed = 0;
        for (PaymentRecord pr : ledger) {
            if (term.equals(pr.term) && !pr.paid && pr.amount > 0) {
                pr.paid = true;
                changed++;
            }
        }
        return changed;
    }

    // ====== 演示用的种子数据（供一次性初始化）======
    public void seedDemoDataIfEmpty() {
        if (!offerings.isEmpty()) return;
        putOrUpdateOffering(new Offering("Fall 2025","IS","INFO 5100","Application Engineering","Dr. Smith","WVH-110","Mon 10:00-12:00",30,0,true,3000));
        putOrUpdateOffering(new Offering("Fall 2025","IS","INFO 5600","DBMS","Prof. Lee","RY-150","Tue 13:00-15:00",35,0,true,3200));
        putOrUpdateOffering(new Offering("Spring 2026","IS","INFO 6205","Program Design Paradigm","Dr. Chen","IV-210","Wed 09:00-11:00",25,0,true,3100));
        // 你也可以在外面继续 add
    }
}
