package FYP19.Dao;

import FYP19.Entities.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper {
    int insertModule(Module module);
    List<Module> queryModulesByTid(@Param("teacher_id") int teacher_id);
    List<Module> queryModulesBySid(@Param("ucd_id") int ucd_id);
    int countStudentNumByCode(@Param("module_code") String module_code);
    int deregisterModule(@Param("module_code") String module_code, @Param("ucd_id") int ucd_id);
    int registerModule(@Param("module_code") String module_code, @Param("ucd_id") int ucd_id);
    int deleteModule(@Param("module_code") String module_code);

}
