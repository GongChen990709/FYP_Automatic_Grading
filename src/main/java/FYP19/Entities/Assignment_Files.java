package FYP19.Entities;

public class Assignment_Files {
    private String assignment_id;
    private String pdf_path;
    private String source_path;
    private String data_path;
    private String datatype_path;

    public Assignment_Files(String assignment_id, String pdf_path, String source_path, String data_path, String datatype_path) {
        this.assignment_id = assignment_id;
        this.pdf_path = pdf_path;
        this.source_path = source_path;
        this.data_path = data_path;
        this.datatype_path = datatype_path;
    }

    public Assignment_Files() {
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(String assignment_id) {
        this.assignment_id = assignment_id;
    }

    public String getPdf_path() {
        return pdf_path;
    }

    public void setPdf_path(String pdf_path) {
        this.pdf_path = pdf_path;
    }

    public String getSource_path() {
        return source_path;
    }

    public void setSource_path(String source_path) {
        this.source_path = source_path;
    }

    public String getData_path() {
        return data_path;
    }

    public void setData_path(String data_path) {
        this.data_path = data_path;
    }

    public String getDatatype_path() {
        return datatype_path;
    }

    public void setDatatype_path(String datatype_path) {
        this.datatype_path = datatype_path;
    }
}
