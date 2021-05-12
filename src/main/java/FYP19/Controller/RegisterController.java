package FYP19.Controller;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.Service.*;
import FYP19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    @Qualifier("StudentsServiceImpl")
    private StudentsService studentService;

    @Autowired
    @Qualifier("TeacherServiceImpl")
    private TeacherService teacherService;
    @Autowired
    @Qualifier("MailService")
    private MailService mailService;

    @Autowired
    @Qualifier("EncryptionService")
    private EncryptionService encryptionService;

//////////////////////////////////////////////////////////////////////////
//Controller Mappings for registering student(s）and teacher(s)
    @RequestMapping("/sendEmailToStudent")
    @ResponseBody
    public Map<String, String> sendEmailToStudent(HttpServletRequest request, @RequestBody Students student){
        Map<String, String> resultMap = new HashMap<String, String>();
        String uuid = UUID.randomUUID().toString();
        boolean email_flag = mailService.sendEmailToStudent(student,uuid);
        boolean student_flag = false;
        if(email_flag==true) {
            student_flag = studentService.initialRegisterStudent(student, uuid);
        }
        if(student_flag&&email_flag){
            resultMap.put("status","true");
        }
        return resultMap;
    }

    @RequestMapping("/sendEmailToMultipleStudents")
    @ResponseBody
    public Map<String, String> sendEmailToStudent(HttpServletRequest request, @RequestBody List<Students> studentList){
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("status","failed");
        int email_count = 0;
        request.getSession().setAttribute(Constants.SUCCESS_EMAIL_COUNT, email_count);
        for(Students stu : studentList){
            String uuid = UUID.randomUUID().toString();
            boolean email_flag = mailService.sendEmailToStudent(stu,uuid);
            boolean student_flag = false;
            if(email_flag==true) {
                student_flag = studentService.initialRegisterStudent(stu, uuid);
            }
            if(student_flag&&email_flag){
                email_count++;
                request.getSession().setAttribute(Constants.SUCCESS_EMAIL_COUNT, email_count);
            }
        }
        if(studentList.size()==email_count){
            resultMap.put("status","success");
        }
        return resultMap;
    }

    @RequestMapping("/doStudentActivation")
    public void studentActivation(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        String activationCode = request.getParameter("code");
        String ucd_id = request.getParameter("ucd_id");
        String sitePath = request.getContextPath();
        System.out.println("Student Activation Check ucd_id "+ ucd_id);

        if(activationCode.equals(studentService.queryActivationCodeById(Integer.parseInt(ucd_id)))){
            resp.sendRedirect(sitePath+"/resources/html/student/StudentSetPwd.html");
        }
        else{
            request.getRequestDispatcher("/status/activationFail.html").forward(request,resp);
        }
    }

    @RequestMapping("/setStuInitialPwd")
    @ResponseBody
    public Map<String, String> setStuInitialPwd(@RequestBody Map<String,String> requestMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String pwd = requestMap.get("newPWD");
        String ucd_id = requestMap.get("id");
        String cipherPwd = encryptionService.BcryptStu(Integer.parseInt(ucd_id),pwd);
        boolean flag = studentService.regComplete(Integer.parseInt(ucd_id), cipherPwd);
        if (flag == true) {
            resultMap.put("status", "success");
        }
        return resultMap;
    }


    @RequestMapping("/sendEmailToTeacher")
    @ResponseBody
    public Map<String, String> sendEmailToTeacher(HttpServletRequest request, @RequestBody Teacher teacher){
        Map<String, String> resultMap = new HashMap<String, String>();
        String uuid = UUID.randomUUID().toString();
        boolean email_flag = mailService.sendEmailToTeacher(teacher,uuid);
        boolean teacher_flag = false;
        if(email_flag==true) {
            teacher_flag = teacherService.initialRegisterTeacher(teacher, uuid);
        }
        if(teacher_flag&&email_flag){
            resultMap.put("status","true");
        }
        return resultMap;
    }


    @RequestMapping("/sendEmailToMultipleTeachers")
    @ResponseBody
    public Map<String, String> sendEmailToMultipleTeacher(HttpServletRequest request, @RequestBody List<Teacher> teacherList){
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("status","failed");
        int email_count = 0;
        request.getSession().setAttribute(Constants.SUCCESS_EMAIL_COUNT, email_count);
        for(Teacher teacher : teacherList){
            String uuid = UUID.randomUUID().toString();
            boolean email_flag = mailService.sendEmailToTeacher(teacher,uuid);
            boolean teacher_flag = false;
            if(email_flag==true) {
                teacher_flag = teacherService.initialRegisterTeacher(teacher, uuid);
            }
            if(teacher_flag&&email_flag){
                email_count++;
                request.getSession().setAttribute(Constants.SUCCESS_EMAIL_COUNT, email_count);
            }
        }
        if(teacherList.size()==email_count){
            resultMap.put("status","success");
        }
        return resultMap;
    }


    @RequestMapping("/doTeacherActivation")
    public void teacherActivation(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        String activationCode = request.getParameter("code");
        String teacher_id = request.getParameter("teacher_id");
        String sitePath = request.getContextPath();
        System.out.println("Student Activation Check ucd_id "+ teacher_id);

        if(activationCode.equals(teacherService.queryActivationCodeById(Integer.parseInt(teacher_id)))){
            resp.sendRedirect(sitePath+"/resources/html/teacher/teacherSetPwd.html");
        }
        else{
            request.getRequestDispatcher("/status/activationFail.html").forward(request,resp);
        }
    }


    @RequestMapping("/setTeaInitialPwd")
    @ResponseBody
    public Map<String, String> setTeaInitialPwd(@RequestBody Map<String,String> requestMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String pwd = requestMap.get("newPWD");
        String teacherId = requestMap.get("teacherId");
        String cipherPwd = encryptionService.BcryptTea(Integer.parseInt(teacherId),pwd);
        boolean flag = teacherService.regComplete(Integer.parseInt(teacherId), cipherPwd);
        if (flag == true) {
            resultMap.put("status", "success");
        }
        return resultMap;
    }
//////////////////////////////////////////////////////////////////////////











}
