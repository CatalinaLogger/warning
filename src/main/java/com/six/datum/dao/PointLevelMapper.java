package com.six.datum.dao;

import com.six.datum.domain.PointLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PointLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointLevel record);

    int insertSelective(PointLevel record);

    PointLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointLevel record);

    int updateByPrimaryKey(PointLevel record);

    /********************************************/
    PointLevel selectByPointId(@Param("pointId") Integer pointId);

    List<PointLevel> selectByPointIdAndTime(@Param("pointId") Integer pointId, @Param("start") String start, @Param("end") String end, @Param("page") int page, @Param("size") int size);
}