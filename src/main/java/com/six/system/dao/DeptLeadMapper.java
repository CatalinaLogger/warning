package com.six.system.dao;

import com.six.system.domain.DeptLead;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptLeadMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeptLead record);

    int insertSelective(DeptLead record);

    DeptLead selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeptLead record);

    int updateByPrimaryKey(DeptLead record);
}