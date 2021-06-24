package FYP19.Service;

import FYP19.Entities.Module;
import FYP19.Entities.Registration_History;
import FYP19.Entities.Students;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface StudentsService {
    Students queryStudentById(int ucd_id);
    int countStudentNumByCode(String module_code);
    List<Module> queryAllModules(int ucd_id);
    List<Students> allActivatedStudents();
    void deleteStudentById(int ucd_id);
    List<Map<String,Object>> allActivatedStudentsAndMajor();
    List<Map<String,Object>> studentsUnderOneModule(String module_code);
    List<Map<String,Object>> studentsNotUnderOneModule(String module_code);
    int deregisterModule(String module_code, int ucd_id);
    int registerModule(String module_code, int ucd_id);
//////////////////
//Process for registering Students
    boolean initialRegisterStudent(Students stu, String activationCode);
    String queryActivationCodeById(int ucd_id);
    boolean regComplete(int ucd_id, String pwd);
//////////////////
    void insertStudentHistory(Registration_History history);
    List<Registration_History> queryStudentHistoryByTimeAndStatus(String time, String status, String type);
    List<String> queryAllHistoryDates();




}
