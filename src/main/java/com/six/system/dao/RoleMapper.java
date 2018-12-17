package com.six.system.dao;

import com.six.system.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    /********************************************/
    List<Role> selectAll();

    List<Role> selectByParentId(@Param("parentId") Integer parentId);

    List<Role> selectByUserId(@Param("userId") Integer userId);
}