package FYP19.Service;

import FYP19.Dao.AssignmentMapper;
import FYP19.Entities.Assignment;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.util.Constants;
import FYP19.util.FileUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AssignmentService {

    private AssignmentMapper assignmentMapper;

    public void setAssignmentMapper(AssignmentMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }

    public boolean insertAssignmentForms(String id, String title, String description, String module_code, String due_date, HttpServletRequest request) throws ParseException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date due_datetime = formatter.parse(due_date);
        Date creation_datetime = new Date();
        Object user = request.getSession().getAttribute(Constants.USER_SESSION);

        if(user instanceof Teacher){
            int teacher_id = ((Teacher) user).getId();
            if(assignmentMapper.insertAssignmentForm(new Assignment(id,title,description,module_code,due_datetime,creation_datetime,teacher_id))==1){
                if(assignmentMapper.initFilesPathById(id)==1){
                    return true;
                }
            }
        }
        if(user instanceof Students){
            int ucd_id = ((Students) user).getUcd_id();
            if(assignmentMapper.insertAssignmentForm(new Assignment(id,title,description,module_code,due_datetime,creation_datetime, ucd_id))==1) {
                if(assignmentMapper.initFilesPathById(id)==1){
                    return true;
                }
            }
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

    public List<Assignment> queryAssignmentsByCreatorIdAndModule(String module_code, HttpServletRequest request){
        List<Assignment> assignmentList = null;
        Object user = request.getSession().getAttribute(Constants.USER_SESSION);
        if(user instanceof Teacher){
            int teacher_id = ((Teacher) user).getId();
            assignmentList = assignmentMapper.queryAssignmentsByCreatorIdAndModule(teacher_id, module_code);
        }
        if(user instanceof Students){
            int ucd_id = ((Students) user).getUcd_id();
            assignmentList = assignmentMapper.queryAssignmentsByCreatorIdAndModule(ucd_id, module_code);
        }
        return assignmentList;
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

    public String queryGrade(int ucd_id, String assignment_id){
        return assignmentMapper.queryGrade(ucd_id, assignment_id);
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
        String directoryPath = request.getSession().getServletContext().getRealPath("/WEB-INF/teacherUpload/"+id);
        FileUtils.deleteFilesByDirectory(directoryPath);
        assignmentMapper.deleteAssignmentFilesById(id);
        assignmentMapper.deleteAssignmentFormById(id);
    }

    public List<Assignment> fuzzyQueryByTitle(String title, int creator_id, String module_code){
        return assignmentMapper.fuzzyQueryByTitle(title,creator_id,module_code);
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











}
