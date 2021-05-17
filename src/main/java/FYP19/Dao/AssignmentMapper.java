package FYP19.Dao;

import FYP19.Entities.Assignment;
import FYP19.Entities.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.ls.LSInput;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AssignmentMapper {
    int insertAssignmentForm(Assignment assignment);

    Assignment queryAssignment(@Param("title") String title);
    List<Assignment> queryAssignmentByModule(@Param("module_code") String module_code);
    List<Assignment> queryAssignmentsByCreatorIdAndModule(@Param("creator_id") int creator_id, @Param("module_code") String module_code);
    String queryStatusById(@Param("id") String id);
    String queryStudentSourcePath(@Param("ucd_id") int ucd_id, @Param("assignment_id") String assignment_id);
    String queryDescriptionById(@Param("id") String id);
    Date queryDueDateById(@Param("id") String id);
    String queryNameById(@Param("id") String id);
    Date querySubmissionDate(@Param("ucd_id") int ucd_id, @Param("assignment_id") String assignment_id);
    Map<String, Object> studentViewAssignment(@Param("assignment_id") String assignment_id);
    Map<String, Object> studentSubmissionHistory(@Param("ucd_id") int ucd_id, @Param("assignment_id") String assignment_id);
    int initFilesPathById(@Param("id") String id);
    int updatePdfPathById(@Param("id") String id, @Param("PdfPath") String path);
    int updateJavaPathById(@Param("id") String id, @Param("JavaPath") String path);
    int updateDataPathById(@Param("id") String id, @Param("DataPath") String path);
    int updateDataTypePathById(@Param("id") String id, @Param("DataTypePath") String path);
    int insertStudentSubmission(@Param("ucd_id") int ucd_id, @Param("assignment_id") String assignment_id, @Param("source_path") String source_path, @Param("submission_date") Date submission_date);
    String queryPdfPathById(@Param("id") String id);
    String queryJavaPathById(@Param("id") String id);
    String queryDataPathById(@Param("id") String id);
    String queryDataTypePathById(@Param("id") String id);
    int updateDueDateById(@Param("id") String id, @Param("due_date") Date due_date);
    int deleteAssignmentFilesById(@Param("id") String id);
    int deleteAssignmentFormById(@Param("id") String id);
    List<Assignment> fuzzyQueryByTitle(@Param("title") String title, @Param("creator_id") int creator_id, @Param("module_code") String module_code);
    Map<String,String> getFilePathsById(@Param("id") String id);
    int countSubmittedById(@Param("id") String id);
    int countNotGradedById(@Param("id") String id);
    String queryGrade(@Param("ucd_id") int ucd_id, @Param("assignment_id") String assignment_id);
    List<Map<String, Object>> teacherViewSubmissions(@Param("assignment_id") String assignment_id);


}
