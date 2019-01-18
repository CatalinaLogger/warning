package com.six.datum.service;

import com.six.datum.common.param.PointParam;
import com.six.datum.domain.Point;
import com.six.datum.domain.PointLevel;
import com.six.datum.domain.PointPhoto;
import com.six.system.common.param.PageParam;
import com.six.system.common.pojo.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPointService {

    PageData<Point> selectPage(boolean bound, Integer deptId, Integer level, String query, PageParam page);

    Point select(Integer pointId);

    String insert(PointParam param);

    String update(PointParam param);

    String delete(Integer pointId);

    String up(Integer pointId);

    String down(Integer pointId);

    String insertPointDept(Integer deptId, List<Integer> pointKeys);

    String deletePointDept(List<Integer> pointKeys);

    PointLevel selectLevel(Integer pointId);

    List<PointLevel> selectLevelList(Integer pointId, String start, String end, Integer index);

    String uploadPhoto(Integer pointId, MultipartFile file);

    List<PointPhoto> selectPhotoList(Integer pointId);

    String deletePhoto(Integer photoId);
}
