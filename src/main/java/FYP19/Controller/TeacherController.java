package FYP19.Controller;

import FYP19.Entities.Assignment;
import FYP19.Entities.Module;
import FYP19.Entities.Teacher;
import FYP19.Service.TeacherService;
import FYP19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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


    @RequestMapping("/associatedModule")
    @ResponseBody
    public List<Map<String,Object>> associatedModule(){
        return teacherService.associatedModule();
    }


    @RequestMapping("/allTeacherInfo")
    @ResponseBody
    public List<Map<String,Object>> allTeacherInfo(){
        return teacherService.allTeacherInfo();
    }

    @RequestMapping("/registerModule")
    @ResponseBody
    public Map<String,String> registerModule(@RequestBody Module module){
        Map<String,String> returnMap = new HashMap<>();
        try{
            teacherService.registerModule(module);
            returnMap.put("status","success");
        }
        catch (Exception e){
            returnMap.put("status","failed");
        }
        return returnMap;
    }

    @RequestMapping("/deleteModule")
    @ResponseBody
    public Map<String,String> deleteModule(@RequestBody Map<String, String> req, HttpServletRequest request){
        Map<String,String> returnMap = new HashMap<>();
        String module_code = req.get("module_code");
        try{
            teacherService.deleteModule(module_code, request);
            returnMap.put("status","success");
        }
        catch (Exception e){
            returnMap.put("status","failed");
        }
        return returnMap;
    }

}
