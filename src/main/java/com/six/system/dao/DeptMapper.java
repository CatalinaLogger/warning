package com.six.system.dao;

import com.six.system.domain.Dept;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
}