package FYP19.Dao;

import FYP19.Entities.Administrator;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    Administrator queryAdminById(@Param("id") int id);
}
