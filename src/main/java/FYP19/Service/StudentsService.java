package FYP19.Service;

import FYP19.Entities.Module;
import FYP19.Entities.Registration_History;
import FYP19.Entities.Students;
import java.util.List;


public interface StudentsService {
    Students queryStudentById(int ucd_id);
    int countStudentNumByCode(String module_code);
    List<Module> queryAllModules(int ucd_id);
//////////////////
//Process for registering Students
    boolean initialRegisterStudent(Students stu, String activationCode);
    String queryActivationCodeById(int ucd_id);
    boolean regComplete(int ucd_id, String pwd);
//////////////////

    void insertStudentHistory(Registration_History history);
    List<Registration_History> queryStudentHistoryByTimeAndStatus(String time, String status);




}
