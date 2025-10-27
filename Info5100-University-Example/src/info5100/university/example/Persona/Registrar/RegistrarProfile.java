package info5100.university.example.Persona.Registrar;

import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.Profile;

/** Registrar 作为系统可登录的一个 Profile 角色 */
public class RegistrarProfile extends Profile {
    private String email;
    private String phone;
    private String officeHours;
    private String department;

    public RegistrarProfile(Person person) {
        super(person); // ★ 关键：调用父类 Profile(Person) 构造
    }

    @Override
    public String getRole() {
        return "Registrar"; // ★ 关键：登录路由要用到
    }

    // 你保留的业务字段
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getOfficeHours() { return officeHours; }
    public void setOfficeHours(String officeHours) { this.officeHours = officeHours; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "RegistrarProfile{" + getPerson() + "}";
    }
}
