package FYP19.Controller;
import FYP19.Entities.Student;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.Service.StudentService;
import FYP19.Service.StudentsService;
import FYP19.Service.TeacherService;
import FYP19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    @Qualifier("StudentsServiceImpl")
    private StudentsService studentsService;
    @Autowired
    @Qualifier("TeacherServiceImpl")
    private TeacherService teacherService;

    @RequestMapping("/doLogin")
    @ResponseBody //return value in JSON format and will not be resolved as a path to view resolver when added this annotation
    public Map<String, String> queryAllStudents(@RequestBody Map<String,String> map, HttpServletRequest req){
        Map<String, String> resultMap = new HashMap<String, String>();
        String identity = map.get("identity");
        String id = map.get("id");
        String pwd = map.get("pwd");

        if(identity.equals("student")){
            Students stu = studentsService.queryStudentById(Integer.parseInt(id));
            if(stu!=null){
                if(stu.getPwd().equals(pwd)){
                    resultMap.put("status","Login Success");
                    req.getSession().setAttribute(Constants.USER_SESSION, stu);
                }
                else{
                    resultMap.put("status","Incorrect Password");
                }
            }
        }

        else if(identity.equals("teacher")){
            Teacher teacher = teacherService.queryTeacherById(Integer.parseInt(id));
            if(teacher!=null){
                if(teacher.getPwd().equals(pwd)){
                    resultMap.put("status","Login Success");
                    req.getSession().setAttribute(Constants.USER_SESSION, teacher);
                }
                else{
                    resultMap.put("status","Incorrect Password");
                }
            }
        }

        else{
            resultMap.put("status","Incorrect Id");
        }

        return resultMap;
    }



}
