import FYP19.Entities.Student;
import FYP19.Service.StudentService;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentServiceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    StudentService studentServiceIImp = (StudentService) context.getBean("StudentServiceImpl");


    @Test
    public void test(){
        System.out.println(12345);
    }

    @Test
    public void queryAllStudents(){
        for(Student stu : studentServiceIImp.queryAllStudents()){
            System.out.println(stu);
        }
    }

    @Test
    public void deleteStudentByNum(){
        studentServiceIImp.deleteStudentByNum(2);
    }

    @Test
    public void addStudent(){
        studentServiceIImp.addStudent(new Student(17371122,"jack","123",4));
    }

    @Test
    public void updateStudent(){
        studentServiceIImp.updateStudent(new Student(17371117,"mile","123456",4));
    }


}
