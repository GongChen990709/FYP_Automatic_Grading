package FYP19.Controller;

import FYP19.AutoGrading.AssignmentTester.AssignmentResultTester;
import FYP19.AutoGrading.JSONConverter.utils.JsonFileReader;
import FYP19.Service.AssignmentService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GradingController {
    @Autowired
    @Qualifier("AssignmentService")
    private AssignmentService assignmentService;


    @RequestMapping("/assignment/grading")
    @ResponseBody
    public Map<String,String> grade(@RequestBody JSONObject obj){
        Map<String,String> returnMap = new HashMap<>();
        String assignment_id = obj.getString("assignment_id");
        String dataPath = assignmentService.getDataPathById(assignment_id);
       // String dataType = JsonFileReader.readJsonFile("D:/study/bishe/test_tem2.json");
       // String data = JsonFileReader.readJsonFile("D:/study/bishe/test_tem2TestData.json");
        JSONArray student_ids = obj.getJSONArray("student_id");
        for(Object o: student_ids){
            int student_id = (int)o;
            System.out.println("StudentID=========" + student_id);
            //String teacherJavaPath = assignmentService.getJavaPathById(assignment_id);
        }
        returnMap.put("code","0") ;
        return returnMap;
    }


}
