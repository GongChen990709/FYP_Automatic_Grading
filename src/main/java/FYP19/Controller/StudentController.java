package FYP19.Controller;

import FYP19.Entities.Student;
import FYP19.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController{
    @Autowired
    @Qualifier("StudentServiceImpl")
    private StudentService studentService;
    @RequestMapping("/queryAllStudents")
    public String queryAllStudents(Model model){
        System.out.println("Enter================");
        List<Student> students = studentService.queryAllStudents();
        model.addAttribute("msg", "Hello world");
        model.addAttribute("studentList", students);
        return "/jsp/allStudent.jsp"; //Default: forward
    }

}
