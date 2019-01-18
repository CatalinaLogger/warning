package com.six.datum.service.impl;


import com.six.datum.common.param.TypeParam;
import com.six.datum.dao.TypeMapper;
import com.six.datum.domain.Type;
import com.six.datum.service.ITypeService;
import com.six.system.common.exception.SixException;
import com.six.system.common.pojo.ResultEnum;
import com.six.system.common.utils.SixUtil;
import com.six.system.dao.ToolMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TypeServiceImpl implements ITypeService {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private ToolMapper toolMapper;

    @Override
    public List<Type> selectList(Integer parentId) {
        return typeMapper.selectByParentId(parentId);
    }

    @Override
    public String insert(TypeParam param) {
        if (checkExist(param.getParentId(), param.getId(), "name", param.getName())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该类型值");
        }
        Type type = new Type();
        BeanUtils.copyProperties(param, type);
        List<Type> list = typeMapper.selectByParentId(type.getParentId());
        if (list.size() > 0) {
            type.setSeq(list.get(list.size() - 1).getSeq() + 1);
        } else {
            type.setSeq(1);
        }
        SixUtil.setOperate(type);
        typeMapper.insert(type);
        return "新增类型值成功";
    }

    @Override
    public String update(TypeParam param) {
        if (checkExist(param.getParentId(), param.getId(), "name", param.getName())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该类型值");
        }
        Type type = typeMapper.selectByPrimaryKey(param.getId());
        if (ObjectUtils.isEmpty(type)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        BeanUtils.copyProperties(param, type);
        SixUtil.setOperate(type);
        typeMapper.updateByPrimaryKey(type);
        return "修改类型值成功";
    }

    @Override
    public String delete(Integer typeId) {
        Type type = typeMapper.selectByPrimaryKey(typeId);
        if (ObjectUtils.isEmpty(type)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        typeMapper.deleteByPrimaryKey(typeId);
        return "删除类型值成功";
    }

    @Override
    public String up(Integer typeId) {
        Type type = typeMapper.selectByPrimaryKey(typeId);
        List<Type> list = typeMapper.selectByParentId(type.getParentId());
        int index = list.indexOf(type);
        if (index > 0) {
            Type up = list.get(index - 1);
            int seq = up.getSeq();
            up.setSeq(type.getSeq());
            type.setSeq(seq);
            typeMapper.updateByPrimaryKey(up);
            typeMapper.updateByPrimaryKey(type);
        } else {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "已经到顶了");
        }
        return "上移成功";
    }

    @Override
    public String down(Integer typeId) {
        Type type = typeMapper.selectByPrimaryKey(typeId);
        List<Type> list = typeMapper.selectByParentId(type.getParentId());
        int index = list.indexOf(type);
        if (index < list.size() - 1) {
            Type down = list.get(index + 1);
            int seq = down.getSeq();
            down.setSeq(type.getSeq());
            type.setSeq(seq);
            typeMapper.updateByPrimaryKey(down);
            typeMapper.updateByPrimaryKey(type);
        } else {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "已经到底了");
        }
        return "下移成功";
    }

    private boolean checkExist(Integer parentId, Integer typeId, String field, String value) {
        return toolMapper.existSize("dat_type", parentId, typeId, field, value) > 0;
    }
}
