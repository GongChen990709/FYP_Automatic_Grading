package FYP19.Service;

import FYP19.Entities.Module;
import FYP19.Entities.Students;

import java.util.List;

public interface StudentsService {
    Students queryStudentById(int ucd_id);
    List<Module> getAllModules(int ucd_id);
}
