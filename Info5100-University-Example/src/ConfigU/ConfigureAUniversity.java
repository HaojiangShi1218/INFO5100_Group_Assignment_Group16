package ConfigU;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/

import info5100.university.example.College.College;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.AdminDirectory;
import info5100.university.example.Persona.AdminProfile;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Faculty.UserAccountDirectory;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import info5100.university.example.Persona.UserAccount;
import info5100.university.example.Persona.Registrar.RegistrarDirectory;
import info5100.university.example.Persona.Registrar.RegistrarProfile;


/**
 *
 * @author kal bugrara
 */
//public class ConfigureAUniversity {

//    public static College initialize() {
//
//        College college = new College("College of Engineering");
//        
//        //Department department = new Department("Information Systems");
//        Department department = college.newDepartment("Information Systems");
//        CourseCatalog coursecatalog = department.getCourseCatalog();
//        
//        Course course = coursecatalog.newCourse("app eng", "info 5100", 4);
//        
//        CourseSchedule courseschedule = department.newCourseSchedule("Fall2020");
//
//        CourseOffer courseoffer = courseschedule.newCourseOffer("info 5100");
//        if (courseoffer==null) return null;
//        courseoffer.generatSeats(10);
//        PersonDirectory pd = department.getPersonDirectory();
//        Person person = pd.newPerson("0112303");
//        person.setName("John Doe");
//        StudentDirectory sd = department.getStudentDirectory();
//        StudentProfile studentProfile1 = sd.newStudentProfile(person);
//        CourseLoad courseload = studentProfile1.newCourseLoad("Fall2020"); 
////        
//        courseload.newSeatAssignment(courseoffer); //register student in class
//        
//// person representing sales organization        
//        Person person001 = pd.newPerson("0112304");
//        person001.setName("John Smith");
//        Person person002 = pd.newPerson("0112305");
//        person002.setName("Gina Montana");
//        Person person003 = pd.newPerson("0112306");
//        person003.setName("Adam Rollen");
// 
//        Person person005 = pd.newPerson("0112307");
//        person005.setName("Jim Dellon");
//        Person person006 = pd.newPerson("0112308");
//        person006.setName("Anna Shnider");
//        Person person007 = pd.newPerson("0112309");
//        person007.setName("Laura Brown");
//        Person person008 = pd.newPerson("0112310");
//        person008.setName("Jack While");
//        Person person010 = pd.newPerson("0112311");
//        person010.setName("Meeten Cider");
//        
//        AdminDirectory adminDirectory = department.getAdminDirectory();
//        AdminProfile adminProfile0 = adminDirectory.newAdminProfile(person001);
//       
//        AdminProfile adminProfile1 = adminDirectory.newAdminProfile(person002);
//        
//        FacultyDirectory facultyDirectory = department.getFacultydirectory();
//        FacultyProfile facultyProfile1 = facultyDirectory.newFacultyProfile(person003);
//        facultyProfile1.getPerson().setEmail("rollen.adam@northeastern.edu");
//        facultyProfile1.getPerson().setPhone("3642557181");
//        facultyProfile1.AssignAsTeacher(courseoffer);
//        
//        
//        UserAccountDirectory uadirectory = department.getUseraccountdirectory();
//        UserAccount ua3 = uadirectory.newUserAccount(adminProfile0, "admin", "****"); /// order products for one of the customers and performed by a sales person
//        UserAccount ua4 = uadirectory.newUserAccount(adminProfile1, "Gina", "666");
//        UserAccount ua5 = uadirectory.newUserAccount(facultyProfile1, "Adam", "3144");
//        
//        int total = department.calculateRevenuesBySemester("Fall2020");
//        System.out.print("Total: " + total);
//        return college;
//    }

public class ConfigureAUniversity {

    public static College initialize() {
        final String SEM = "Fall2020";

        College college = new College("College of Engineering");
        Department department = college.newDepartment("Information Systems");

        // ----- Catalog + Schedule -----
        CourseCatalog coursecatalog = department.getCourseCatalog();
        CourseSchedule courseschedule = department.newCourseSchedule(SEM);

        // 5 courses
        coursecatalog.newCourse("Application Engineering", "INFO 5100", 4);
        coursecatalog.newCourse("Program Structures",     "INFO 6205", 4);
        coursecatalog.newCourse("Data Management Sys",    "DAMG 6210", 4);
        coursecatalog.newCourse("Web Design & User Exp",  "INFO 6150", 4);
        coursecatalog.newCourse("Cloud Computing",        "CSYE 6225", 4);

        // 5 offers (make plenty of seats so enrollment won’t fail)
        CourseOffer o1 = courseschedule.newCourseOffer("INFO 5100"); o1.generatSeats(20);
        CourseOffer o2 = courseschedule.newCourseOffer("INFO 6205"); o2.generatSeats(20);
        CourseOffer o3 = courseschedule.newCourseOffer("DAMG 6210"); o3.generatSeats(20);
        CourseOffer o4 = courseschedule.newCourseOffer("INFO 6150"); o4.generatSeats(20);
        CourseOffer o5 = courseschedule.newCourseOffer("CSYE 6225"); o5.generatSeats(20);
        CourseOffer[] offers = { o1, o2, o3, o4, o5 };

        // ----- People (30 total) -----
        PersonDirectory pd = department.getPersonDirectory();

        // helper to create persons with sequential ids and names
        java.util.List<Person> people = new java.util.ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            String id = String.format("01123%02d", i); // 0112301..0112330
            Person p = pd.newPerson(id);
            p.setName(sampleName(i));
            p.setEmail("user" + i + "@example.edu");
            p.setPhone("617000" + String.format("%04d", i));
            people.add(p);
        }

        // roles:
        Person adminPerson     = people.get(0);   // 1 admin
        //Person registrarPerson = people.get(1);   // 1 registrar (if your API supports it)
        java.util.List<Person> facultyPersons = people.subList(2, 12);   // 10 faculty
        java.util.List<Person> studentPersons = people.subList(12, 22);  // 10 students
        // remaining persons (22..29) are just extra people on the directory

        // ----- Admin -----
        AdminDirectory adminDirectory = department.getAdminDirectory();
        AdminProfile adminProfile = adminDirectory.newAdminProfile(adminPerson);

        // ----- Registrar (if your model has it) -----
        // Many INFO5100 codebases have Registrar; if yours does, uncomment:
        // RegistrarDirectory registrarDirectory = department.getRegistrarDirectory();
        // RegistrarProfile registrarProfile     = registrarDirectory.newRegistrarProfile(registrarPerson);

        // ----- Faculty (10) -----
        FacultyDirectory facultyDirectory = department.getFacultydirectory();
        java.util.List<FacultyProfile> faculties = new java.util.ArrayList<>();
        for (int i = 0; i < facultyPersons.size(); i++) {
            FacultyProfile fp = facultyDirectory.newFacultyProfile(facultyPersons.get(i));
            fp.getPerson().setEmail("faculty" + (i+1) + "@example.edu");
            fp.getPerson().setPhone("617555" + String.format("%04d", i+1));
            faculties.add(fp);
        }

        // assign 1 faculty to each course offer (reuse if fewer than offers)
        o1.getSubjectCourse().setName("Application Engineering");
        for (int i = 0; i < offers.length; i++) {
            FacultyProfile teacher = faculties.get(i % faculties.size());
            teacher.AssignAsTeacher(offers[i]);
        }

        // ----- Students (10) + enroll (seat assignments) -----
        StudentDirectory sd = department.getStudentDirectory();
        java.util.List<StudentProfile> students = new java.util.ArrayList<>();
        for (Person sp : studentPersons) {
            students.add(sd.newStudentProfile(sp));
        }

        // each student gets a courseload and we enroll them round-robin into 2–3 courses
        int idx = 0;
        for (int s = 0; s < students.size(); s++) {
            StudentProfile st = students.get(s);
            CourseLoad cl = st.newCourseLoad(SEM);

            // Enroll in 3 different courses (wrap around)
            for (int k = 0; k < 3; k++) {
                CourseOffer offer = offers[(idx + k) % offers.length];
                cl.newSeatAssignment(offer);
            }
            idx++;
        }

        // ----- Accounts (admin + faculties; add more as needed) -----
        UserAccountDirectory uad = department.getUseraccountdirectory();
        uad.newUserAccount(adminProfile, "admin", "****");

        for (int i = 0; i < faculties.size(); i++) {
            FacultyProfile fp = faculties.get(i);
            uad.newUserAccount(fp, "fac" + (i+1), "pass" + (i+1));
        }

        // If you created registrarProfile above, you can also:
        // uad.newUserAccount(registrarProfile, "registrar", "****");
        // ① 建 Person（只有 id、name 等基础字段）
        //registrar 角色建立
// —— registrar 角色建立 ——

// ① 先建一个 Person（只有 id / name / 联系方式可选）
// —— registrar 角色建立 ——

// ① Person
Person regPerson = department.getPersonDirectory().newPerson("0112400");
regPerson.setName("Registrar");

// ② 从目录创建 RegistrarProfile（方法名按你们源码调整）
RegistrarDirectory registrarDirectory = new RegistrarDirectory();
// 二选一：看 RegistrarDirectory 里是哪个方法
// RegistrarProfile regProfile = registrarDirectory.newRegistrar(regPerson);
RegistrarProfile   regProfile         = registrarDirectory.newRegistrar(regPerson);

// ③ 联系方式：若 Profile 没有 setter，就改设置到 person 上
// 如果有 regProfile.setEmail / setPhone 就用下面两行：
// regProfile.setEmail("registrar@example.edu");
// regProfile.setPhone("617-000-0000");
// 否则用 person：
regProfile.getPerson().setEmail("registrar@example.edu");
regProfile.getPerson().setPhone("617-000-0000");

// ④ 账号：不要再次声明 uad，直接复用上面已创建的 uad
uad.newUserAccount(regProfile, "registrar", "****");

// 日志
System.out.println("Seeded Registrar: " + regPerson.getPersonId()
        + " / " + regProfile.getPerson().getEmail());


// 日志
System.out.println("Seeded Registrar: " + regPerson.getPersonId()
        + " / " + regProfile.getEmail());

//一直到这里 xuanli liu

// 日志
System.out.println("Seeded Registrar: " + regPerson.getPersonId()
        + " / " + regProfile.getEmail());


        // (Optional) compute revenue
        int total = department.calculateRevenuesBySemester(SEM);
        System.out.println("Total: " + total);

        return college;
        
    }

    // --- helper for sample names ---
    private static String sampleName(int i) {
        String[] first = {"Alex","Jamie","Taylor","Jordan","Casey","Riley","Avery","Morgan","Quinn","Reese",
                          "Rowan","Sage","Skyler","Emerson","Parker","Drew","Shawn","Kendall","Bailey","Hayden",
                          "Cameron","Logan","Sydney","Payton","Harper","Elliot","Finley","Dakota","Marley","Rory"};
        String[] last  = {"Lee","Patel","Nguyen","Rivera","Kim","Chen","Murphy","Garcia","Khan","Zhang",
                          "Wong","Martin","Singh","Lopez","Davis","Brown","Wilson","Clark","Baker","Cruz",
                          "Young","Scott","Green","Adams","Hill","Cooper","Ward","Cook","Bell","Gray"};
        return first[(i-1) % first.length] + " " + last[(i-1) % last.length];
    }
}

