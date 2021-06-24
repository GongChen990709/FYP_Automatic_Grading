package FYP19.Controller;
import FYP19.Entities.Registration_History;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.Service.*;
import FYP19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

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

    @RequestMapping("/doLogin")
    @ResponseBody //return value in JSON format and will not be resolved as a path to view resolver when added this annotation
    public Map<String, String> doLogin(@RequestBody Map<String,String> map, HttpServletRequest req){
        Map<String, String> message = userService.loginCheck(map,req);
        System.out.println(req.getSession().getServletContext().getRealPath("/upload"));
        System.out.println("Controller===="+ message);
        return message;
    }

    @RequestMapping("/doLogOut")
    @ResponseBody
    public Map<String, String> logOut(HttpServletRequest request){
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("status","true");
        request.getSession().removeAttribute(Constants.USER_SESSION);
        return resultMap;
    }

//Controller Mappings for registering student(s）and teacher(s)
    @RequestMapping("/register/sendEmailToStudent")
    @ResponseBody
    public Map<String, String> sendEmailToStudent(HttpServletRequest request, @RequestBody Students student) throws MessagingException {
        Map<String, String> resultMap = new HashMap<String, String>();
        String uuid = UUID.randomUUID().toString();
      boolean email_flag = false;
//        try {
         email_flag = mailService.sendEmailToStudent(student,uuid);
//        }
//        catch (MessagingException e) {
//
//        }
//        catch (MailSendException e){
//            resultMap.put("status","err2");
//            return resultMap;
//        }
        boolean student_flag = false;
        if(email_flag==true) {
            try{
                student_flag = studentService.initialRegisterStudent(student, uuid);
            }
            catch (DuplicateKeyException e){
                resultMap.put("status","err1");
                return resultMap;
            }
        }
        if(student_flag&&email_flag){
            resultMap.put("status","true");
        }
        return resultMap;
    }

    @RequestMapping("/register/sendEmailToMultipleStudents")
    @ResponseBody
    public Map<String, String> sendEmailToStudents(HttpServletRequest request, @RequestBody List<Students> studentList) {
        Map<String, String> resultMap = new HashMap<String, String>();
        int email_count = 0;
        int failure_count = 0;
        Date creation_date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creation_datetime = sdf.format(creation_date);
        String status="FAILED";
        for(Students stu : studentList){
            String uuid = UUID.randomUUID().toString();
            Registration_History stuHistory = new Registration_History(uuid,creation_datetime, stu.getUcd_id(), stu.getName(), stu.getEmail(), "student");
            stuHistory.setMajor_code(stu.getMajor_code());
            boolean email_flag = false;
            try{
                email_flag = mailService.sendEmailToStudent(stu,uuid);
            }
            catch (MessagingException e) {

            }
            catch (MailSendException e){
                status = "FAILED: Mail Sending Error";
            }
            boolean student_flag = false;
            if(email_flag==true) {
                try{
                    student_flag = studentService.initialRegisterStudent(stu, uuid);
                }
                catch (DuplicateKeyException e){
                    status = "FAILED: Duplicate Student ID";
                }
            }
            if(student_flag&&email_flag){
                email_count++;
                request.getSession().setAttribute(Constants.SUCCESS_EMAIL_COUNT, email_count);
                stuHistory.setStatus("SUCCESS");
                studentService.insertStudentHistory(stuHistory);
            }
            else{
                failure_count++;
                request.getSession().setAttribute(Constants.FAILED_EMAIL_COUNT, failure_count);
                stuHistory.setStatus(status);
                studentService.insertStudentHistory(stuHistory);
            }
        }
        request.getSession().setAttribute(Constants.REGISTRATION_TIME, creation_datetime);
        resultMap.put("status","success");
        return resultMap;
    }

    @RequestMapping("/register/emailCounts")
    @ResponseBody
    public Map<String, Integer> EmailCounts(HttpServletRequest request){
        Map<String, Integer> resultMap = new HashMap<>();
        int successNum=0;
        int failNum=0;
        if(request.getSession().getAttribute(Constants.SUCCESS_EMAIL_COUNT)!=null){
            successNum = (Integer) request.getSession().getAttribute(Constants.SUCCESS_EMAIL_COUNT);
        }
        if(request.getSession().getAttribute(Constants.FAILED_EMAIL_COUNT)!=null){
            failNum = (Integer) request.getSession().getAttribute(Constants.FAILED_EMAIL_COUNT);
        }
        resultMap.put("successNum",successNum);
        resultMap.put("failNum",failNum);
        return resultMap;
    }

    @RequestMapping("/register/doStudentActivation")
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

    @RequestMapping("/register/setStuInitialPwd")
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


    @RequestMapping("/register/sendEmailToTeacher")
    @ResponseBody
    public Map<String, String> sendEmailToTeacher(HttpServletRequest request, @RequestBody Teacher teacher){
        Map<String, String> resultMap = new HashMap<String, String>();
        String uuid = UUID.randomUUID().toString();
        boolean email_flag = false;
        try {
            email_flag = mailService.sendEmailToTeacher(teacher,uuid);
        }
        catch (MessagingException e) {

        }
        catch (MailSendException e){
            resultMap.put("status","err2");
            return resultMap;
        }
        boolean teacher_flag = false;
        if(email_flag==true) {
            try{
                teacher_flag = teacherService.initialRegisterTeacher(teacher, uuid);
            }
            catch (DuplicateKeyException e){
                resultMap.put("status","err1");
                return resultMap;
            }
        }
        if(teacher_flag&&email_flag){
            resultMap.put("status","true");
        }
        return resultMap;
    }


    @RequestMapping("/register/sendEmailToMultipleTeachers")
    @ResponseBody
    public Map<String, String> sendEmailToMultipleTeacher(HttpServletRequest request, @RequestBody List<Teacher> teacherList){
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("status","failed");
        int email_count = 0;
        request.getSession().setAttribute(Constants.SUCCESS_EMAIL_COUNT, email_count);
        for(Teacher teacher : teacherList){
            String uuid = UUID.randomUUID().toString();
            boolean email_flag = false;
            try {
                email_flag = mailService.sendEmailToTeacher(teacher,uuid);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
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


    @RequestMapping("/register/doTeacherActivation")
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


    @RequestMapping("/register/setTeaInitialPwd")
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

    @RequestMapping("/register/successStudents")
    @ResponseBody
    public List<Registration_History> successStudentsList(HttpServletRequest request){
        String reg_datetime = (String) request.getSession().getAttribute(Constants.REGISTRATION_TIME);
        return studentService.queryStudentHistoryByTimeAndStatus(reg_datetime,"SUCCESS","student");
    }

    @RequestMapping("/register/failedStudents")
    @ResponseBody
    public List<Registration_History> failedStudentsList(HttpServletRequest request){
        String reg_datetime = (String) request.getSession().getAttribute(Constants.REGISTRATION_TIME);
        return studentService.queryStudentHistoryByTimeAndStatus(reg_datetime,"FAILED", "student");
    }
    //////////////////////////////////////////////////////////////////////////
    @RequestMapping("/register/student/allHistoryDates")
    @ResponseBody
    public Map<String,List<String>> allStuHistoryDates() {
        List<String> history_dates = studentService.queryAllHistoryDates();
        Map<String,List<String>> returnMap = new HashMap<>();
        returnMap.put("history_dates",history_dates);
        return returnMap;
    }

    @RequestMapping("/register/history/successStudents")
    @ResponseBody
    public List<Registration_History>  successStuHistoryByDate(@RequestBody Map<String,String> map) {
        String history_date = map.get("history_date");
        return studentService.queryStudentHistoryByTimeAndStatus(history_date,"SUCCESS", "student");
    }

    @RequestMapping("/register/history/failedStudents")
    @ResponseBody
    public List<Registration_History> failedStuHistoryByDate(@RequestBody Map<String,String> map) {
        String history_date = map.get("history_date");
        return studentService.queryStudentHistoryByTimeAndStatus(history_date,"FAILED", "student");
    }





}
