package FYP19.Service;

import FYP19.Entities.Module;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface TeacherService {
    Teacher queryTeacherById(int id);
    List<Teacher> allTeachers();
    int registerModule(Module module);
    List<Module> queryAllModules(int teacher_id);
    List<Map<String, Object>> associatedModule();
    List<Map<String, Object>> allTeacherInfo();
    int deleteModule(String module_code, HttpServletRequest request);
    void deleteTeacherById(int id, HttpServletRequest request);
//////////////////
//Process for registering Teacher
    boolean initialRegisterTeacher(Teacher teacher, String activationCode);
    String queryActivationCodeById(int teacher_id);
    boolean regComplete(int teacher_id, String pwd);
//////////////////






}
