package FYP19.Service;

import FYP19.Entities.Student;


import java.util.List;

public interface StudentService {

    int addStudent(Student student);

    int deleteStudentByNum(int studentNum);

    int updateStudent(Student student);

    List<Student> queryAllStudents();
}
