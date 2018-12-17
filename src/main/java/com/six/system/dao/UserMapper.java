package com.six.system.dao;

import com.six.system.common.param.PageParam;
import com.six.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);
    /********************************************/
    User selectByKeyword(@Param("keyword") String keyword);

    int countAll();

    List<User> selectPageAll(@Param("query") String query, @Param("page") PageParam page);

    int countByDeptId(@Param("deptId") Integer deptId, @Param("query") String query);

    List<User> selectPageByDeptId(@Param("deptId") Integer deptId, @Param("query") String query, @Param("page") PageParam page);

    int countByRoleId(@Param("roleId") Integer roleId, @Param("query") String query);

    List<User> selectPageByRoleId(@Param("roleId") Integer roleId, @Param("query") String query, @Param("page") PageParam page);

    int countUnRoleId(@Param("roleId") Integer roleId, @Param("query") String query);

    List<User> selectPageUnRoleId(@Param("roleId") Integer roleId, @Param("query") String query, @Param("page") PageParam page);
}