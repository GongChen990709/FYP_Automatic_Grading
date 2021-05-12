package FYP19.Controller;

import FYP19.Entities.Assignment;
import FYP19.Entities.Module;
import FYP19.Entities.Teacher;
import FYP19.Service.TeacherService;
import FYP19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    @Qualifier("TeacherServiceImpl")
    private TeacherService teacherService;

    @RequestMapping("/allModules")
    @ResponseBody
    public List<Object> allModules(HttpServletRequest request){
        List<Object> resultList = new ArrayList<Object>();
        Teacher teacher = (Teacher) request.getSession().getAttribute(Constants.USER_SESSION);
        resultList.add(teacher);
        List<Module> moduleList= teacherService.queryAllModules(teacher.getId());
        resultList.addAll(moduleList);
        return resultList;
    }



}
