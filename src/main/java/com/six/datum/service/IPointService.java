package com.six.datum.service;

import com.six.datum.common.param.PointParam;
import com.six.datum.domain.Point;

import java.util.List;

public interface IPointService {

    List<Point> selectByDeptId(Integer deptId, Boolean child);

    String insert(PointParam param);

    String update(PointParam param);

    String delete(Integer pointId);

    String up(Integer pointId);

    String down(Integer pointId);
}
