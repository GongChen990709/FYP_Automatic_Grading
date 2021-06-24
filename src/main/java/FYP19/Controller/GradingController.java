package FYP19.Controller;
import FYP19.AutoGrading.AssignmentTester.AssignmentResultTester;
import FYP19.AutoGrading.JSONConverter.utils.JsonFileReader;
import FYP19.AutoGrading.ReflectionImp.interfaces.ReflectionCallerErrorException;
import FYP19.AutoGrading.executor.Executor2;
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
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class GradingController {
    @Autowired
    @Qualifier("AssignmentService")
    private AssignmentService assignmentService;

    @RequestMapping("/assignment/grading")
    @ResponseBody
    public Map<String,String> grade(@RequestBody JSONObject requestJSON, HttpServletRequest request) throws ReflectionCallerErrorException, IOException, InterruptedException {
        int count = 0;
        JSONArray student_ids = requestJSON.getJSONArray("student_id");
        String assignment_id = requestJSON.getString("assignment_id");
        request.getSession().setAttribute(Constants.GRADED_COUNT,count);
        request.getSession().setAttribute(Constants.STUDENTS_NEED_GRADING,student_ids.size());
        Map<String,String> returnMap = new HashMap<>();
        //Get paths data.json and dataType.json files
        String dataPath = assignmentService.getDataPathById(assignment_id);
        String dataTypePath = assignmentService.getDataTypePathById(assignment_id);
        //Read two files and convert to JSON String
        String data = JsonFileReader.readJsonFile(dataPath);
        String dataType = JsonFileReader.readJsonFile(dataTypePath);
        //Compile the source code of teacher, specify path for resulting .class file
        String teacherSourcePath = assignmentService.getJavaPathById(assignment_id);
        String teacherDestPath = teacherSourcePath.substring(0, teacherSourcePath.lastIndexOf("/"));
        JavaCompile javaCompile = new JavaCompile();
        javaCompile.compile(teacherSourcePath,teacherDestPath);
        //Process teacher's code
        AssignmentResultTester tester = new AssignmentResultTester(dataType);
        tester.dataUpdate(data);
        String teacherClassName = teacherSourcePath.substring(teacherSourcePath.lastIndexOf("/")+1, teacherSourcePath.lastIndexOf("."));
        Map<String,List<List<Object>>> inputData = tester.getTestData();
        Map<String,List<Object>> teacherResult = tester.classTest(teacherDestPath, teacherClassName);
        String constructor_name= tester.getConstructorName();
        String constructor_method = Arrays.toString(JSONArray.fromObject(inputData.get(constructor_name).get(0)).toArray());
        JSONObject inputJsonData = JSONObject.fromObject(inputData);
        JSONObject teacherJsonResult = JSONObject.fromObject(teacherResult);
//        BenchmarkStarter benchmarkStarter = new BenchmarkStarter();
//        benchmarkStarter.benchMarkStart(dataTypePath,dataPath,teacherClassPath,teacherClassName);
        for(Object o: student_ids) {
            int student_id = (int) o;
            String studentSourcePath = assignmentService.queryStudentSourcePath(student_id, assignment_id);
            String studentDestPath = studentSourcePath.substring(0, studentSourcePath.lastIndexOf("/"));
            javaCompile.compile(studentSourcePath, studentDestPath);
            String studentClassName = studentSourcePath.substring(studentSourcePath.lastIndexOf("/") + 1, studentSourcePath.lastIndexOf("."));
            Map<String, List<Object>> studentResult = tester.classTest(studentDestPath, studentClassName);
            JSONObject studentJsonResult = JSONObject.fromObject(studentResult);
            //Check time elapsed
            Executor2 executor = new Executor2();
            executor.resultShower(dataTypePath,dataPath,studentDestPath,studentClassName,Constants.BENCHMARK_TEST_PATH);
            String benchmark_result = JsonFileReader.readJsonFile(Constants.BENCHMARK_RESULT_PATH);
            com.alibaba.fastjson.JSONArray jmh_result = JSON.parseArray(benchmark_result);
            com.alibaba.fastjson.JSONObject jmh_object = (com.alibaba.fastjson.JSONObject) jmh_result.get(0);
            String measurementBatchSize = jmh_object.getString("measurementBatchSize");
            String time_ns = jmh_object.getJSONObject("primaryMetric").getString("score");
            //Check accuracy and generating grading result
            generateGradingDetails(student_id, assignment_id, inputJsonData,teacherJsonResult,studentJsonResult,constructor_name, measurementBatchSize, time_ns, constructor_method);
            count++;
            request.getSession().setAttribute(Constants.GRADED_COUNT,count);
        }
        returnMap.put("code","0");
        return returnMap;
    }

    public void generateGradingDetails(int ucd_id, String assignment_id, JSONObject inputData, JSONObject correctResult, JSONObject studentResult, String constructor_name, String measurementBatchSize, String time_ns, String constructor_method){
        int total_count=0;
        int correct_count=0;
        JSONObject result = new JSONObject();
        JSONArray accuracy_result = new JSONArray();
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
                total_count++;
                if(booleanResult) correct_count++;
                accuracy_result.add(obj);
            }
        }
        result.put("accuracy_result",accuracy_result);
        result.put("measurementBatchSize", measurementBatchSize);
        result.put("time_ns", time_ns);
        float accuracy_rate_raw = ((float) correct_count/total_count) * 100;
        float accuracy_rate = (float) new BigDecimal(accuracy_rate_raw).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        result.put("accuracy_rate", Float.toString(accuracy_rate));
        result.put("constructor_method",constructor_method);
        String stu_source_path = assignmentService.queryStudentSourcePath(ucd_id,assignment_id);
        String grade_path = stu_source_path.substring(0, stu_source_path.lastIndexOf("/"))+"/result.json";
        try {
            FileUtils.writeToJsonFile(JSON.parseObject(result.toString()), grade_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assignmentService.insertAssignmentAssessment(ucd_id,assignment_id,grade_path);
    }

    @RequestMapping("/grading/progress")
    @ResponseBody
    public Map<String, Float> gradedCounts(HttpServletRequest request){
        Map<String, Float> resultMap = new HashMap<>();
        float percentage_raw = 0;
        int total = 1;
        int graded = 0;
        if(request.getSession().getAttribute(Constants.STUDENTS_NEED_GRADING)!=null){
            total = (Integer) request.getSession().getAttribute(Constants.STUDENTS_NEED_GRADING);
        }
        if(request.getSession().getAttribute(Constants.GRADED_COUNT)!=null){
            graded = (Integer) request.getSession().getAttribute(Constants.GRADED_COUNT);
        }
        percentage_raw =  ((float) graded/total) * 100;
        float percentage = (float) new BigDecimal(percentage_raw).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        resultMap.put("percentage",percentage);
        return resultMap;
    }

    @RequestMapping("/releaseGrade")
    @ResponseBody
    public Map<String,String> teacherReleaseGrade(HttpServletRequest request){
        String assignment_id = request.getParameter("assignment_id");
        Map<String, String> returnMap = new HashMap<>();
        List<String> grade_detail_paths = assignmentService.queryAllGradeDetailPaths(assignment_id);
        System.out.println("Size======"+grade_detail_paths.size());

        long [] benchmark = new long[grade_detail_paths.size()];
        int count=0;
        for(String grade_detail_path : grade_detail_paths){
            String result_data = JsonFileReader.readJsonFile(grade_detail_path);
            System.out.println("result_data======="+result_data);
            com.alibaba.fastjson.JSONObject result_json_data = JSON.parseObject(result_data);
            String time_ns = result_json_data.getString("time_ns");
            long one_time = Long.parseLong(time_ns.substring(0,time_ns.lastIndexOf("."))); //ns
            System.out.println("one_time======="+one_time);

            benchmark[count] = one_time;
            count++;
        }
        long max = benchmark[0];
        long min = benchmark[0];
        for(int i=1;i<benchmark.length;i++){
            if(benchmark[i]>max){
                max=benchmark[i];
            }
            if(benchmark[i]<min){
                min=benchmark[i];
            }
        }
        List<Map<String,Object>> allAssessments = assignmentService.queryAllAssessment(assignment_id);
        for(Map<String,Object> assessment : allAssessments){
            String stu_detail_path = (String) assessment.get("grade_details_path");
            String result_data = JsonFileReader.readJsonFile(stu_detail_path);
            com.alibaba.fastjson.JSONObject stu_json_data = JSON.parseObject(result_data);
            String time_ns = stu_json_data.getString("time_ns");
            long stu_time = Long.parseLong(time_ns.substring(0,time_ns.lastIndexOf("."))); //ns
            float accuracy_rate = stu_json_data.getFloat("accuracy_rate");
            System.out.println("accuracy_rate=========="+ accuracy_rate);
            System.out.println("max======="+max);
            System.out.println("min======"+min);
            System.out.println("student time" + stu_time);
            float time_score = 0;
            if((max-min)==0){
                time_score = 100;
            }
            else {
                time_score = (1-((float)(stu_time-min)/(max-min))) * 100;
            }
            System.out.println("time_score======"+time_score);
            float grade_raw = (float) (accuracy_rate*0.9 + time_score*0.1);
            System.out.println("grade_raw======="+grade_raw);
            float grade = (float) new BigDecimal(grade_raw).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println("grade======="+grade);
            assignmentService.updateAssignmentGrade(grade,(int)assessment.get("student_id"),assignment_id);
        }
        List<Map<String,Object>> daoList = assignmentService.teacherViewSubmissions(assignment_id);
        for(Map<String,Object> index : daoList){
            if(index.get("submission_date")==null){
                assignmentService.insertAssignmentAssessment((int)index.get("ucd_id"),assignment_id,null);
                assignmentService.updateAssignmentGrade(0,(int)index.get("ucd_id"),assignment_id);
            }
        }
        returnMap.put("status", "success");
        return returnMap;
    }
}
