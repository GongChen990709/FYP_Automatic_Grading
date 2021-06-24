package FYP19.Service;

import FYP19.Dao.AssignmentMapper;
import FYP19.Dao.StudentsMapper;
import FYP19.Entities.Assignment;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.util.Constants;
import FYP19.util.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AssignmentService {

    private AssignmentMapper assignmentMapper;
    private StudentsMapper studentsMapper;

    public void setAssignmentMapper(AssignmentMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }
    public void setStudentsMapper(StudentsMapper studentsMapper) {
        this.studentsMapper = studentsMapper;
    }

    public boolean insertAssignmentForms(String id, String title, String description, String module_code, String due_date) throws ParseException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date due_datetime = formatter.parse(due_date);
        Date creation_datetime = new Date();
        if(assignmentMapper.insertAssignmentForm(new Assignment(id,title,description,module_code,due_datetime,creation_datetime))==1){
            assignmentMapper.initFilesPathById(id);
            return true;
        }
        return false;
    }

    public boolean updateDueDateById(String due_date, String id) throws ParseException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date due_datetime = formatter.parse(due_date);
        if(assignmentMapper.updateDueDateById(id,due_datetime)==1){
            return true;
        }
        return false;
    }

    public boolean updatePdfPath(String id, String path){
        if(assignmentMapper.updatePdfPathById(id, path)==1){
            return true;
        }
        return false;
    }

    public boolean updateJavaPath(String id, String path){
        if(assignmentMapper.updateJavaPathById(id, path)==1){
            return true;
        }
        return false;
    }

    public boolean updateDataPath(String id, String path){
        if(assignmentMapper.updateDataPathById(id, path)==1){
            return true;
        }
        return false;
    }

    public boolean updateDataTypePath(String id, String path){
        if(assignmentMapper.updateDataTypePathById(id, path)==1){
            return true;
        }
        return false;
    }

    public String getPdfPathById(String id){
        return assignmentMapper.queryPdfPathById(id);
    }

    public String getJavaPathById(String id){
        return assignmentMapper.queryJavaPathById(id);
    }

    public String getDataPathById(String id){
        return assignmentMapper.queryDataPathById(id);
    }

    public String getDataTypePathById(String id){
        return assignmentMapper.queryDataTypePathById(id);
    }

    public List<Assignment> queryAssignmentByModule(String module_code){
        return assignmentMapper.queryAssignmentByModule(module_code);
    }

    public List<Assignment> queryAssignmentsByModule(String module_code){
        return assignmentMapper.queryAssignmentByModule(module_code);
    }

    public String queryDescriptionById(String id){
        return assignmentMapper.queryDescriptionById(id);
    }

    public Assignment queryAssignment(String title){
       return assignmentMapper.queryAssignment(title);
    }

    public String queryStatusById(String id){
        return assignmentMapper.queryStatusById(id);
    }

    public String queryStudentSourcePath(int ucd_id, String assignment_id){
        return assignmentMapper.queryStudentSourcePath(ucd_id, assignment_id);
    }

    public Float queryGrade(int ucd_id, String assignment_id){
        return assignmentMapper.queryGrade(ucd_id, assignment_id);
    }

    public Float[] queryAllGrades(String assignment_id){
        return assignmentMapper.queryAllGrades(assignment_id);
    }

    public List<Map<String, Object>> teacherViewSubmissions(String assignment_id){
        return assignmentMapper.teacherViewSubmissions(assignment_id);
    }

    public Date queryDueDateById(String assignment_id){
        return assignmentMapper.queryDueDateById(assignment_id);
    }

    public String queryNameById(String assignment_id){
        return assignmentMapper.queryNameById(assignment_id);
    }


    public Map<String, Object> studentViewAssignment(String assignment_id){
        return assignmentMapper.studentViewAssignment(assignment_id);
    }


    public Date querySubmissionDate(int ucd_id, String assignment_id){
        return assignmentMapper.querySubmissionDate(ucd_id,assignment_id);
    }


    public void deleteAssignmentById(String id, HttpServletRequest request){
        String status = assignmentMapper.queryStatusById(id);
        String teacherBasePath = request.getSession().getServletContext().getRealPath("/WEB-INF/teacherUpload/"+id);
        File teacherFiles = new File(teacherBasePath);
        if(teacherFiles.exists()) {
            FileUtils.deleteFilesByDirectory(teacherBasePath);
        }
        if(status.equals("Completed")){
            String module_code = queryModuleCode(id);
            String studentBasePath = request.getSession().getServletContext().getRealPath("/WEB-INF/studentUpload/"+id);
            List<Map<String,Object>> allStudents = studentsMapper.studentsUnderOneModule(module_code);
            for(Map<String, Object> stu : allStudents){
                int ucd_id = (int) stu.get("ucd_id");
                File studentFiles = new File(studentBasePath+"/"+ucd_id);
                if(studentFiles.exists()){
                    FileUtils.deleteFilesByDirectory(studentFiles.getPath());
                }
            }
            FileUtils.deleteFilesByDirectory(studentBasePath);
        }
        assignmentMapper.deleteAssignmentFormById(id);
        assignmentMapper.deleteAssignmentFilesById(id);
    }


    public List<Assignment> fuzzyQueryByTitle(String title, String module_code){
        return assignmentMapper.fuzzyQueryByTitle(title,module_code);
    }

    public Map<String,String> getFilePathsById(String id){
        return assignmentMapper.getFilePathsById(id);
    }

    public int countSubmittedById(String id){
        return assignmentMapper.countSubmittedById(id);
    }

    public int countNotGradedById(String id){
        return assignmentMapper.countNotGradedById(id);
    }

    public boolean insertStudentSubmission(int ucd_id, String assignment_id, String source_path, Date submission_date){
        if(assignmentMapper.insertStudentSubmission(ucd_id, assignment_id, source_path, submission_date)==1){
            return true;
        }
        return false;
    }

    public Map<String, Object> studentSubmissionHistory(int ucd_id, String assignment_id){
        return assignmentMapper.studentSubmissionHistory(ucd_id, assignment_id);
    }


    public boolean insertAssignmentAssessment(int ucd_id, String assignment_id, String grade_details_path){
        if(assignmentMapper.insertAssignmentAssessment(ucd_id,assignment_id,grade_details_path)==1){
            return true;
        }
        return  false;
    }


    public List<Map<String, Object>> teacherViewAllGrades(String assignment_id){
        return assignmentMapper.teacherViewAllGrades(assignment_id);
    }


    public String queryStuReportPath(int ucd_id, String assignment_id){
        return assignmentMapper.queryStuReportPath(ucd_id,assignment_id);
    }

    public List<Map<String, Object>> queryAllAssessment(String assignment_id){
        return assignmentMapper.queryAllAssessment(assignment_id);
    }

    public List<String> queryAllGradeDetailPaths(String assignment_id){
        return assignmentMapper.queryAllGradeDetailPaths(assignment_id);
    }

    public int updateAssignmentGrade(float grade, int ucd_id, String assignment_id){
        return assignmentMapper.updateAssignmentGrade(grade, ucd_id, assignment_id);
    }

    public String queryModuleCode(String assignment_id) {
        return assignmentMapper.queryModuleCode(assignment_id);
    }

    public List<Map<String,Object>> teacherViewGradeById(String assignment_id, int ucd_id){
        return assignmentMapper.teacherViewGradeById(assignment_id,ucd_id);
    }

    public List<Map<String,Object>>  teacherViewGradeByName(String assignment_id, String name){
        return assignmentMapper.teacherViewGradeByName(assignment_id, name);
    }











}
