package FYP19.Dao;

import FYP19.Entities.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StudentMapper {

    int addStudent(Student student);

    //All parameters in SQLs in Mapper.xml file are the same as the name specified within @Param
    int deleteStudentByNum(@Param("studentNum") int studentNum);

    int updateStudent(Student student);

    List<Student> queryAllStudents();

}
