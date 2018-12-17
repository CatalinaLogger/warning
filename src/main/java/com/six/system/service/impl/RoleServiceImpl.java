package com.six.system.service.impl;

import com.six.system.common.exception.SixException;
import com.six.system.common.param.RoleParam;
import com.six.system.common.pojo.RoleNode;
import com.six.system.common.pojo.ResultEnum;
import com.six.system.common.utils.SixUtil;
import com.six.system.dao.*;
import com.six.system.domain.Role;
import com.six.system.service.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Autowired
    private RoleUserMapper roleUserMapper;
    @Autowired
    private ToolMapper toolMapper;

    @Override
    public List<RoleNode> selectTree() {
        List<Role> roleList = roleMapper.selectAll();
        LinkedMultiValueMap<Integer, RoleNode> roleMap = new LinkedMultiValueMap<>();
        List<RoleNode> rootList = new ArrayList<>();
        for (Role role: roleList) {
            RoleNode node = RoleNode.adapt(role);
            if (node.getParentId().equals(0)) {
                rootList.add(node);
            } else {
                roleMap.add(node.getParentId(), node);
            }
        }
        Collections.sort(rootList, Comparator.comparingInt(RoleNode::getSeq));
        for (RoleNode role : rootList) {
            List<RoleNode> children = roleMap.get(role.getId());
            if (!CollectionUtils.isEmpty(children)) {
                Collections.sort(children, Comparator.comparingInt(RoleNode::getSeq));
                // 设置角色数据
                role.setChildren(children);
            }
        }
        return rootList;
    }

    @Override
    @Transactional
    public String insert(RoleParam param) {
        String type = param.getParentId().equals(0) ? "角色组" : "角色";
        if (checkExist(param.getParentId(), param.getId(), "name", param.getName())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该" + type);
        }
        Role role = new Role();
        BeanUtils.copyProperties(param, role);
        List<Role> list = roleMapper.selectByParentId(role.getParentId());
        if (list.size() > 0) {
            role.setSeq(list.get(list.size() - 1).getSeq() + 1);
        } else {
            role.setSeq(1);
        }
        role.setEdit(1);
        SixUtil.setOperate(role);
        roleMapper.insert(role);
        if (param.getAuthKeys().size() > 0) {
            roleAuthMapper.insertForeach(role, param.getAuthKeys());
        }
        return "新增" + type + "成功";
    }

    @Override
    @Transactional
    public String update(RoleParam param) {
        String type = param.getParentId().equals(0) ? "角色组" : "角色";
        if (checkExist(param.getParentId(), param.getId(), "name", param.getName())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该" + type);
        }
        Role role = roleMapper.selectByPrimaryKey(param.getId());
        if (ObjectUtils.isEmpty(role)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "待更新" + type + "不存在");
        }
        BeanUtils.copyProperties(param, role);
        SixUtil.setOperate(role);
        roleMapper.updateByPrimaryKey(role);
        List<Integer> oldKeys = roleAuthMapper.selectAuthKeysByRoleId(role.getId());
        List<Integer> newKeys = param.getAuthKeys();
        Map<String, List<Integer>> deptMap = SixUtil.handelKeys(oldKeys, newKeys);
        if (deptMap.containsKey("addKeys")) {
            roleAuthMapper.insertForeach(role, deptMap.get("addKeys"));
        }
        if (deptMap.containsKey("delKeys")) {
            roleAuthMapper.deleteForeach(role, deptMap.get("delKeys"));
        }
        return "修改" + type + "成功";
    }

    @Override
    @Transactional
    public String delete(Integer roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        if (ObjectUtils.isEmpty(role)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        if (!CollectionUtils.isEmpty(roleMapper.selectByParentId(roleId))) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "请先删除该组的角色");
        }
        if (!CollectionUtils.isEmpty(roleUserMapper.selectUserKeysByRoleId(roleId))) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "请先移除该角色的成员");
        }
        roleMapper.deleteByPrimaryKey(roleId);
        String type = role.getParentId().equals(0) ? "角色组" : "角色";
        return type + "删除成功";
    }

    @Override
    @Transactional
    public String up(Integer roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        List<Role> list = roleMapper.selectByParentId(role.getParentId());
        int index = list.indexOf(role);
        if (index > 0) {
            Role up = list.get(index - 1);
            int seq = up.getSeq();
            up.setSeq(role.getSeq());
            role.setSeq(seq);
            roleMapper.updateByPrimaryKey(up);
            roleMapper.updateByPrimaryKey(role);
        } else {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "已经到顶了");
        }
        return "上移成功";
    }

    @Override
    public String down(Integer roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        List<Role> list = roleMapper.selectByParentId(role.getParentId());
        int index = list.indexOf(role);
        if (index < list.size() - 1) {
            Role down = list.get(index + 1);
            int seq = down.getSeq();
            down.setSeq(role.getSeq());
            role.setSeq(seq);
            roleMapper.updateByPrimaryKey(down);
            roleMapper.updateByPrimaryKey(role);
        } else {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "已经到底了");
        }
        return "下移成功";
    }

    @Override
    public String insertRoleUser(Integer roleId, List<Integer> userKeys) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        if (ObjectUtils.isEmpty(role)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "角色未找到");
        }
        SixUtil.setOperate(role);
        roleUserMapper.insertForeach(role, userKeys);
        return "添加成员成功";
    }

    @Override
    public String deleteRoleUser(Integer roleId, List<Integer> userKeys) {
        if (CollectionUtils.isEmpty(userKeys)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "用户ID数组不能为空");
        }
        Role role = roleMapper.selectByPrimaryKey(roleId);
        if (ObjectUtils.isEmpty(role)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "角色未找到");
        }
        roleUserMapper.deleteForeach(role, userKeys);
        return "移除成员成功";
    }

    private boolean checkExist(Integer parentId, Integer roleId, String field, String value) {
        return toolMapper.existSize("sys_role", parentId, roleId, field, value) > 0;
    }

}
