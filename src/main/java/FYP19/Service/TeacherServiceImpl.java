package FYP19.Service;

import FYP19.Dao.*;
import FYP19.Entities.*;
import FYP19.util.FileUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;


public class TeacherServiceImpl implements TeacherService{
    private TeacherMapper teacherMapper;
    private ModuleMapper moduleMapper;
    private UserMapper userMapper;
    private AssignmentMapper assignmentMapper;
    private StudentsMapper studentsMapper;

    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }
    public void setModuleMapper(ModuleMapper moduleMapper){this.moduleMapper = moduleMapper;}
    public void setUserMapper(UserMapper userMapper){this.userMapper = userMapper;}
    public void setAssignmentMapper(AssignmentMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }
    public void setStudentsMapper(StudentsMapper studentsMapper) {
        this.studentsMapper = studentsMapper;
    }

    public Teacher queryTeacherById(int id) {
        return teacherMapper.queryTeacherById(id);
    }
    public int registerModule(Module module) {
        return moduleMapper.insertModule(module);
    }
    public List<Module> queryAllModules(int teacher_id) {
        return moduleMapper.queryModulesByTid(teacher_id);
    }

    public List<Map<String, Object>> associatedModule() {
        return teacherMapper.associatedModule();
    }
    public List<Map<String, Object>> allTeacherInfo() {
        return teacherMapper.allTeacherInfo();
    }

    public void deleteTeacherById(int id, HttpServletRequest request) {
        List<Map<String,Object>> modules = teacherMapper.associatedModule();
        for(Map<String,Object> module : modules){
            String module_code = (String) module.get("code");
            deleteModule(module_code, request);
        }
        teacherMapper.deleteTeacherById(id);
    }

    public int deleteModule(String module_code, HttpServletRequest request){
        List<Assignment> assignmentList = assignmentMapper.queryAssignmentByModule(module_code);
        for(Assignment assignment : assignmentList){
            String assignmentId = assignment.getId();
            String status = assignmentMapper.queryStatusById(assignmentId);
            String teacherBasePath = request.getSession().getServletContext().getRealPath("/WEB-INF/teacherUpload/"+assignmentId);
            File teacherFiles = new File(teacherBasePath);
            if(teacherFiles.exists()) {
                FileUtils.deleteFilesByDirectory(teacherBasePath);
            }
            if(status.equals("Completed")){
                String studentBasePath = request.getSession().getServletContext().getRealPath("/WEB-INF/studentUpload/"+assignmentId);
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
        }
        return moduleMapper.deleteModule(module_code);
    }



    //Register Service Assist
    public boolean insertDepart(String code, String name){
        if(teacherMapper.insertDepartment(new Department(code,name))==1){
            return true;
        }
        return false;
   }
    public boolean insertRole(int teacher_id){
       if(userMapper.insertRole(teacher_id,"teacher")==1){
           return true;
       }
       return false;
   }
    public boolean updateIsActivatedById(int teacher_id, boolean isActivated) {
        if(teacherMapper.updateIsActivatedById(teacher_id, isActivated)==1){
            return true;
        }
        return false;
   }
    public boolean updateActivationCodeById(int teacher_id, String activationCode) {
       if(teacherMapper.updateActivationCodeById(teacher_id,activationCode)==1){
           return true;
       }
        return false;
   }
    public boolean updatePwdById(int teacher_id, String pwd) {
       if(teacherMapper.updatePwdById(teacher_id,pwd)==1){
            return true;
        }
       return false;
   }
/////////////////////////////
//Register Service
    //Register single teacher
    public boolean initialRegisterTeacher(Teacher teacher, String activationCode) {
       Department depart = teacherMapper.queryDepartmentByCode(teacher.getDepartment_code());
       boolean depart_flag = false;
       boolean teacher_flag = false;
       boolean activation_flag = false;
       boolean role_flag = false;
       if(depart==null){
           String name = "";
           if(teacher.getDepartment_code().equals("CS")){
               name = "School of Computer Science";
           }
           if(teacher.getDepartment_code().equals("EE")){
               name = "School of Electrical and Electronic Engineering";
           }
           if(insertDepart(teacher.getDepartment_code(),name)){
               depart_flag=true;
           }
       }
       else{depart_flag=true;}

       if(insertRole(teacher.getId())){
            role_flag = true;
       }

       if(teacherMapper.registerTeacher(teacher)==1){
           teacher_flag = true;
       }

       if(teacherMapper.updateActivationCodeById(teacher.getId(),activationCode)==1){
           activation_flag = true;
       }
       return depart_flag&&role_flag&&teacher_flag&&activation_flag;
   }
    public String queryActivationCodeById(int teacher_id) {
       return teacherMapper.queryActivationCodeById(teacher_id);
   }
    public boolean regComplete(int teacher_id, String pwd) {
       boolean pwd_flag = updatePwdById(teacher_id,pwd);
       boolean activate_flag = updateIsActivatedById(teacher_id,true);
       boolean code_flag = updateActivationCodeById(teacher_id,null);
       return pwd_flag&&code_flag&&activate_flag;
   }
/////////////////////////////
}
