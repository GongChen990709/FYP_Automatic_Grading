package FYP19.Entities;

public class TA {
    private int ucd_id;
    private String name;
    private String pwd;
    private String email;
    private String major_code;
    private int teacher_id;


    public TA(int ucd_id, String name, String pwd, String email, String major_code, int teacher_id) {
        this.ucd_id = ucd_id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.major_code = major_code;
        this.teacher_id = teacher_id;
    }

    public TA() {
    }

    public int getUcd_id() {
        return ucd_id;
    }

    public void setUcd_id(int ucd_id) {
        this.ucd_id = ucd_id;
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

    public String getMajor_code() {
        return major_code;
    }

    public void setMajor_code(String major_code) {
        this.major_code = major_code;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "TA{" +
                "ucd_id=" + ucd_id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", major_code='" + major_code + '\'' +
                ", teacher_id=" + teacher_id +
                '}';
    }
}
