package FYP19.Dao;

import FYP19.Entities.Major;
import FYP19.Entities.Module;
import FYP19.Entities.Students;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;

import java.util.List;

public interface StudentsMapper {
    //All parameters in SQLs in Mapper.xml file are the same as the name specified within @Param
    //for primitive types paras(int string..)
    Students queryStudentById(@Param("ucd_id") int ucd_id);
    int registerStudent(Students stu);
    int insertMajor(Major major);
    Major queryMajorByCode(@Param("code") String code);
    String queryActivationCodeById(@Param("ucd_id") int ucd_id);
    int updateActivationCodeById(@Param("ucd_id") int ucd_id, @Param("A_code") String activation_code);
    int updateIsActivatedById(@Param("ucd_id") int ucd_id, @Param("isActivated") boolean isActivated);
    int updatePwdById(@Param("ucd_id") int ucd_id, @Param("pwd") String password);
    boolean queryIsActivatedById(@Param("ucd_id") int ucd_id);
    int updateSaltById(@Param("ucd_id") int ucd_id, @Param("salt") String salt);
    String querySaltById(@Param("ucd_id") int ucd_id);



}
