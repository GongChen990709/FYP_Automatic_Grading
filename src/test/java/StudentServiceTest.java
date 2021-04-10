import FYP19.Entities.Student;
import FYP19.Entities.Students;
import FYP19.Service.StudentService;
import FYP19.Service.StudentsService;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentServiceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    StudentsService studentsServiceIImp = (StudentsService) context.getBean("StudentsServiceImpl");


    @Test
    public void test(){
        System.out.println(12345);
    }

    @Test
    public void queryAllStudents(){
       Students stu = studentsServiceIImp.queryStudentById(172059141);
        System.out.println(stu);
    }


}
