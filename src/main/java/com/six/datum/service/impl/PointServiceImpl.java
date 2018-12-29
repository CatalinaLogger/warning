package com.six.datum.service.impl;

import com.six.datum.common.param.PointParam;
import com.six.datum.dao.PointMapper;
import com.six.datum.domain.Point;
import com.six.datum.service.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointServiceImpl implements IPointService {

    @Autowired
    private PointMapper pointMapper;

    @Override
    public List<Point> selectByDeptId(Integer deptId, Boolean child) {
        return null;
    }

    @Override
    public String insert(PointParam param) {
        return null;
    }

    @Override
    public String update(PointParam param) {
        return null;
    }

    @Override
    public String delete(Integer pointId) {
        return null;
    }

    @Override
    public String up(Integer pointId) {
        return null;
    }

    @Override
    public String down(Integer pointId) {
        return null;
    }
}
