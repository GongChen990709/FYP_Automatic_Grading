package FYP19.Dao;

import org.apache.ibatis.annotations.Param;

public interface UserMapper{
    String queryRoleById(@Param("user_id") int id);
    int insertRole(@Param("user_id") int id, @Param("role") String role);
}
