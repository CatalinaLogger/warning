package com.six.datum.dao;

import com.six.datum.domain.Point;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Point record);

    int insertSelective(Point record);

    Point selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Point record);

    int updateByPrimaryKeyWithBLOBs(Point record);

    int updateByPrimaryKey(Point record);
}