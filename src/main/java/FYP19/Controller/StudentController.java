package FYP19.Controller;

import FYP19.Entities.Module;
import FYP19.Entities.Student;
import FYP19.Entities.Students;
import FYP19.Service.StudentService;
import FYP19.Service.StudentsService;
import FYP19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController{
    @Autowired
    @Qualifier("StudentsServiceImpl")
    private StudentsService studentService;
    @RequestMapping("/allModules")
    @ResponseBody
    public List<Module> queryAllModules(HttpServletRequest request){
        Students student  = (Students) request.getSession().getAttribute(Constants.USER_SESSION);
        List<Module> modules = studentService.getAllModules(student.getUcd_id());
        return modules;
    }

    @RequestMapping("/oneModule")
    @ResponseBody
    public Module queryOneModule(@RequestBody Map<String,String> map, HttpServletRequest request){
        String moduleName = map.get("moduleName");
        Module module = studentService.getModuleByName(moduleName);
        return module;
    }


}
