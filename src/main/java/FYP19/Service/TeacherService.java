package FYP19.Service;

import FYP19.Entities.Module;
import FYP19.Entities.Students;
import FYP19.Entities.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher queryTeacherById(int id);
    int registerModule(Module module);
    List<Module> queryAllModules(int teacher_id);

//////////////////
//Process for registering Teacher
    boolean initialRegisterTeacher(Teacher teacher, String activationCode);
    String queryActivationCodeById(int teacher_id);
    boolean regComplete(int teacher_id, String pwd);
//////////////////






}
