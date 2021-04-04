package FYP19.Service;

import FYP19.Dao.StudentMapper;
import FYP19.Entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
//All functions here are configured to be executed in transactions; Ensuring ACID principles. 
    private StudentMapper studentMapper;

    public void setStudentMapper(StudentMapper studentMapper){
        this.studentMapper = studentMapper;
    }

    public int addStudent(Student student) { return studentMapper.addStudent(student); }

    public int deleteStudentByNum(int studentNum) {
        return studentMapper.deleteStudentByNum(studentNum);
    }

    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    public List<Student> queryAllStudents() {
        return studentMapper.queryAllStudents();
    }

}
