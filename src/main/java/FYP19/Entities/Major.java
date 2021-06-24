package FYP19.Entities;

public class Major {
    private String code;
    private String title;

    public Major(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public Major(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Major{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
