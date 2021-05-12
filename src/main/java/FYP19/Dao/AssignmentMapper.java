package FYP19.Dao;

import FYP19.Entities.Assignment;
import FYP19.Entities.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AssignmentMapper {
    int insertAssignmentForm(Assignment assignment);

    Assignment queryAssignment(@Param("title") String title);

    List<Assignment> queryAssignmentsByCreatorIdAndModule(@Param("creator_id") int creator_id, @Param("module_code") String module_code);

    int initFilesPathById(@Param("id") String id);
    int updatePdfPathById(@Param("id") String id, @Param("PdfPath") String path);
    int updateJavaPathById(@Param("id") String id, @Param("JavaPath") String path);
    int updateDataPathById(@Param("id") String id, @Param("DataPath") String path);
    int updateDataTypePathById(@Param("id") String id, @Param("DataTypePath") String path);

}
