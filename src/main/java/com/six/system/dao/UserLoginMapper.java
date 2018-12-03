package com.six.system.dao;

import com.six.system.domain.UserLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);
}