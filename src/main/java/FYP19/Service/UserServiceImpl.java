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
//        if(identity.equals("student")){
//            System.out.println(studentsMapper.queryIsActivatedById(Integer.parseInt(id))+"dasdasdasdasdasdasdas");
//            if(studentsMapper.queryIsActivatedById(Integer.parseInt(id))==true) {
//                Students stu = studentsMapper.queryStudentById(Integer.parseInt(id));
//                if (stu != null) {
//                    if(pwdCheck(stu.getUcd_id(),pwd)){
//                        req.getSession().setAttribute(Constants.USER_SESSION, stu);
//                        msg = "Login Success";
//                    }
//                    else{
//                        msg = "Password incorrect";
//                    }
//                }
//            }
//            else{
//                msg = "Do not activate yet";
//            }
//        }
//
//        else if(identity.equals("teacher")){
//            Teacher teacher = teacherMapper.queryTeacherById(Integer.parseInt(id));
//            if(teacher!=null){
//                if(teacher.getPwd().equals(pwd)){
//                    req.getSession().setAttribute(Constants.USER_SESSION, teacher);
//                    msg = "";
//                }
//            }
//        }

        if(identity.equals("admin")){
            Administrator admin = adminMapper.queryAdminById(Integer.parseInt(id));
            if(admin.getPwd().equals(pwd)){
                req.getSession().setAttribute(Constants.USER_SESSION, admin);
                returnMap.put("status", "Login Success");
                returnMap.put("identity", "admin");
                return returnMap;
            }
        }

        returnMap.put("status", "Incorrect Password");
        return returnMap;
    }


    public boolean pwdCheck(int ucd_id, String plainPwd){
        String salt = studentsMapper.querySaltById(ucd_id);
        Students stu = studentsMapper.queryStudentById(ucd_id);
        if(BCrypt.hashpw(plainPwd,salt).equals(stu.getPwd())){
            return true;
        }
        return false;
    }

}
