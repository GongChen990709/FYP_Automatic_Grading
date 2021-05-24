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
import java.util.HashMap;
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

    @RequestMapping("/allActivatedStudents")
    @ResponseBody
    public List<Students> allStudents(HttpServletRequest request){
        return studentService.allActivatedStudents();
    }

    @RequestMapping("/deleteStudent")
    @ResponseBody
    public Map<String,String> deleteStudent(@RequestBody Map<String, String> req){
        Map<String,String> returnMap = new HashMap<>();
        String ucd_id = req.get("ucd_id");
        try{
            studentService.deleteStudentById(Integer.parseInt(ucd_id));
            returnMap.put("status","true");
        }
        catch (Exception e){
            returnMap.put("status","false");
        }
        return returnMap;
    }


    @RequestMapping("/allActivatedStudentsUnderModule")
    @ResponseBody
    public List<Map<String,Object>> studentsUnderOneModule(@RequestBody Map<String, String> req){
        String module_code = req.get("module_code");
        return studentService.studentsUnderOneModule(module_code);
    }


    @RequestMapping("/deregisterModule")
    @ResponseBody
    public Map<String,String> deregisterModule(@RequestBody Map<String, String> req){
        Map<String, String> returnMap = new HashMap<>();
        String ucd_id = req.get("ucd_id");
        String module_code = req.get("module_code");
        try{
            studentService.deregisterModule(module_code, Integer.parseInt(ucd_id));
            returnMap.put("status","success");
        }
        catch (Exception e){
            returnMap.put("status","failed");
        }
        return returnMap;
    }

    @RequestMapping("/allActivatedStudentsNotUnderModule")
    @ResponseBody
    public List<Map<String,Object>> studentsNotUnderOneModule(@RequestBody Map<String, String> req){
        String module_code = req.get("module_code");
        return studentService.studentsNotUnderOneModule(module_code);
    }


    @RequestMapping("/batchRegisterToModule")
    @ResponseBody
    public Map<String,String> batchRegisterToModule(@RequestBody Map<String, Object> req){
        Map<String, String> returnMap = new HashMap<>();
        String module_code = (String) req.get("module_code");
        List<String> ucd_ids = (List<String>) req.get("ucd_id");
        try{
            for(String ucd_id : ucd_ids){
                studentService.registerModule(module_code,Integer.parseInt(ucd_id));
            }
            returnMap.put("status","success");
        }
        catch (Exception e){
            returnMap.put("status","failed");
        }
        return returnMap;
    }




















}
