package com.six.system.dao;

import com.six.system.domain.Auth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    Auth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);
    /********************************************/
    List<Auth> selectAll();

    List<Auth> selectByParentId(@Param("parentId") Integer parentId);

    List<Auth> selectByUserId(@Param("userId") Integer userId);

    List<Auth> selectByRoleId(@Param("roleId") Integer roleId);
}