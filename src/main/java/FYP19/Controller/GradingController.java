package FYP19.Controller;

import FYP19.AutoGrading.AssignmentTester.AssignmentResultTester;
import FYP19.AutoGrading.AssignmentTester.BenchmarkStarter;
import FYP19.AutoGrading.JSONConverter.utils.JsonFileReader;
import FYP19.AutoGrading.ReflectionImp.interfaces.ReflectionCallerErrorException;
import FYP19.AutoGrading.executor.JavaCompile;
import FYP19.Service.AssignmentService;
import FYP19.util.Constants;
import FYP19.util.FileUtils;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.openjdk.jmh.runner.RunnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
public class GradingController {
    @Autowired
    @Qualifier("AssignmentService")
    private AssignmentService assignmentService;


    @RequestMapping("/assignment/grading")
    @ResponseBody
    public Map<String,String> grade(@RequestBody JSONObject obj, HttpServletRequest request) throws ReflectionCallerErrorException, RunnerException {
        int count = 0;
        request.getSession().setAttribute(Constants.GRADED_COUNT,count);
        Map<String,String> returnMap = new HashMap<>();
        String assignment_id = obj.getString("assignment_id");

        String dataPath = assignmentService.getDataPathById(assignment_id);
        String dataTypePath = assignmentService.getDataTypePathById(assignment_id);

        String teacherSourcePath = assignmentService.getJavaPathById(assignment_id);
        String teacherDestPath = teacherSourcePath.substring(0, teacherSourcePath.lastIndexOf("/"));

        String data = JsonFileReader.readJsonFile(dataPath);
        String dataType = JsonFileReader.readJsonFile(dataTypePath);

        JavaCompile javaCompile = new JavaCompile();
        javaCompile.compile(teacherSourcePath,teacherDestPath);

        AssignmentResultTester tester = new AssignmentResultTester(dataType);
        tester.dataUpdate(data);

        String teacherClassPath = teacherDestPath;
        String teacherClassName = teacherSourcePath.substring(teacherSourcePath.lastIndexOf("/")+1, teacherSourcePath.lastIndexOf("."));

        Map<String,List<Object>> teacherResult = tester.classTest(teacherClassPath, teacherClassName);
        Map<String,List<List<Object>>> inputData = tester.getTestData();
        String constructor_name= tester.getConstructorName();
        JSONObject inputJsonData = JSONObject.fromObject(inputData);
        JSONObject teacherJsonResult = JSONObject.fromObject(teacherResult);


        JSONArray student_ids = obj.getJSONArray("student_id");
        request.getSession().setAttribute(Constants.STUDENTS_NEED_GRADING,student_ids.size());

//        BenchmarkStarter benchmarkStarter = new BenchmarkStarter();
//        benchmarkStarter.benchMarkStart(dataType,data,teacherClassPath,teacherClassName);

        for(Object o: student_ids) {
            int student_id = (int) o;
            String studentSourcePath = assignmentService.queryStudentSourcePath(student_id, assignment_id);
            String studentDestPath = studentSourcePath.substring(0, studentSourcePath.lastIndexOf("/"));
            javaCompile.compile(studentSourcePath, studentDestPath);
            String studentClassPath = studentDestPath;
            String studentClassName = studentSourcePath.substring(studentSourcePath.lastIndexOf("/") + 1, studentSourcePath.lastIndexOf("."));
            Map<String, List<Object>> studentResult = tester.classTest(studentClassPath, studentClassName);
            JSONObject studentJsonResult = JSONObject.fromObject(studentResult);
            generateGradingDetails(student_id, assignment_id, inputJsonData,teacherJsonResult,studentJsonResult,constructor_name);
            count++;
            request.getSession().setAttribute(Constants.GRADED_COUNT,count);
        }
        returnMap.put("code","0") ;
        return returnMap;
    }

    @RequestMapping("/grading/progress")
    @ResponseBody
    public Map<String, Float> gradedCounts(HttpServletRequest request){
        Map<String, Float> resultMap = new HashMap<>();
        float percentage = 0;
        int total = 1;
        int graded = 0;
        if(request.getSession().getAttribute(Constants.STUDENTS_NEED_GRADING)!=null){
            total = (Integer) request.getSession().getAttribute(Constants.STUDENTS_NEED_GRADING);
        }
        if(request.getSession().getAttribute(Constants.GRADED_COUNT)!=null){
            graded = (Integer) request.getSession().getAttribute(Constants.GRADED_COUNT);
        }
        percentage = (float) (graded/total*100);
        resultMap.put("percentage",percentage);
        return resultMap;
    }

    @RequestMapping("/teacher/viewGrades")
    @ResponseBody
    public String teacherViewGrades(HttpServletRequest request){
        String assignment_id = request.getParameter("assignment_id");
        List<Map<String, Object>> grades = assignmentService.teacherViewAllGrades(assignment_id);
        int count = grades.size();
        String grades_json = JSON.toJSONString(grades);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+grades_json+"}";
        return result_json;
    }

    @RequestMapping("/teacher/viewStudentReport")
    @ResponseBody
    public String teacherViewStudentReport(HttpServletRequest request){
        String assignment_id = request.getParameter("assignment_id");
        String ucd_id =  request.getParameter("student_id");
        String report_path = assignmentService.queryStuReportPath(Integer.parseInt(ucd_id),assignment_id);
        String report_data = JsonFileReader.readJsonFile(report_path);
        System.out.println(report_data);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+report_data+"}";
        return result_json;
    }








    public void generateGradingDetails(int ucd_id, String assignment_id, JSONObject inputData, JSONObject correctResult, JSONObject studentResult, String constructor_name){
        System.out.println("Input Data "+inputData);
        System.out.println("Correct result "+ correctResult);
        System.out.println("Student result "+ studentResult);
        int total=0;
        int correctCount=0;
        JSONArray result = new JSONArray();
        JSONObject obj = new JSONObject();
        inputData.remove(constructor_name);
        Iterator inputIter = inputData.entrySet().iterator();
        Iterator studentIter = studentResult.entrySet().iterator();
        Iterator teacherIter = correctResult.entrySet().iterator();
        while(inputIter.hasNext()){
            Map.Entry entry_input = (Map.Entry) inputIter.next();
            Map.Entry entry_studentOutput = (Map.Entry) studentIter.next();
            Map.Entry entry_teacherOutput = (Map.Entry) teacherIter.next();
            String methodName = (String) entry_input.getKey();
            JSONArray inputArray = (JSONArray) entry_input.getValue();
            JSONArray studentArray =  (JSONArray) entry_studentOutput.getValue();
            JSONArray teacherArray =  (JSONArray) entry_teacherOutput.getValue();
            for (int i = 0; i < inputArray.size(); i++) {
                boolean booleanResult = teacherArray.get(i).equals(studentArray.get(i));
                obj.put("FunctionName",methodName);
                obj.put("InputData",inputArray.get(i));
                obj.put("YourOutput",studentArray.get(i));
                obj.put("TeacherOutput",teacherArray.get(i));
                obj.put("Result", booleanResult);
                total++;
                if(booleanResult) correctCount++;
                result.add(obj);
            }
        }
        float grade = ((float) correctCount/total) * 100;
        String stu_source_path = assignmentService.queryStudentSourcePath(ucd_id,assignment_id);
        String grade_path = stu_source_path.substring(0, stu_source_path.lastIndexOf("/"))+"/result.json";
        System.out.println("=======GradePath "+grade_path);
        System.out.println("==========Assignment_Result"+ obj);
        try {
            FileUtils.writeToJsonFile(result, grade_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assignmentService.insertAssignmentAssessment(ucd_id,assignment_id,Float.toString(grade),grade_path);
    }

}
