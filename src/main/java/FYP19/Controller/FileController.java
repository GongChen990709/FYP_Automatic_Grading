package FYP19.Controller;

import FYP19.Entities.Students;
import FYP19.AutoGrading.ReflectionImp.interfaces.ReflectionCallerErrorException;
import FYP19.AutoGrading.executor.JavaCompile;
import FYP19.Service.AssignmentService;
import FYP19.util.Constants;
import FYP19.util.FileUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {

    @Autowired
    @Qualifier("AssignmentService")
    private AssignmentService assignmentService;

///////////////////////////////////////////////
//Teacher Functions Unit
    //1. Adding an assignment, uploading 4 files
    //Upload assignment requirements in pdf format
    @RequestMapping("/teacher/pdfUpload")
    @ResponseBody
    public Map<String,String> assignmentPDFUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        boolean update_flag;
        String id = request.getParameter("assignment_id");
        String absPath = request.getSession().getServletContext().getRealPath("/WEB-INF/teacherUpload");
        File filePath = new File(absPath);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        try {
            File assignmentPath = new File(filePath.getPath()+"/"+id);
            if(!assignmentPath.exists()){
                assignmentPath.mkdir();
            }
            String realPath = assignmentPath+"/"+file.getOriginalFilename();
            file.transferTo(new File(realPath));
            update_flag = assignmentService.updatePdfPath(id,realPath);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "1");
            return returnMap;
        }
        if(update_flag==false){
            returnMap.put("code", "1");
            return returnMap;
        }
        returnMap.put("code","0");
        return returnMap;
    }
    //Upload correct solution for the assignment in java source file format
    @RequestMapping("/teacher/JavaUpload")
    @ResponseBody
    public Map<String,String> teacherJavaUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        boolean update_flag;
        String id = request.getParameter("assignment_id");
        String absPath = request.getSession().getServletContext().getRealPath("/WEB-INF/teacherUpload");
        File filePath = new File(absPath);
        if (!filePath.exists()) {
            filePath.mkdir();
            System.out.println("Teacher file path "+filePath);
        }
        try {
            File assignmentPath = new File(filePath.getPath()+"/"+id);
            if(!assignmentPath.exists()){
                assignmentPath.mkdir();
            }
            String realPath = assignmentPath+"/"+file.getOriginalFilename();
            file.transferTo(new File(realPath));
            update_flag = assignmentService.updateJavaPath(id,realPath);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "1");
            return returnMap;
        }
        if(update_flag==false){
            returnMap.put("code", "1");
            return returnMap;
        }
        returnMap.put("code","0");
        return returnMap;
    }
    //Upload data.json for specifying test data for the assignment
    @RequestMapping("/teacher/DataUpload")
    @ResponseBody
    public Map<String,String> DataUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        boolean update_flag;
        String id = request.getParameter("assignment_id");
        String absPath = request.getSession().getServletContext().getRealPath("/WEB-INF/teacherUpload");
        File filePath = new File(absPath);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        try {
            File assignmentPath = new File(filePath.getPath()+"/"+id);
            if(!assignmentPath.exists()){
                assignmentPath.mkdir();
            }
            String realPath = assignmentPath+"/"+file.getOriginalFilename();
            file.transferTo(new File(realPath));
            update_flag = assignmentService.updateDataPath(id,realPath);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "1");
            return returnMap;
        }
        if(update_flag==false){
            returnMap.put("code", "1");
            return returnMap;
        }
        returnMap.put("code","0");
        return returnMap;
    }
    //Upload datatype.json for specifying assignment requirements
    @RequestMapping("/teacher/DataTypeUpload")
    @ResponseBody
    public Map<String,String> DataTypeUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        boolean update_flag;
        String id = request.getParameter("assignment_id");
        String absPath = request.getSession().getServletContext().getRealPath("/WEB-INF/teacherUpload");
        File filePath = new File(absPath);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        try {
            File assignmentPath = new File(filePath.getPath()+"/"+id);
            if(!assignmentPath.exists()){
                assignmentPath.mkdir();
            }
            String realPath = assignmentPath+"/"+file.getOriginalFilename();
            file.transferTo(new File(realPath));
            update_flag = assignmentService.updateDataTypePath(id,realPath);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "1");
            return returnMap;
        }
        if(update_flag==false){
            returnMap.put("code", "1");
            return returnMap;
        }
        returnMap.put("code","0");
        return returnMap;
    }
    //Upload From frontend user interface
    @RequestMapping("/teacher/UI/DataTypeUpload")
    @ResponseBody
    public Map<String,String> DataTypeUIUpload(@RequestBody JSONObject requestObject, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        String assignment_id = requestObject.getString("assignment_id");
        JSONObject dataType = requestObject.getJSONObject("dataType");
        boolean update_flag;
        String absPath = request.getSession().getServletContext().getRealPath("/WEB-INF/teacherUpload");
        File filePath = new File(absPath);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        try {
            File assignmentPath = new File(filePath.getPath()+"/"+assignment_id);
            if(!assignmentPath.exists()){
                assignmentPath.mkdir();
            }
            String realPath = assignmentPath+"/"+"datatype.json";
            FileUtils.writeToJsonFile(dataType, realPath);
            update_flag = assignmentService.updateDataTypePath(assignment_id,realPath);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("status", "failed");
            return returnMap;
        }
        if(update_flag==false){
            returnMap.put("status", "failed");
            return returnMap;
        }

        returnMap.put("status","success");
        return returnMap;
    }



    //2. Downloading 4 files
    @RequestMapping("/teacher/pdfDownload")
    public String assignmentPDFDownload(HttpServletRequest request, HttpServletResponse resp) {
        String assignmentID = request.getParameter("id");
        String pdf_path = assignmentService.getPdfPathById(assignmentID);
        FileUtils.downloadFileByPath(pdf_path,request,resp);
        return null;
    }
    //Download correct implementation .java file
    @RequestMapping("/teacher/JavaDownload")
    public String assignmentJavaDownload(HttpServletRequest request, HttpServletResponse resp) {
        String assignmentID = request.getParameter("id");
        String java_path = assignmentService.getJavaPathById(assignmentID);
        FileUtils.downloadFileByPath(java_path,request,resp);
        return null;
    }
    //Download data.json file
    @RequestMapping("/teacher/dataDownload")
    public String assignmentDataDownload(HttpServletRequest request, HttpServletResponse resp) {
        String assignmentID = request.getParameter("id");
        String data_path = assignmentService.getDataPathById(assignmentID);
        FileUtils.downloadFileByPath(data_path,request,resp);
        return null;
    }
    //Download datatype.json file
    @RequestMapping("/teacher/datatypeDownload")
    public String assignmentDatatypeDownload(HttpServletRequest request, HttpServletResponse resp) {
        String assignmentID = request.getParameter("id");
        String datatype_path = assignmentService.getDataTypePathById(assignmentID);
        FileUtils.downloadFileByPath(datatype_path,request,resp);
        return null;
    }

    //3. Downloading student source file
    //Download student uploaded source code .java file
    @RequestMapping("/teacher/studentDownload")
    public String downloadStudentFile(HttpServletRequest request, HttpServletResponse resp) {
        String assignment_id = request.getParameter("assignment_id");
        int student_id = Integer.parseInt(request.getParameter("student_id"));
        String source_path = assignmentService.queryStudentSourcePath(student_id,assignment_id);
        FileUtils.downloadFileByPath(source_path, request, resp);
        return null;
    }

////////////////////////////////////////////////////
//Student Function Unit
    //1. Submit assignment source .java file
    @RequestMapping("/student/javaUpload")
    @ResponseBody
    public Map<String,String> studentJavaUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        int ucd_id = ((Students)request.getSession().getAttribute(Constants.USER_SESSION)).getUcd_id();
        boolean update_flag;
        String id = request.getParameter("assignment_id");
        String uploadPah = request.getSession().getServletContext().getRealPath("/WEB-INF/studentUpload");
        File upload = new File(uploadPah);
        if(!upload.exists()){
            upload.mkdir();
        }
        File assignment = new File(upload.getPath()+"/"+id);
        if(!assignment.exists()){
            assignment.mkdir();
        }
        File student = new File(assignment.getPath()+"/"+ucd_id);
        if(!student.exists()){
            student.mkdir();
        }
        try{
            String realPath = student.getPath()+"/"+file.getOriginalFilename();
            file.transferTo(new File(realPath));
            Date submission_date = new Date();
            update_flag = assignmentService.insertStudentSubmission(ucd_id,id,realPath,submission_date);
        }
        catch (Exception e) {
            e.printStackTrace();
            returnMap.put("code", "1");
            return returnMap;
        }
        if(update_flag==false){
            returnMap.put("code", "1");
            return returnMap;
        }
        returnMap.put("code","0");
        return returnMap;
    }

    //2. Download the submitted file
    @RequestMapping("/student/javaDownload")
    public String studentJavaDownload(HttpServletRequest request, HttpServletResponse resp) {
        String assignment_id = request.getParameter("id");
        int student_id = ((Students)request.getSession().getAttribute(Constants.USER_SESSION)).getUcd_id();
        String source_path = assignmentService.queryStudentSourcePath(student_id,assignment_id);
        FileUtils.downloadFileByPath(source_path, request, resp);
        return null;
    }
////////////////////////////////////////////////////

}
