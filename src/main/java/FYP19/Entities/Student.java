package FYP19.Entities;
import org.apache.ibatis.type.Alias;

@Alias("student")
public class Student {
    private int studentNum;
    private String studentName;
    private String password;
    private int stage;

    public Student(int studentNum, String studentName, String password, int stage) {
        this.studentNum = studentNum;
        this.studentName = studentName;
        this.password = password;
        this.stage = stage;
    }

    public Student(){}


    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNum=" + studentNum +
                ", studentName='" + studentName + '\'' +
                ", password='" + password + '\'' +
                ", stage=" + stage +
                '}';
    }
}
