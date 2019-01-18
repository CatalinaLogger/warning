package com.six.datum.service.impl;

import com.six.datum.common.param.PointParam;
import com.six.datum.dao.PointLevelMapper;
import com.six.datum.dao.PointMapper;
import com.six.datum.dao.PointPhotoMapper;
import com.six.datum.domain.Point;
import com.six.datum.domain.PointLevel;
import com.six.datum.domain.PointPhoto;
import com.six.datum.service.IPointService;
import com.six.system.common.exception.SixException;
import com.six.system.common.param.PageParam;
import com.six.system.common.pojo.PageData;
import com.six.system.common.pojo.ResultEnum;
import com.six.system.common.pojo.SixConfig;
import com.six.system.common.utils.FastClient;
import com.six.system.common.utils.SessionLocal;
import com.six.system.common.utils.SixUtil;
import com.six.system.dao.DeptMapper;
import com.six.system.domain.Dept;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PointServiceImpl implements IPointService {

    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private PointLevelMapper levelMapper;
    @Autowired
    private PointPhotoMapper photoMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private SixConfig sixConfig;
    @Autowired
    private FastClient fastClient;

    @Override
    public PageData<Point> selectPage(boolean bound, Integer deptId, Integer level, String query, PageParam page) {
        query = ObjectUtils.isEmpty(query) ? null : query;
        if (bound) {
            // 返回绑定监测点的结果
            int total = pointMapper.countByUserIdAndDeptId(SessionLocal.getUser().getId(), deptId, level, query);
            List<Point> list = pointMapper.selectPageByUserIdAndDeptId(SessionLocal.getUser().getId(), deptId, level, query, page);
            return new PageData<>(page.getPage(), page.getSize(), total, list);
        } else {
            // 返回未绑定监测点的结果
            int total = pointMapper.countUnDeptId(query);
            List<Point> list = pointMapper.selectPageUnDeptId(query, page);
            return new PageData<>(page.getPage(), page.getSize(), total, list);
        }
    }

    @Override
    public Point select(Integer pointId) {
        return pointMapper.selectByPrimaryKey(pointId);
    }

    @Override
    public String insert(PointParam param) {
        return null;
    }

    @Override
    public String update(PointParam param) {
        Point point = pointMapper.selectByPrimaryKey(param.getId());
        if (ObjectUtils.isEmpty(point)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        BeanUtils.copyProperties(param, point);
        SixUtil.setOperate(point);
        pointMapper.updateByPrimaryKeyWithBLOBs(point);
        return "修改监测点成功";
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

    @Override
    public String insertPointDept(Integer deptId, List<Integer> pointKeys) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        if (ObjectUtils.isEmpty(dept)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        SixUtil.setOperate(dept);
        pointMapper.updateForeach(dept, pointKeys);
        return "添加监测点成功";
    }

    @Override
    public String deletePointDept(List<Integer> pointKeys) {
        pointMapper.updateForeach(new Dept(), pointKeys);
        return "移除监测点成功";
    }

    @Override
    public PointLevel selectLevel(Integer pointId) {
        return levelMapper.selectByPointId(pointId);
    }

    @Override
    public List<PointLevel> selectLevelList(Integer pointId, String start, String end, Integer index) {
//        int[] piontList = getPiontList();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date now = new Date();
//        Calendar calendar = Calendar.getInstance(); //日历对象
//        calendar.setTime(now); //设置当前日期
//
//        PointLevel level = new PointLevel();
//        level.setTime(now);
//        level.setOperateName("超级管理员");
//        level.setStatus(1);
//        System.out.println(format.format(new Date()));
//        for (int i = 100000; i > 0; i--) {
//            int id = piontList[i % piontList.length];
//            level.setId(i);
//            level.setPointId(id);
//            level.setLevel(i % 5 + 1);
//            calendar.add(Calendar.MINUTE, -1);
//            level.setTime(calendar.getTime());
//            levelMapper.insert(level);
//        }
//        System.out.println(format.format(new Date()));
//        return null;

        if (ObjectUtils.isEmpty(end) || ObjectUtils.isEmpty(start)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            Calendar calendar = Calendar.getInstance(); //日历对象
            calendar.setTime(now); //设置当前日期
            if (ObjectUtils.isEmpty(end)) {
                end = format.format(now);
            }
            if (ObjectUtils.isEmpty(start)) {
                calendar.add(Calendar.MONTH, -1); //月份减一为-1，加一为1
                start = format.format(calendar.getTime());
            }
        }
        return levelMapper.selectByPointIdAndTime(pointId, start, end, (index - 1) * 100, 100);
    }

    @Override
    @Transactional
    public String uploadPhoto(Integer pointId, MultipartFile file) {
        Point point = pointMapper.selectByPrimaryKey(pointId);
        if (ObjectUtils.isEmpty(point)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        try {
            String path = fastClient.uploadFile(file);
            point.setPreview(path);
            pointMapper.updateByPrimaryKey(point);
            PointPhoto photo = new PointPhoto();
            photo.setUrl(path);
            photo.setPointId(pointId);
            SixUtil.setOperate(photo);
            photoMapper.insert(photo);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "上传现场图失败");
        }
        return "上传现场图成功";
    }

    @Override
    public List<PointPhoto> selectPhotoList(Integer pointId) {
        List<PointPhoto> list = photoMapper.selectByPointId(pointId);
        for (PointPhoto photo : list) {
            photo.setUrl(sixConfig.getFastUrl() + photo.getUrl());
        }
        return list;
    }

    @Override
    public String deletePhoto(Integer photoId) {
        PointPhoto photo = photoMapper.selectByPrimaryKey(photoId);
        if (ObjectUtils.isEmpty(photo)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        photoMapper.deleteByPrimaryKey(photoId);
        return "删除现场图成功";
    }

    private int[] getPiontList() {
        int[] x = {85
                ,86
                ,87
                ,88
                ,89
                ,90
                ,91
                ,92
                ,93
                ,94
                ,95
                ,96
                ,97
                ,98
                ,99
                ,100
                ,101
                ,102
                ,103
                ,104
                ,105
                ,106
                ,107
                ,108
                ,109
                ,110
                ,111
                ,112
                ,113
                ,114
                ,115
                ,116
                ,117
                ,118
                ,119
                ,120
                ,121
                ,122
                ,123
                ,124
                ,125
                ,126
                ,127
                ,128
                ,129
                ,130
                ,131
                ,132
                ,133
                ,134
                ,135
                ,136
                ,137
                ,138
                ,139
                ,140
                ,141
                ,142
                ,143
                ,144
                ,145
                ,146
                ,147
                ,148
                ,149
                ,150
                ,151
                ,152
                ,153
                ,154
                ,155
                ,156
                ,157
                ,158
                ,159
                ,160
                ,161
                ,162
                ,163
                ,164
                ,165
                ,166
                ,167
                ,168
                ,169
                ,170
                ,171
                ,172
                ,173
                ,206
                ,207
                ,208
                ,209
                ,212
                ,213
                ,214
                ,215
                ,216
                ,217
                ,218
                ,176
                ,177
                ,178
                ,179
                ,180
                ,181
                ,182
                ,183
                ,196
                ,184
                ,202
                ,203
                ,204
                ,211
                ,197
                ,210
                ,64
                ,200
                ,205
        };
        return x;
    }
}