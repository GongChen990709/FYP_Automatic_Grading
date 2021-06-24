package FYP19.Dao;

import FYP19.Entities.Administrator;
import FYP19.Entities.Registration_History;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminMapper {
    Administrator queryAdminById(@Param("id") int id);
    String querySaltById(@Param("admin_id") int id);
    int insertStudentHistory(Registration_History history);
    List<Registration_History> queryStudentHistoryByTimeAndStatus(@Param("time") String time, @Param("status") String status, @Param("type") String type);
    List<String> queryAllHistoryDates();
}
