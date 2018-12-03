package com.six.system.dao;

import com.six.system.domain.RoleAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAuth record);

    int insertSelective(RoleAuth record);

    RoleAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAuth record);

    int updateByPrimaryKey(RoleAuth record);
}