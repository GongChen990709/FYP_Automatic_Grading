import FYP19.Entities.Module;
import FYP19.Entities.Students;
import FYP19.Service.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class StudentServiceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    StudentsService studentsServiceIImp = (StudentsService) context.getBean("StudentsServiceImpl");
    TeacherService teacherServiceIImp = (TeacherService) context.getBean("TeacherServiceImpl");

    MailService mailService = (MailService) context.getBean("MailService");
    EncryptionService encryptionService = (EncryptionService) context.getBean("EncryptionService");


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
    public void getAllModules(){
        List<Module> modules = studentsServiceIImp.getAllModules(17205914);
        for(Module m : modules){
            System.out.println(m);
        }
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

    }

//    @Test
//    public void textEmail(){
//        //studentsServiceIImp.registerStudent(new Students(11111,"helloWorld",null,"chen.gong@ucdconnect.ie","EEEN"));
//        mailService.registerStudent(new Students(11111,"helloWorld",null,"chen.gong@ucdconnect.ie","EEEN"));
//    }



}
