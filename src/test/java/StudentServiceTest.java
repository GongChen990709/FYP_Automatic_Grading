import FYP19.Entities.Assignment;
import FYP19.Entities.Module;
import FYP19.Entities.Students;
import FYP19.Service.*;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.jvm.hotspot.tools.jcore.ByteCodeRewriter;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StudentServiceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    StudentsService studentsServiceIImp = (StudentsService) context.getBean("StudentsServiceImpl");
    TeacherService teacherServiceIImp = (TeacherService) context.getBean("TeacherServiceImpl");

    MailService mailService = (MailService) context.getBean("MailService");
    EncryptionService encryptionService = (EncryptionService) context.getBean("EncryptionService");

     AssignmentService assignmentService = (AssignmentService) context.getBean("AssignmentService");


    @Test
    public void test(){
        System.out.println(12345);
    }

    @Test
    public void queryAllStudents(){
       Students stu = studentsServiceIImp.queryStudentById(172059141);
        System.out.println(stu);
    }



    @Test
    public void aa(){
        teacherServiceIImp.registerModule(new Module("COMP2003J","Object-Oriented Programming",2));
        teacherServiceIImp.registerModule(new Module("COMP2004J","Database and Info System",2));
        teacherServiceIImp.registerModule(new Module("COMP2005J","Object-Oriented Design",2));
        teacherServiceIImp.registerModule(new Module("COMP2006J","Cloud Computing",2));
        teacherServiceIImp.registerModule(new Module("COMP2007J","Python",2));
    }

    @Test
    public void test11(){
        System.out.println(studentsServiceIImp.queryActivationCodeById(3123));
    }


    @Test
    public void test1112(){
        System.out.println(studentsServiceIImp.queryAllModules(17));
    }

//    @Test
//    public void textEmail(){
//        //studentsServiceIImp.registerStudent(new Students(11111,"helloWorld",null,"chen.gong@ucdconnect.ie","EEEN"));
//        mailService.registerStudent(new Students(11111,"helloWorld",null,"chen.gong@ucdconnect.ie","EEEN"));
//    }

    @Test
    public void aaa(){
        String salt = BCrypt.gensalt();
        System.out.println(salt);
        String pwd = BCrypt.hashpw("gc",salt);
        System.out.println(pwd);
    }

    @Test
    public void path() throws ParseException {
        //String uuid = UUID.randomUUID().toString();
        //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date datetime = formatter.parse("1969-01-01 02:00:00");
        //System.out.println(datetime.toString());
        //assignmentService.insertAssignment(new Assignment(uuid,datetime,"assignment1","suanfa"));
        Assignment ass = assignmentService.queryAssignment("assignment1");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(ass.getDue_date());
        System.out.println(dateString);
    }

    //获取当前时间并转换为String
    @Test
    public void tes111(){
        //Date currentTime = new Date();
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date dateString = formatter.format(currentTime);

        //ParsePosition pos = new ParsePosition(8);
        //Date currentTime_2 = formatter.parse(dateString, pos);
        //System.out.println(currentTime_2.toString());
    }

    @Test
    public void tesaas(){
        System.out.println(studentsServiceIImp.countStudentNumByCode("COMP"));
    }





}
