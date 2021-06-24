package FYP19.Service;

import FYP19.Dao.AdminMapper;
import FYP19.Dao.StudentsMapper;
import FYP19.Dao.TeacherMapper;
import FYP19.Dao.UserMapper;
import FYP19.Entities.Administrator;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.util.Constants;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    private StudentsMapper studentsMapper;
    private TeacherMapper teacherMapper;
    private AdminMapper adminMapper;
    private UserMapper userMapper;

    public void setStudentsMapper(StudentsMapper studentsMapper) {
        this.studentsMapper = studentsMapper;
    }

    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public void setUserMapper(UserMapper userMapper){this.userMapper = userMapper;}


    public Map<String,String> loginCheck(Map<String,String> map, HttpServletRequest req) {
        String id = map.get("id");
        String pwd = map.get("pwd");
        Map<String,String> returnMap = new HashMap<String, String>();
        String identity = userMapper.queryRoleById(Integer.parseInt(id));

        if(identity==null){
            returnMap.put("status", "User Not Found: Check your id please");
            return returnMap;
        }

        if(identity.equals("admin")){
            int adminId = Integer.parseInt(id);
            Administrator admin = adminMapper.queryAdminById(Integer.parseInt(id));
            if(BCrypt.hashpw(pwd,adminMapper.querySaltById(adminId)).equals(admin.getPwd())){
                returnMap.put("status","Login Success");
                returnMap.put("identity","admin");
                req.getSession().setAttribute(Constants.USER_SESSION, admin);
                return returnMap;
            }
        }

        if(identity.equals("student")){
            int ucd_id = Integer.parseInt(id);
            boolean isActivated = studentsMapper.queryIsActivatedById(ucd_id);
            if(!isActivated){
                returnMap.put("status","Not Activated");
                return returnMap;
            }
            Students stu = studentsMapper.queryStudentById(ucd_id);
            if(BCrypt.hashpw(pwd,studentsMapper.querySaltById(ucd_id)).equals(stu.getPwd())){
                returnMap.put("status","Login Success");
                returnMap.put("identity","student");
                req.getSession().setAttribute(Constants.USER_SESSION, stu);
                return returnMap;
            }
        }

        if(identity.equals("teacher")){
            int t_id = Integer.parseInt(id);
            boolean isActivated = teacherMapper.queryIsActivatedById(t_id);
            if(!isActivated){
                returnMap.put("status","Not Activated");
                return returnMap;
            }
            Teacher teacher = teacherMapper.queryTeacherById(t_id);
            if(BCrypt.hashpw(pwd,teacherMapper.querySaltById(t_id)).equals(teacher.getPwd())){
                returnMap.put("status","Login Success");
                returnMap.put("identity","teacher");
                req.getSession().setAttribute(Constants.USER_SESSION, teacher);
                return returnMap;
            }
        }

        returnMap.put("status", "Password entered is incorrect.");
        return returnMap;
    }

}
