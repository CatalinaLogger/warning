package com.six.system.dao;

import com.six.system.domain.Dept;
import com.six.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HandMapper {
    /** 查询所有区域负责人 */
    List<User> selectLeadAll(@Param("roleId") Integer roleId);
    /** 查询所有用户的部门 */
    List<Dept> selectDeptAllOfUser();
}