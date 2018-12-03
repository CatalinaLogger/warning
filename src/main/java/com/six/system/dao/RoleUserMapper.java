package com.six.system.dao;

import com.six.system.domain.RoleUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleUser record);

    int insertSelective(RoleUser record);

    RoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleUser record);

    int updateByPrimaryKey(RoleUser record);
}