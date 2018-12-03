package com.six.system.dao;

import com.six.system.domain.DeptUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeptUser record);

    int insertSelective(DeptUser record);

    DeptUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeptUser record);

    int updateByPrimaryKey(DeptUser record);
}