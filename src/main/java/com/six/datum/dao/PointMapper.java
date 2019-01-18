package com.six.datum.dao;

import com.six.datum.domain.Point;
import com.six.system.common.param.PageParam;
import com.six.system.domain.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Point record);

    int insertSelective(Point record);

    Point selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Point record);

    int updateByPrimaryKeyWithBLOBs(Point record);

    int updateByPrimaryKey(Point record);

    /********************************************/
    int countByUserIdAndDeptId(@Param("userId") Integer userId, @Param("deptId") Integer deptId, @Param("level") Integer level, @Param("query") String query);

    List<Point> selectPageByUserIdAndDeptId(@Param("userId") Integer userId, @Param("deptId") Integer deptId, @Param("level") Integer level, @Param("query") String query, @Param("page") PageParam page);

    int countUnDeptId(@Param("query") String query);

    List<Point> selectPageUnDeptId(@Param("query") String query, @Param("page") PageParam page);

    int updateForeach(@Param("dept") Dept dept, @Param("pointKeys") List<Integer> pointKeys);
}