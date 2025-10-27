package info5100.university.example.workareas.RegistrarRole.shared;

import java.util.*;

/** 全局共享的内存数据仓库（单例），供 Registrar 四个面板联动（Java 8 兼容） */
public class RegistrarDataStore {

    // ===================== 常量 & GPA 工具 =====================
    public static final String[] LETTERS = {"A","A-","B+","B","B-","C+","C","C-","F"};

    private static int letterIndex(String l){
        for (int i=0;i<LETTERS.length;i++) if (LETTERS[i].equals(l)) return i;
        return LETTERS.length-1; // 默认 F
    }
    public static double letterToPoint(String letter){
        switch (letter){
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B":  return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C":  return 2.0;
            case "C-": return 1.7;
            default:   return 0.0; // F
        }
    }
    private static double round2(double v){ return Math.round(v*100.0)/100.0; }

    // ===================== 模型 =====================
    public static class Offering {
        public String term, dept, courseId, title, faculty, room, time;
        public int capacity, enrolled;
        public boolean open;
        public int fee;

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
        public String term, dept, studentId, courseId;
        public int amount; public boolean paid;
        public PaymentRecord(String term, String dept, String studentId, String courseId, int amount, boolean paid) {
            this.term = term; this.dept = dept; this.studentId = studentId; this.courseId = courseId;
            this.amount = amount; this.paid = paid;
        }
    }
    public static class CourseStat {
        public final String term, dept, courseId, title, faculty;
        public final int enrolled;
        public CourseStat(String term, String dept, String courseId, String title, String faculty, int enrolled) {
            this.term = term; this.dept = dept; this.courseId = courseId; this.title = title; this.faculty = faculty; this.enrolled = enrolled;
        }
    }
    public static class ProgramGpaRow {
        public String program;
        public int[] buckets = new int[LETTERS.length];
        public double avgGpa;
    }

    // ===================== 单例 =====================
    private static final RegistrarDataStore INSTANCE = new RegistrarDataStore();
    public static RegistrarDataStore getInstance() { return INSTANCE; }
    private RegistrarDataStore() {}

    // ===================== 存储结构 =====================
    private final Map<String, Offering> offerings = new HashMap<String, Offering>();
    private final Map<String, Set<String>> courseToStudents = new HashMap<String, Set<String>>();
    private final Map<String, Set<String>> studentToCourses = new HashMap<String, Set<String>>();
    private final List<PaymentRecord> ledger = new ArrayList<PaymentRecord>();
    private final Map<String, String> studentProgram = new HashMap<String, String>();
    private final Map<String, Map<String, String>> grades = new HashMap<String, Map<String, String>>();

    // ===================== 查询入口 =====================
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
    public String getProgramOfStudent(String sid){ return studentProgram.getOrDefault(sid, "MS"); }
    public void setProgramOfStudent(String sid, String program){
        if (sid!=null && program!=null) studentProgram.put(sid, program);
    }

    public Set<String> getAllTerms() {
        LinkedHashSet<String> terms = new LinkedHashSet<String>();
        for (Offering o : offerings.values()) if (o.term != null && !o.term.isEmpty()) terms.add(o.term);
        for (PaymentRecord pr : ledger)      if (pr.term != null && !pr.term.isEmpty()) terms.add(pr.term);
        return terms;
    }

    public List<CourseStat> getCourseStats(String termFilter) {
        List<CourseStat> out = new ArrayList<CourseStat>();
        for (Offering o : offerings.values()) {
            if (termFilter != null && !termFilter.equals(o.term)) continue;
            int enrolled = 0;
            Set<String> ss = courseToStudents.get(o.courseId);
            if (ss != null) enrolled = ss.size();
            out.add(new CourseStat(o.term, o.dept, o.courseId, o.title, o.faculty, enrolled));
        }
        Collections.sort(out, new Comparator<CourseStat>() {
            public int compare(CourseStat a, CourseStat b){
                int t = (a.term==null?"":a.term).compareTo(b.term==null?"":b.term);
                if (t!=0) return t;
                int d = (a.dept==null?"":a.dept).compareTo(b.dept==null?"":b.dept);
                if (d!=0) return d;
                return (a.courseId==null?"":a.courseId).compareTo(b.courseId==null?"":b.courseId);
            }
        });
        return out;
    }

    public List<ProgramGpaRow> getGpaDistributionByTerm(String term){
        LinkedHashMap<String, ProgramGpaRow> map = new LinkedHashMap<String, ProgramGpaRow>();
        for (String prog : new HashSet<String>(studentProgram.values())){
            ProgramGpaRow r = new ProgramGpaRow(); r.program = prog; map.put(prog, r);
        }
        if (map.isEmpty()){
            ProgramGpaRow r = new ProgramGpaRow(); r.program = "MS"; map.put("MS", r);
        }

        for (Map.Entry<String, Map<String,String>> e : grades.entrySet()){
            String sid = e.getKey();
            String prog = studentProgram.getOrDefault(sid, "MS");
            ProgramGpaRow row = map.get(prog);
            if (row == null){ row = new ProgramGpaRow(); row.program = prog; map.put(prog, row); }

            for (Map.Entry<String,String> g : e.getValue().entrySet()){
                String cid = g.getKey();
                Offering o = offerings.get(cid);
                if (o == null || !term.equals(o.term)) continue;
                String letter = g.getValue();
                int idx = letterIndex(letter);
                row.buckets[idx]++;
            }
        }

        for (ProgramGpaRow r : map.values()){
            int n=0; double sum=0;
            for (int i=0;i<LETTERS.length;i++){
                int c = r.buckets[i];
                n += c;
                sum += c * letterToPoint(LETTERS[i]);
            }
            r.avgGpa = n==0 ? 0.0 : round2(sum/n);
        }
        return new ArrayList<ProgramGpaRow>(map.values());
    }

    // ===================== Offering 维护 =====================
    public void putOrUpdateOffering(Offering o) {
        offerings.put(o.courseId, o);
        if (!courseToStudents.containsKey(o.courseId)) {
            courseToStudents.put(o.courseId, new HashSet<String>());
        }
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

    // ===================== Enrollment（联动财务） =====================
    public String enroll(String studentId, String courseId) {
        Offering o = offerings.get(courseId);
        if (o == null) return "Course " + courseId + " not found.";
        if (!o.open) return "Course " + courseId + " is closed.";
        if (getCoursesOfStudent(studentId).contains(courseId)) return "Student already enrolled.";
        if (o.enrolled >= o.capacity) return "Course is full. Capacity = " + o.capacity + ".";

        Set<String> ss = courseToStudents.get(courseId);
        if (ss == null) { ss = new HashSet<String>(); courseToStudents.put(courseId, ss); }
        ss.add(studentId);
        Set<String> cc = studentToCourses.get(studentId);
        if (cc == null) { cc = new HashSet<String>(); studentToCourses.put(studentId, cc); }
        cc.add(courseId);

        o.enrolled = ss.size();
        ledger.add(new PaymentRecord(o.term, o.dept, studentId, courseId, o.fee, false));
        return "OK";
    }

    public String drop(String studentId, String courseId) {
        Offering o = offerings.get(courseId);
        if (o == null) return "Course " + courseId + " not found.";
        if (!getCoursesOfStudent(studentId).contains(courseId)) return "Student is not enrolled in this course.";

        Set<String> ss = courseToStudents.get(courseId);
        if (ss != null) ss.remove(studentId);
        Set<String> cc = studentToCourses.get(studentId);
        if (cc != null) cc.remove(courseId);

        o.enrolled = (ss == null ? 0 : ss.size());

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
            ledger.add(new PaymentRecord(o.term, o.dept, studentId, courseId, -o.fee, true));
        }
        return "OK";
    }

    // ===================== Finance =====================
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

    // ===================== 成绩 =====================
    public String recordGrade(String studentId, String courseId, String letter){
        Offering o = offerings.get(courseId);
        if (o == null) return "Course not found.";
        if (!getCoursesOfStudent(studentId).contains(courseId))
            return "Student is not enrolled in this course.";
        Map<String,String> m = grades.get(studentId);
        if (m == null){ m = new HashMap<String,String>(); grades.put(studentId, m); }
        m.put(courseId, letter);
        return "OK";
    }
    public Map<String,String> getGradesOfStudent(String sid){
        Map<String,String> m = grades.get(sid);
        return m==null ? Collections.<String,String>emptyMap() : new HashMap<String,String>(m);
    }

    // ===================== Demo 种子数据 =====================
    public void seedStudentsIfEmpty(){
        if (!studentProgram.isEmpty()) return;
        studentProgram.put("S10001", "MSIS");
        studentProgram.put("S10002", "MSCS");
        studentProgram.put("S10003", "MSIS");
    }
    public void seedDemoDataIfEmpty() {
        if (!offerings.isEmpty()) return;
        putOrUpdateOffering(new Offering("Fall 2025","IS","INFO 5100","Application Engineering","Dr. Smith","WVH-110","Mon 10:00-12:00",30,0,true,3000));
        putOrUpdateOffering(new Offering("Fall 2025","IS","INFO 5600","DBMS","Prof. Lee","RY-150","Tue 13:00-15:00",35,0,true,3200));
        putOrUpdateOffering(new Offering("Spring 2026","IS","INFO 6205","Program Design Paradigm","Dr. Chen","IV-210","Wed 09:00-11:00",25,0,true,3100));
        seedStudentsIfEmpty();
    }
}
