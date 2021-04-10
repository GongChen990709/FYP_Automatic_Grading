package FYP19.Service;

import FYP19.Dao.TeacherMapper;
import FYP19.Entities.Teacher;
import org.springframework.stereotype.Service;


public class TeacherServiceImpl implements TeacherService{
   private TeacherMapper teacherMapper;

    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public Teacher queryTeacherById(int id) {
        return teacherMapper.queryTeacherById(id);
    }
}
