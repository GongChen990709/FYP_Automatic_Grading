package FYP19.Dao;

import FYP19.Entities.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {
    Teacher queryTeacherById(@Param("teacher_id") int id);


}
