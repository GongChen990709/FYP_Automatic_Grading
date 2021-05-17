package FYP19.Entities;

import java.util.Date;

public class Assignment {
    private String id;
    private String title;
    private String description;
    private String module_code;
    private Date due_date;
    private Date creation_date;
    private int creator_id;

    public Assignment(String id, String title, String description, String module_code, Date due_date, Date creation_date, int creator_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.module_code = module_code;
        this.due_date = due_date;
        this.creation_date = creation_date;
        this.creator_id = creator_id;
    }

    public Assignment(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule_code() {
        return module_code;
    }

    public void setModule_code(String module_code) {
        this.module_code = module_code;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }
}
