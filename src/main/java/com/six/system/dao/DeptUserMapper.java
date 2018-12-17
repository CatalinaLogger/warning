package com.six.system.dao;

import com.six.system.domain.DeptUser;
import com.six.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeptUser record);

    int insertSelective(DeptUser record);

    DeptUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeptUser record);

    int updateByPrimaryKey(DeptUser record);

    /********************************************/
    List<Integer> selectDeptKeysByUserId(@Param("userId") Integer userId);

    List<Integer> selectUserKeysByDeptId(@Param("deptId") Integer deptId);

    int insertForeach(@Param("user") User user, @Param("deptKeys") List<Integer> deptKeys);

    int deleteForeach(@Param("user") User user, @Param("deptKeys") List<Integer> deptKeys);

    int deleteByUserId(@Param("userId") Integer userId);
}