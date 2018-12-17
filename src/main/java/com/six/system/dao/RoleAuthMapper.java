package com.six.system.dao;

import com.six.system.domain.Role;
import com.six.system.domain.RoleAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAuth record);

    int insertSelective(RoleAuth record);

    RoleAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAuth record);

    int updateByPrimaryKey(RoleAuth record);
    /********************************************/
    List<Integer> selectAuthKeysByRoleId(@Param("roleId") Integer roleId);

    int insertForeach(@Param("role") Role role, @Param("authKeys") List<Integer> authKeys);

    int deleteForeach(@Param("role") Role role, @Param("authKeys") List<Integer> authKeys);

    int deleteByAuthId(@Param("authId") Integer authId);
}