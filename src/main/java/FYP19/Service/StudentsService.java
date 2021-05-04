package FYP19.Service;

import FYP19.Entities.Module;
import FYP19.Entities.Students;

import java.util.List;

public interface StudentsService {
    Students queryStudentById(int ucd_id);
    List<Module> getAllModules(int ucd_id);
    Module getModuleByName(String module_name);
    void batchRegStudents(List<Students> studentList);

//////////////////
//Process for registering Students
    boolean initialRegisterStudent(Students stu, String activationCode);
    String queryActivationCodeById(int ucd_id);
    boolean regComplete(int ucd_id, String pwd);
//////////////////


}
