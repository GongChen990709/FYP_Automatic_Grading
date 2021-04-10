package FYP19.Service;

import FYP19.Dao.StudentMapper;
import FYP19.Dao.StudentsMapper;
import FYP19.Entities.Students;
import org.springframework.stereotype.Service;

@Service
public class StudentsServiceImpl implements StudentsService{
    private StudentsMapper studentsMapper;

    public void setStudentsMapper(StudentsMapper studentsMapper) {
        this.studentsMapper = studentsMapper;
    }

    public Students queryStudentById(int ucd_id) {
        return studentsMapper.queryStudentById(ucd_id);
    }
}
