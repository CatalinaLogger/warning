package com.six.datum.dao;

import com.six.datum.domain.PointPhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PointPhotoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointPhoto record);

    int insertSelective(PointPhoto record);

    PointPhoto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointPhoto record);

    int updateByPrimaryKey(PointPhoto record);

    /********************************************/
    List<PointPhoto> selectByPointId(@Param("pointId") Integer pointId);
}