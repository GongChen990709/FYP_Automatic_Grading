package FYP19.Service;

import FYP19.Dao.AssignmentMapper;
import FYP19.Entities.Assignment;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.util.Constants;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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



    public Assignment queryAssignment(String title){
       return assignmentMapper.queryAssignment(title);
    }

}
