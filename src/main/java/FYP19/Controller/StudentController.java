package FYP19.Controller;

import FYP19.Entities.Module;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.Service.StudentsService;
import FYP19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public List<Object> allModules(HttpServletRequest request){
        List<Object> resultList = new ArrayList<Object>();
        Students student = (Students) request.getSession().getAttribute(Constants.USER_SESSION);
        resultList.add(student);
        List<Module> moduleList= studentService.queryAllModules(student.getUcd_id());
        resultList.addAll(moduleList);
        return resultList;
    }


}
