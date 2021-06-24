package FYP19.Entities;

public class Teacher {
    private int id;
    private String name;
    private String pwd;
    private String email;
    private String department_code;

    public Teacher(int id, String name, String pwd, String email, String department_code) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.department_code = department_code;
    }

    public Teacher() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", department_code='" + department_code + '\'' +
                '}';
    }
}
