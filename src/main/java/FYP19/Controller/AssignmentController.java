package FYP19.Controller;

import FYP19.Entities.Assignment;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.Service.AssignmentService;
import FYP19.Service.StudentsService;
import FYP19.util.Constants;
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

    @Autowired
    @Qualifier("StudentsServiceImpl")
    private StudentsService studentsService;

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
        String resultJSON = teacherFormatConverter(assignmentList,"all");
        return resultJSON;
    }

    @RequestMapping("/teacher/allPublishedAssignments")
    @ResponseBody
    public String allTeacherPublishedAssignments(HttpServletRequest request) {
        String module_code = request.getParameter("module_code");
        List<Assignment> assignmentList = assignmentService.queryAssignmentsByCreatorIdAndModule(module_code,request);
        String resultJSON = teacherFormatConverter(assignmentList,"part");
        return resultJSON;
    }



    @RequestMapping("/student/allAssignments")
    @ResponseBody
    public String allStudentAssignments(HttpServletRequest request) {
        String module_code = request.getParameter("module_code");
        List<Assignment> assignmentList = assignmentService.queryAssignmentByModule(module_code);
        int ucd_id = ((Students)request.getSession().getAttribute(Constants.USER_SESSION)).getUcd_id();
        String resultJSON = studentFormatConverter(assignmentList, ucd_id);
        return resultJSON;
    }


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


    @RequestMapping("/assignment/query")
    @ResponseBody
    public String fuzzyQueryAssignments(HttpServletRequest request){
        String title = request.getParameter("title");
        String module_code = request.getParameter("module_code");
        String json_result="";
        Object user = request.getSession().getAttribute(Constants.USER_SESSION);
        if(user instanceof Teacher){
            int teacher_id = ((Teacher) user).getId();
            List<Assignment> assignmentList = assignmentService.fuzzyQueryByTitle(title,teacher_id,module_code);
            json_result = teacherFormatConverter(assignmentList,"all");
        }
        if(user instanceof Students){
            int ucd_id = ((Students) user).getUcd_id();
            List<Assignment> assignmentList = assignmentService.fuzzyQueryByTitle(title,ucd_id,module_code);
            json_result = teacherFormatConverter(assignmentList,"all");
        }
        return json_result;
    }

    @RequestMapping("teacher/deleteAssignment")
    @ResponseBody
    public Map<String,String> deleteAssignment(@RequestBody Map<String,String> map, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<>();
        String id = map.get("id");
        try {
            assignmentService.deleteAssignmentById(id, request);
        } catch (Exception e) {
            returnMap.put("code", "1");
            return returnMap;
        }
        returnMap.put("code", "0");
        return returnMap;
    }

    //Show which files are not submitted
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


    //Used in download area to show all assignment files uploaded
    @RequestMapping("/assignment/allUploadedFiles")
    @ResponseBody
    public List<Map<String,String>> allUploadedFiles(HttpServletRequest request){
        String assignmentId = request.getParameter("id");
        List<Map<String,String>> resultList = new ArrayList<>();
        Map<String,String> filePaths = assignmentService.getFilePathsById(assignmentId);
        System.out.println("MAP=============== "+filePaths);
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


    //name description due_date fileName
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


    @RequestMapping("/student/submissionHistory")
    @ResponseBody
    //fileName submission_date
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
            if(assignmentService.queryGrade(student_id,assignment_id)!=null){
                returnMap.put("grading_status", "Graded");
            }
            returnList.add(returnMap);
        }
        int count = returnList.size();
        String assignment_json= JSON.toJSONString(returnList);
        String result_json = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+assignment_json+"}";
        return result_json;
    }



    @RequestMapping("/assignment/assignmentName")
    @ResponseBody
    public Map<String,String> getAssignmentName(HttpServletRequest request){
        String id = request.getParameter("assignment_id");
        String name = assignmentService.queryNameById(id);
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("name",name);
        return returnMap;
    }








    //Convert assignment list to the frontend format
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

    public String dateConverter(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }













}
