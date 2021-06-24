package FYP19.Controller;

import FYP19.AutoGrading.JSONConverter.utils.JsonFileReader;
import FYP19.Entities.Assignment;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.Service.AssignmentService;
import FYP19.Service.StudentsService;
import FYP19.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.collections.map.ListOrderedMap;
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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AssignmentController {
    @Autowired
    @Qualifier("AssignmentService")
    private AssignmentService assignmentService;

    @Autowired
    @Qualifier("StudentsServiceImpl")
    private StudentsService studentsService;

//////////////////////////////////////////////////////////////////
//Teacher Functions Unit
    //1. Adding an assignment
    //Basic information of the assignment
    @RequestMapping("/assignment/form")
    @ResponseBody
    public Map<String,String> assignmentFormUpload(@RequestBody Map<String,String> map) throws ParseException {
        Map<String, String> returnMap = new HashMap<String, String>();
        String id = UUID.randomUUID().toString().replace("-","");
        returnMap.put("code","1");  //failed
        String title = map.get("title");
        String description = map.get("description");
        String module_code = map.get("module_code");
        String due_date = map.get("due_date");
        boolean flag = assignmentService.insertAssignmentForms(id, title,description,module_code,due_date);
        if(flag==true){
            returnMap.put("code", "0"); //success
            returnMap.put("id", id);
            return returnMap;
        }
        return returnMap;
    }

    //2. Showing the assignments the teacher uploaded
    @RequestMapping("/teacher/allAssignments")
    @ResponseBody
    public String allTeacherAssignments(HttpServletRequest request) {
        String module_code = request.getParameter("module_code");
        List<Assignment> assignmentList = assignmentService.queryAssignmentsByModule(module_code);
        String resultJSON = teacherFormatConverter(assignmentList,"all");
        return resultJSON;
    }

    @RequestMapping("/teacher/allPublishedAssignments")
    @ResponseBody
    public String allTeacherPublishedAssignments(HttpServletRequest request) {
        String module_code = request.getParameter("module_code");
        List<Assignment> assignmentList = assignmentService.queryAssignmentByModule(module_code);
        String resultJSON = teacherFormatConverter(assignmentList,"part");
        return resultJSON;
    }

    //3. Modifying due date of published assignments
    @RequestMapping("/assignment/edit")
    @ResponseBody
    public Map<String,String> editAssignmentDueDate(@RequestBody Map<String,String> map){
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("code","1");  //failed
        String id = map.get("id");
        String due_date = map.get("due_date");
        boolean flag=false;
        try {
            flag = assignmentService.updateDueDateById(due_date,id);
        }
        catch(ParseException e){
            returnMap.put("errMsg","Invalid Date Format: Please Follow YYYY-MM-DD HH:MM:SS");
        }
        if(flag==true){
            returnMap.put("code", "0"); //success
            return returnMap;
        }
        return returnMap;
    }

    //4. Show all files teacher uploaded used in download area
    @RequestMapping("/assignment/allUploadedFiles")
    @ResponseBody
    public List<Map<String,String>> allUploadedFiles(HttpServletRequest request){
        String assignmentId = request.getParameter("id");
        List<Map<String,String>> resultList = new ArrayList<>();
        Map<String,String> filePaths = assignmentService.getFilePathsById(assignmentId);
        if(filePaths!=null){
            if(filePaths.get("pdf_path")!=null){
                Map<String,String> map = new HashMap<>();
                String pdf_name = filePaths.get("pdf_path").substring(filePaths.get("pdf_path").lastIndexOf("/")+1);
                map.put("fileName", pdf_name);
                map.put("type", "pdf");
                resultList.add(map);
            }

            if(filePaths.get("source_path")!=null){
                Map<String,String> map = new HashMap<>();
                String java_name = filePaths.get("source_path").substring(filePaths.get("source_path").lastIndexOf("/")+1);
                map.put("fileName", java_name);
                map.put("type", "java");
                resultList.add(map);
            }

            if(filePaths.get("data_path")!=null){
                Map<String,String> map = new HashMap<>();
                String data_name = filePaths.get("data_path").substring(filePaths.get("data_path").lastIndexOf("/")+1);
                map.put("fileName", data_name);
                map.put("type", "data");
                resultList.add(map);
            }

            if(filePaths.get("datatype_path")!=null){
                Map<String,String> map = new HashMap<>();
                String datatype_name = filePaths.get("datatype_path").substring(filePaths.get("datatype_path").lastIndexOf("/")+1);
                map.put("fileName", datatype_name);
                map.put("type", "dataType");
                resultList.add(map);
            }
        }
        return resultList;
    }

    //5. If teacher is not completed publishing the assignment, it returns which files are not submitted and which are submitted
    @RequestMapping("/assignment/fileStatus")
    @ResponseBody
    public Map<String,String> fileStatus(HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<>();
        String id = request.getParameter("assignment_id");
        System.out.println(id);
        returnMap.put("pdf","false");
        returnMap.put("java","false");
        returnMap.put("data","false");
        returnMap.put("dataType","false");
        if(assignmentService.getPdfPathById(id)!=null){
            returnMap.put("pdf","true");
        }
        if(assignmentService.getJavaPathById(id)!=null){
            returnMap.put("java","true");
        }
        if(assignmentService.getDataPathById(id)!=null){
            returnMap.put("data","true");
        }
        if(assignmentService.getDataTypePathById(id)!=null){
            returnMap.put("dataType","true");
        }
        return returnMap;
    }

    //6. Delete the assignment and all corresponding directory and files
    @RequestMapping("teacher/deleteAssignment")
    @ResponseBody
    public Map<String,String> deleteAssignment(@RequestBody Map<String,String> map, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<>();
        String id = map.get("id");
        try {
            assignmentService.deleteAssignmentById(id, request);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "1");
            return returnMap;
        }
        returnMap.put("code", "0");
        return returnMap;
    }

    //7. Find assignments by matching patterns of the assignment name
    @RequestMapping("/assignment/query")
    @ResponseBody
    public String fuzzyQueryAssignments(HttpServletRequest request){
        String title = request.getParameter("title");
        String module_code = request.getParameter("module_code");
        String isPublished = request.getParameter("isPublished");
        String option = "all";
        if(isPublished!=null){
            option = "part";
        }
        List<Assignment> assignmentList = assignmentService.fuzzyQueryByTitle(title,module_code);
        String json_result = teacherFormatConverter(assignmentList,option);
        return json_result;
    }

    //8. Show student No who registered the module and No of student submitted the assignment and No of student is not graded
    @RequestMapping("/assignment/summary")
    @ResponseBody
    public Map<String,Object> summaryInfo(HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>();
        String id = request.getParameter("assignment_id");
        String module_code = request.getParameter("module_code");
        String description = assignmentService.queryDescriptionById(id);
        String due_date = dateConverter(assignmentService.queryDueDateById(id));
        String name = assignmentService.queryNameById(id);
        int participants = studentsService.countStudentNumByCode(module_code);
        int submitted = assignmentService.countSubmittedById(id);
        int notGraded = assignmentService.countNotGradedById(id);
        returnMap.put("description",description);
        returnMap.put("participants",participants);
        returnMap.put("submitted",submitted);
        returnMap.put("notGraded",notGraded);
        returnMap.put("due_date",due_date);
        returnMap.put("name",name);
        return returnMap;
    }

    //9. View all students information and their submission status
    @RequestMapping("/assignment/viewSubmissions")
    @ResponseBody
    public String teacherViewSubmissions(HttpServletRequest request) {
        String assignment_id = request.getParameter("assignment_id");
        List<Map<String,Object>> returnList = new ArrayList<>();
        List<Map<String,Object>> daoList = assignmentService.teacherViewSubmissions(assignment_id);
        for(Map<String,Object> index : daoList){
            Map<String, Object> returnMap = new HashMap<>();
            int student_id = (int) index.get("ucd_id");
            String student_name = (String) index.get("name");
            returnMap.put("student_id",student_id);
            returnMap.put("student_name",student_name);
            returnMap.put("status","Not Submitted");
            returnMap.put("grading_status", "Not Graded");
            Date submission_date = (Date) index.get("submission_date");
            if(submission_date!=null){
                String submission_datetime = dateConverter(submission_date);
                String source_path = (String) index.get("source_path");
                returnMap.put("submission_date",submission_datetime);
                returnMap.put("fileName", source_path.substring(source_path.lastIndexOf("/")+1));
                returnMap.put("status","Submitted");
            }
            if(assignmentService.queryStuReportPath(student_id,assignment_id)!=null){
                returnMap.put("grading_status", "Graded");
            }
            returnList.add(returnMap);
        }
        int count = returnList.size();
        String assignment_json= JSON.toJSONString(returnList);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+assignment_json+"}";
        return result_json;
    }

    //10. view all student grade
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

    //11. view student grade by id
    @RequestMapping("/teacher/viewGradeById")
    @ResponseBody
    public String teacherViewGradeById(HttpServletRequest request){
        String assignment_id = request.getParameter("assignment_id");
        String ucd_id = request.getParameter("ucd_id");
        List<Map<String,Object>> returnList = assignmentService.teacherViewGradeById(assignment_id,Integer.parseInt(ucd_id));
        String grade_json = JSON.toJSONString(returnList);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+grade_json+"}";
        return result_json;
    }

    //12. view student grade by name
    @RequestMapping("/teacher/viewGradeByName")
    @ResponseBody
    public String teacherViewGradeByName(HttpServletRequest request){
        String assignment_id = request.getParameter("assignment_id");
        String name = request.getParameter("name");
        List<Map<String,Object>> returnList = assignmentService.teacherViewGradeByName(assignment_id,name);
        String grade_json = JSON.toJSONString(returnList);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+grade_json+"}";
        return result_json;
    }



    //////////////////////////
   //Common: Student and teacher report data
    @RequestMapping("/teacher/report/accuracy")
    @ResponseBody
    public String accuracyReport(HttpServletRequest request){
        String assignment_id = request.getParameter("assignment_id");
        String ucd_id =  request.getParameter("student_id");
        String report_path = assignmentService.queryStuReportPath(Integer.parseInt(ucd_id),assignment_id);
        if(report_path!=null){
            String report_data = JsonFileReader.readJsonFile(report_path);
            com.alibaba.fastjson.JSONObject report_json_data = JSON.parseObject(report_data);
            report_data = report_json_data.getString("accuracy_result");
            System.out.println(report_data);
            String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+report_data+"}";
            return result_json;
        }
        return "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+ "[]" +"}";
    }

    @RequestMapping("/student/report/accuracy")
    @ResponseBody
    public String accuracyReportStudent(HttpServletRequest request){
        String assignment_id = request.getParameter("assignment_id");
        int ucd_id =  ((Students) request.getSession().getAttribute(Constants.USER_SESSION)).getUcd_id();
        String report_path = assignmentService.queryStuReportPath(ucd_id,assignment_id);
        if(report_path!=null){
            String report_data = JsonFileReader.readJsonFile(report_path);
            com.alibaba.fastjson.JSONObject report_json_data = JSON.parseObject(report_data);
            report_data = report_json_data.getString("accuracy_result");
            System.out.println(report_data);
            String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+report_data+"}";
            return result_json;
        }
        return "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+ "[]" +"}";
    }


    @RequestMapping("/teacher/report/data")
    @ResponseBody
    public Map<String,Object> dataReport(HttpServletRequest request){
        Map<String,Object> returnMap = new HashMap<>();
        String assignment_id = request.getParameter("assignment_id");
        String ucd_id =  request.getParameter("student_id");
        String report_path = assignmentService.queryStuReportPath(Integer.parseInt(ucd_id),assignment_id);
        if(report_path!=null){
            String report_data = JsonFileReader.readJsonFile(report_path);
            com.alibaba.fastjson.JSONObject report_json_data = JSON.parseObject(report_data);
            String time_ns = report_json_data.getString("time_ns");
            String batchSize = report_json_data.getString("measurementBatchSize");
            String constructor_method = report_json_data.getString("constructor_method");
            String accuracy_rate = report_json_data.getString("accuracy_rate");
            String time_ns_reduced =time_ns.substring(0,time_ns.lastIndexOf("."));
            /////////
            returnMap.put("batchSize",batchSize);
            returnMap.put("time_ns",time_ns_reduced);
            returnMap.put("constructor_method",constructor_method);
            returnMap.put("accuracy_rate",accuracy_rate);
        }
        /////////
        Float grade = assignmentService.queryGrade(Integer.parseInt(ucd_id),assignment_id);
        returnMap.put("grade", Float.toString(grade));
        Float[] allFloatGrades = assignmentService.queryAllGrades(assignment_id);
        int [] counter = new int[10];
        float total=0;
        for(Float index : allFloatGrades){
            if(index>=0 && index<10){
                counter[0]++;
            }
            if(index>=10 && index<20){
                counter[1]++;
            }
            if(index>=20 && index<30){
                counter[2]++;
            }
            if(index>=30 && index<40){
                counter[3]++;
            }
            if(index>=40 && index<50){
                counter[4]++;
            }
            if(index>=50 && index<60){
                counter[5]++;
            }
            if(index>=60 && index<70){
                counter[6]++;
            }
            if(index>=70 && index<80){
                counter[7]++;
            }
            if(index>=80 && index<90){
                counter[8]++;
            }
            if(index>=90 && index<=100){
                counter[9]++;
            }
            total = total + index;
        }
        float averaged_grade = (float) new BigDecimal(total/allFloatGrades.length).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        returnMap.put("allGrades", counter);
        returnMap.put("averaged_grade",averaged_grade);
        return returnMap;
    }


    @RequestMapping("/student/report/data")
    @ResponseBody
    public Map<String,Object> dataReportStudent(HttpServletRequest request){
        Map<String,Object> returnMap = new HashMap<>();
        String assignment_id = request.getParameter("assignment_id");
        int ucd_id =  ((Students) request.getSession().getAttribute(Constants.USER_SESSION)).getUcd_id();
        String report_path = assignmentService.queryStuReportPath(ucd_id,assignment_id);
        if(report_path!=null){
            String report_data = JsonFileReader.readJsonFile(report_path);
            com.alibaba.fastjson.JSONObject report_json_data = JSON.parseObject(report_data);
            String time_ns = report_json_data.getString("time_ns");
            String batchSize = report_json_data.getString("measurementBatchSize");
            String constructor_method = report_json_data.getString("constructor_method");
            String accuracy_rate = report_json_data.getString("accuracy_rate");
            String time_ns_reduced =time_ns.substring(0,time_ns.lastIndexOf("."));
            /////////
            returnMap.put("batchSize",batchSize);
            returnMap.put("time_ns",time_ns_reduced);
            returnMap.put("constructor_method",constructor_method);
            returnMap.put("accuracy_rate",accuracy_rate);
        }
        /////////
        Float grade = assignmentService.queryGrade(ucd_id,assignment_id);
        returnMap.put("grade", Float.toString(grade));
        Float[] allFloatGrades = assignmentService.queryAllGrades(assignment_id);
        int [] counter = new int[10];
        float total=0;
        for(Float index : allFloatGrades){
            if(index>=0 && index<10){
                counter[0]++;
            }
            if(index>=10 && index<20){
                counter[1]++;
            }
            if(index>=20 && index<30){
                counter[2]++;
            }
            if(index>=30 && index<40){
                counter[3]++;
            }
            if(index>=40 && index<50){
                counter[4]++;
            }
            if(index>=50 && index<60){
                counter[5]++;
            }
            if(index>=60 && index<70){
                counter[6]++;
            }
            if(index>=70 && index<80){
                counter[7]++;
            }
            if(index>=80 && index<90){
                counter[8]++;
            }
            if(index>=90 && index<=100){
                counter[9]++;
            }
            total = total + index;
        }
        float averaged_grade = (float) new BigDecimal(total/allFloatGrades.length).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        returnMap.put("allGrades", counter);
        returnMap.put("averaged_grade",averaged_grade);
        return returnMap;
    }



    //////////////////

/////////////////////////////


//////////////////////////////////////////////////////////////////
//Student Functions Unit
    //1. view all assignments for a module the student registered
    @RequestMapping("/student/allAssignments")
    @ResponseBody
    public String allStudentAssignments(HttpServletRequest request) {
        String module_code = request.getParameter("module_code");
        List<Assignment> assignmentList = assignmentService.queryAssignmentByModule(module_code);
        int ucd_id = ((Students)request.getSession().getAttribute(Constants.USER_SESSION)).getUcd_id();
        String resultJSON = studentFormatConverter(assignmentList, ucd_id);
        return resultJSON;
    }

    //2. View detailed information of the assignment
    @RequestMapping("/student/viewAssignment")
    @ResponseBody
    public Map<String,String> studentViewAssignment(HttpServletRequest request){
        Map<String, String> returnMap = new HashMap<>();
        String assignment_id =  request.getParameter("assignment_id");
        Map<String, Object> viewAss = assignmentService.studentViewAssignment(assignment_id);
        Date due_date = (Date) viewAss.get("due_date");
        String due_datetime  = dateConverter(due_date);
        String pdf_path = (String)viewAss.get("pdf_path");
        returnMap.put("due_date", due_datetime);
        returnMap.put("name",(String) viewAss.get("title"));
        returnMap.put("description",(String) viewAss.get("description"));
        returnMap.put("fileName", pdf_path.substring(pdf_path.lastIndexOf("/")+1));
        return returnMap;
    }

    //3. View the file student uploaded and its uploaded date
    @RequestMapping("/student/submissionHistory")
    @ResponseBody
    public String submissionHistory(HttpServletRequest request){
        List<Map<String, String>> returnList = new ArrayList<>();
        Map<String, String> returnMap = new HashMap<>();
        int ucd_id = ((Students)request.getSession().getAttribute(Constants.USER_SESSION)).getUcd_id();
        String assignment_id =  request.getParameter("assignment_id");
        Map<String, Object> assHistory = assignmentService.studentSubmissionHistory(ucd_id, assignment_id);
        Date submission_date = (Date) assHistory.get("submission_date");
        String submission_datetime  = dateConverter(submission_date);
        String source_path = (String)assHistory.get("source_path");
        returnMap.put("submission_date",submission_datetime);
        returnMap.put("fileName", source_path.substring(source_path.lastIndexOf("/")+1));
        returnList.add(returnMap);
        String submission_json= JSON.toJSONString(returnList);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+submission_json+"}";
        return result_json;
    }

    //4. View grade and report
    @RequestMapping("/student/viewGrade")
    @ResponseBody
    public String studentViewGrade(HttpServletRequest request){
        String module_code =  request.getParameter("module_code");
        int ucd_id = ((Students)request.getSession().getAttribute(Constants.USER_SESSION)).getUcd_id();
        List<Assignment> assignmentList = assignmentService.queryAssignmentByModule(module_code);
        List<Map<String, String>> returnList = new ArrayList<>();
        for(Assignment ass : assignmentList){
            Map<String, String> returnMap = new HashMap<>();
            String title = ass.getTitle();
            String id = ass.getId();
            returnMap.put("title", title);
            returnMap.put("id",id);
            Float grade = assignmentService.queryGrade(ucd_id,ass.getId());
            if(grade!=null){
                returnMap.put("score", Float.toString(grade));
                returnList.add(returnMap);
            }
        }
        String submission_json= JSON.toJSONString(returnList);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+1+",\"data\":"+submission_json+"}";
        return result_json;
    }

//////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////
//Utils and common functions
    //Query Assignment name
    @RequestMapping("/assignment/assignmentName")
    @ResponseBody
    public Map<String,String> getAssignmentName(HttpServletRequest request){
        String id = request.getParameter("assignment_id");
        String name = assignmentService.queryNameById(id);
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("name",name);
        return returnMap;
    }
    //Convert assignment list to the frontend format in teacher pages
    public String teacherFormatConverter(List<Assignment> assignmentList, String option){
        List<Map<String,String>> resultList = new ArrayList<>();
        for(Assignment ass : assignmentList){
            Map<String,String> resultMap = new HashMap<>();
            String id = ass.getId();
            String title = ass.getTitle();
            Date due_date = ass.getDue_date();
            Date creation_date = ass.getCreation_date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String due_datetime = sdf.format(due_date);
            String creation_datetime = sdf.format(creation_date);
            String status = assignmentService.queryStatusById(id);
            if((option.equals("part") && status.equals("Completed")) || option.equals("all")){
                resultMap.put("id",id);
                resultMap.put("title",title);
                resultMap.put("due_date",due_datetime);
                resultMap.put("creation_date",creation_datetime);
                resultMap.put("status",status);
                resultList.add(resultMap);
            }
        }
        int count = resultList.size();
        String assignment_json= JSON.toJSONString(resultList);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+assignment_json+"}";
        return result_json;
    }
    //Convert assignment list to the frontend format in student pages
    public String studentFormatConverter(List<Assignment> assignmentList, int ucd_id){
        List<Map<String,String>> resultList = new ArrayList<>();
        for(Assignment ass : assignmentList){
            Map<String,String> resultMap = new HashMap<>();
            String id = ass.getId();
            if(assignmentService.queryStatusById(id).equals("Completed")){
                String title = ass.getTitle();
                Date due_date = ass.getDue_date();
                Date submission_date = assignmentService.querySubmissionDate(ucd_id, id);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String due_datetime = sdf.format(due_date);
                resultMap.put("status", "Not Submitted");
                if(submission_date!=null){
                    String submission_datetime = sdf.format(submission_date);
                    resultMap.put("submission_date",submission_datetime);
                }
                String source_path = assignmentService.queryStudentSourcePath(ucd_id,id);
                if(source_path!=null){
                    resultMap.put("status", "Submitted");
                }
                resultMap.put("id",id);
                resultMap.put("title",title);
                resultMap.put("due_date",due_datetime);
                resultList.add(resultMap);
            }
        }
        int count = resultList.size();
        String assignment_json= JSON.toJSONString(resultList);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+assignment_json+"}";
        return result_json;
    }
    //Convert Java.util.date to String
    public String dateConverter(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
//////////////////////////////////////////////////////////////////////
}
