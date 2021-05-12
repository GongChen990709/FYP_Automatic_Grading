package FYP19.Controller;

import FYP19.Resolver.JavaCompile;
import FYP19.Service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {

    @Autowired
    @Qualifier("AssignmentService")
    private AssignmentService assignmentService;

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
    //Upload data.json for specifying test data for assignments
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
    //Upload datatype.json for specifying  assignment requirements
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

    //download assignment requirements in pdf format
    @RequestMapping("/teacher/pdfDownload")
    @ResponseBody
    public Map<String,String> assignmentPDFDownload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        Map<String, String> returnMap = new HashMap<String, String>();
        return returnMap;
    }





    @RequestMapping("/testcompile")
    @ResponseBody
    public void test(HttpServletRequest request) throws ClassNotFoundException {
        //String path = request.getSession().getServletContext().getRealPath("WEB-INF/classes/FYP19/Resolver");
        //FYP_Automatic_Grading.out.artifacts.FYP_Automatic_Grading_war_exploded.WEB-INF.classes.FYP19.Resolver.test_tem
        String src = "/Users/gongchen/Documents/StageFour/Degree Project/FYP_Automatic_Grading/out/artifacts/FYP_Automatic_Grading_war_exploded/WEB-INF/StudentSource/test_tem.java";
        String dest = "/Users/gongchen/Documents/StageFour/Degree Project/FYP_Automatic_Grading/out/artifacts/FYP_Automatic_Grading_war_exploded/WEB-INF/StudentSource";
        JavaCompile compiler = new JavaCompile();
        compiler.compile(src,dest);
        //SelfClassLoader loader = new SelfClassLoader("/Users/gongchen/Documents/StageFour/Degree Project/FYP_Automatic_Grading/out/artifacts/FYP_Automatic_Grading_war_exploded");
        //System.out.println(loader.loadClass("test_tem"));
    }

}
