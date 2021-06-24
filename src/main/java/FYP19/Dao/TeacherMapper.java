package FYP19.Dao;

import FYP19.Entities.Department;
import FYP19.Entities.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    int deleteTeacherById(@Param("id") int teacher_id);
    List<Teacher> allTeachers();
    ////////////////////////////////

    ////////////////////////////////
    //Login DAO
    boolean queryIsActivatedById(@Param("t_id") int id);
    String querySaltById(@Param("t_id") int id);
    // //////////////////////////////
    Teacher queryTeacherById(@Param("teacher_id") int id);
    List<Map<String,Object>> associatedModule();
    List<Map<String,Object>> allTeacherInfo();
}
