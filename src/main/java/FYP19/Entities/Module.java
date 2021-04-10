package FYP19.Entities;

public class Module {
    private String code;
    private String name;
    private int teacher_id;

    public Module(String code, String name, int teacher_id) {
        this.code = code;
        this.name = name;
        this.teacher_id = teacher_id;
    }

    public Module() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "Module{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", teacher_id=" + teacher_id +
                '}';
    }
}
