package FYP19.Dao;

import FYP19.Entities.Department;
import FYP19.Entities.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {

    ////////////////////////////////
    //Register DAO Block
    int updateActivationCodeById(@Param("id") int teacher_id, @Param("A_code") String activation_code);
    String queryActivationCodeById(@Param("teacher_id") int id);
    int updateSaltById(@Param("teacher_id") int teacher_id, @Param("salt") String salt);
    int updateIsActivatedById(@Param("teacher_id") int teacher_id, @Param("isActivated") boolean isActivated);
    int updatePwdById(@Param("teacher_id") int teacher_id, @Param("pwd") String password);
    int insertDepartment(Department department);
    Department queryDepartmentByCode(@Param("code") String code);
    int registerTeacher(Teacher teacher);
    ////////////////////////////////

    Teacher queryTeacherById(@Param("teacher_id") int id);
}
