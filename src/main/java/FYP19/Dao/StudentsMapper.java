package FYP19.Dao;

import FYP19.Entities.Module;
import FYP19.Entities.Students;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentsMapper {
    //All parameters in SQLs in Mapper.xml file are the same as the name specified within @Param
    //for primitive types paras(int string..)
    Students queryStudentById(@Param("ucd_id") int ucd_id);
    List<Module> getAllModules(@Param("ucd_id") int ucd_id);

}
