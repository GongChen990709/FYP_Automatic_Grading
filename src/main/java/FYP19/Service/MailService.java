package FYP19.Service;

import FYP19.Dao.StudentsMapper;
import FYP19.Entities.Major;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.util.Constants;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class MailService {
    private JavaMailSender javaMailSender;
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmailToStudent(Students stu, String activation_code) throws MessagingException {
        String controllerPath = "http://localhost:8080/autoGrading/register/doStudentActivation";
        String content = "<html>"+
                "<body>"+
                "Hello "+ stu.getName() + "<br>Your Student id is: "+stu.getUcd_id()+". Please click the link below to activate your account<br>" +
                "<a href= " + controllerPath + "?code="+ activation_code +"&ucd_id="+stu.getUcd_id()+">Click to activate your account</a>"+
                "</body>"+
                "</html>";
        return sendEmail(stu.getEmail(),"FYP19: Activate your account", content);
    }

    public boolean sendEmailToTeacher(Teacher teacher, String activation_code) throws MessagingException {
        String controllerPath = "http://localhost:8080/autoGrading/register/doTeacherActivation";
        String content = "<html>"+
                "<body>"+
                "Hello "+ teacher.getName() + "<br>Your Teacher id is: "+teacher.getId()+". Please click the link below to activate your account<br>" +
                "<a href= " + controllerPath + "?code="+ activation_code +"&teacher_id="+teacher.getId()+">Click to activate your account</a>"+
                "</body>"+
                "</html>";
        return sendEmail(teacher.getEmail(),"FYP19: Activate your account", content);
    }

    public boolean sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage mineMsg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mineMsg, true, "utf-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(Constants.SITE_EMAIL);
        helper.setText(content,true);
        javaMailSender.send(mineMsg);
        return true;
    }



}
