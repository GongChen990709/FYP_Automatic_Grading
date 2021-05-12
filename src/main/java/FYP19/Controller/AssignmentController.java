package FYP19.Controller;

import FYP19.Entities.Assignment;
import FYP19.Service.AssignmentService;
import FYP19.Service.StudentsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AssignmentController {
    @Autowired
    @Qualifier("AssignmentService")
    private AssignmentService assignmentService;

    //Upload assignment requirements in pdf format
    @RequestMapping("/assignment/form")
    @ResponseBody
    public Map<String,String> assignmentFormUpload(@RequestBody Map<String,String> map, HttpServletRequest request) throws ParseException {
        Map<String, String> returnMap = new HashMap<String, String>();
        String id = UUID.randomUUID().toString().replace("-","");
        returnMap.put("code","1");  //failed
        String title = map.get("title");
        String description = map.get("description");
        String module_code = map.get("module_code");
        String due_date = map.get("due_date");
        boolean flag = assignmentService.insertAssignmentForms(id, title,description,module_code,due_date,request);
        if(flag==true){
            returnMap.put("code", "0"); //success
            returnMap.put("id", id);
            return returnMap;
        }
        return returnMap;
    }


    @RequestMapping("/teacher/allAssignments")
    @ResponseBody
    public String allTeacherAssignments(HttpServletRequest request) {
        String module_code = request.getParameter("module_code");
        List<Assignment> assignmentList = assignmentService.queryAssignmentsByCreatorIdAndModule(module_code,request);
        List<Map<String,String>> resultList = new ArrayList<Map<String, String>>();
        for(Assignment ass : assignmentList){
            Map<String,String> resultMap = new HashMap<String, String>();
            String id = ass.getId();
            String title = ass.getTitle();
            Date due_date = ass.getDue_date();
            Date creation_date = ass.getCreation_date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String due_datetime = sdf.format(due_date);
            String creation_datetime = sdf.format(creation_date);
            resultMap.put("id",id);
            resultMap.put("title",title);
            resultMap.put("due_date",due_datetime);
            resultMap.put("creation_date",creation_datetime);
            resultList.add(resultMap);
        }
        int count = assignmentList.size();
        String json1= JSON.toJSONString(resultList);
        System.out.println(json1);

        String layui_json1 = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json1+"}";
        return layui_json1;
    }




}
