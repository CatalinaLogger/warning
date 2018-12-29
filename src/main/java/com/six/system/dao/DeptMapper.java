package com.six.system.dao;

import com.six.system.domain.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    /********************************************/
    List<Dept> selectAll();

    List<Dept> selectByParentId(@Param("parentId") Integer parentId);

    List<Dept> selectByUserId(@Param("userId") Integer userId);

    List<Dept> selectChildByUserId(@Param("userId") Integer userId);
}