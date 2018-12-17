package com.six.system.dao;

import com.six.system.domain.Role;
import com.six.system.domain.RoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleUser record);

    int insertSelective(RoleUser record);

    RoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleUser record);

    int updateByPrimaryKey(RoleUser record);
    /*****************************************************/
    List<Integer> selectLeadKeysByRoleIdAndDeptId(@Param("roleId") Integer roleId, @Param("deptId") Integer deptId);

    List<Integer> selectUserKeysByRoleId(@Param("roleId") Integer roleId);

    int insertForeach(@Param("role") Role role, @Param("userKeys") List<Integer> userKeys);

    int deleteForeach(@Param("role") Role role, @Param("userKeys") List<Integer> userKeys);

    int deleteByUserId(@Param("userId") Integer userId);
}