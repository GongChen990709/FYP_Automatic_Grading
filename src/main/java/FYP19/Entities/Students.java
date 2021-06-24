package FYP19.Entities;

public class Students {
    private int ucd_id;
    private String name;
    private String pwd;
    private String email;
    private String major_code;


    public Students(int ucd_id, String name, String pwd, String email, String major_code) {
        this.ucd_id = ucd_id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.major_code = major_code;
    }

    public Students() {
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

    @Override
    public String toString() {
        return "Students{" +
                "ucd_id=" + ucd_id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", major_code='" + major_code + '\'' +
                '}';
    }
}
