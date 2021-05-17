package FYP19.Service;

import FYP19.Dao.AdminMapper;
import FYP19.Dao.ModuleMapper;
import FYP19.Dao.StudentsMapper;
import FYP19.Dao.UserMapper;
import FYP19.Entities.Major;
import FYP19.Entities.Module;
import FYP19.Entities.Registration_History;
import FYP19.Entities.Students;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class StudentsServiceImpl implements StudentsService{
    private StudentsMapper studentsMapper;
    private UserMapper userMapper;
    private AdminMapper adminMapper;
    private ModuleMapper moduleMapper;
    public void setStudentsMapper(StudentsMapper studentsMapper) {
        this.studentsMapper = studentsMapper;
    }
    public void setUserMapper(UserMapper userMapper){this.userMapper = userMapper;}
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }
    public void setModuleMapper(ModuleMapper moduleMapper){this.moduleMapper = moduleMapper;}


    public Students queryStudentById(int ucd_id) {
        return studentsMapper.queryStudentById(ucd_id);
    }


    public int countStudentNumByCode(String module_code){
        return moduleMapper.countStudentNumByCode(module_code);
    }


    public List<Module> queryAllModules(int ucd_id) {
        return moduleMapper.queryModulesBySid(ucd_id);
    }


    ///////////////////////////////
//Registration Service
    public boolean updateIsActivatedById(int ucd_id, boolean isActivated) {
        if(studentsMapper.updateIsActivatedById(ucd_id, isActivated)==1){
            return true;
        }
        return false;
    }
    public boolean updateActivationCodeById(int ucd_id, String activationCode) {
        if(studentsMapper.updateActivationCodeById(ucd_id,activationCode)==1){
            return true;
        }
        return false;
    }
    public boolean updatePwdById(int ucd_id, String pwd) {
        if(studentsMapper.updatePwdById(ucd_id,pwd)==1){
            return true;
        }
        return false;
    }
    public boolean insertRole(int ucd_id){
        if(userMapper.insertRole(ucd_id,"student")==1){
            return true;
        }
        return false;
    }
//Process for registering Students
    //Step1: Set basic information for student including activation_code, except password
    public boolean initialRegisterStudent(Students stu, String activationCode) {
        Major major = studentsMapper.queryMajorByCode(stu.getMajor_code());
        boolean major_flag = false;
        boolean student_flag = false;
        boolean activation_flag = false;
        boolean role_flag = false;
        if(major==null){
            String title = "";
            if(stu.getMajor_code().equals("EIE")){
                title = "Electronic Information Engineering";
            }
            else if(stu.getMajor_code().equals("IOT")){
                title = "Internet Of Things";
            }
            else if(stu.getMajor_code().equals("SE")){
                title = "Software Engineering";
            }
            if(studentsMapper.insertMajor(new Major(stu.getMajor_code(),title))==1){
                major_flag=true;
            }
        }
        else{major_flag=true;}

        if(insertRole(stu.getUcd_id())){
            role_flag = true;
        }

        if(studentsMapper.registerStudent(stu)==1){
            student_flag = true;
        }

        if(studentsMapper.updateActivationCodeById(stu.getUcd_id(),activationCode)==1){
            activation_flag = true;
        }
        return major_flag&&role_flag&&student_flag&&activation_flag;
    }
    //Step2: When students click the link in Email, check if the activation code is the same as the one stored in DB
    //to do authentic check then redirect to the page for setting password.
    public String queryActivationCodeById(int ucd_id) {
        return studentsMapper.queryActivationCodeById(ucd_id);
    }
    //Step3: After the password is set, the activation code will expire(be removed) and the activation status will change.
    public boolean regComplete(int ucd_id, String pwd) {
        boolean pwd_flag = updatePwdById(ucd_id,pwd);
        boolean activate_flag = updateIsActivatedById(ucd_id,true);
        boolean code_flag = updateActivationCodeById(ucd_id,null);
        return pwd_flag&&code_flag&&activate_flag;
    }
//////////////////////////////
//Registration History
    public void insertStudentHistory(Registration_History history) {
        adminMapper.insertStudentHistory(history);
    }
    public List<Registration_History> queryStudentHistoryByTimeAndStatus(String time, String status) {
        return adminMapper.queryStudentHistoryByTimeAndStatus(time,status);
    }
//////////////////////////////

}
