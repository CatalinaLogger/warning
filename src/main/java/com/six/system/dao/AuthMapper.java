package com.six.system.dao;

import com.six.system.domain.Auth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    Auth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);
}