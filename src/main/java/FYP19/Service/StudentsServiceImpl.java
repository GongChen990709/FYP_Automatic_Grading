package FYP19.Service;

import FYP19.Dao.StudentMapper;
import FYP19.Dao.StudentsMapper;
import FYP19.Entities.Module;
import FYP19.Entities.Students;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsServiceImpl implements StudentsService{
    private StudentsMapper studentsMapper;

    public void setStudentsMapper(StudentsMapper studentsMapper) {
        this.studentsMapper = studentsMapper;
    }

    public Students queryStudentById(int ucd_id) {
        return studentsMapper.queryStudentById(ucd_id);
    }

    public List<Module> getAllModules(int ucd_id) {
        return studentsMapper.getAllModules(ucd_id);
    }

    public Module getModuleByName(String module_name) {
        return studentsMapper.getModuleByName(module_name);
    }
}
