package com.six.system.service.impl;

import com.six.system.common.exception.SixException;
import com.six.system.common.param.DeptParam;
import com.six.system.common.pojo.DeptNode;
import com.six.system.common.pojo.ResultEnum;
import com.six.system.common.pojo.SixConfig;
import com.six.system.common.utils.LevelUtil;
import com.six.system.common.utils.SessionLocal;
import com.six.system.common.utils.SixUtil;
import com.six.system.dao.*;
import com.six.system.domain.Dept;
import com.six.system.domain.Role;
import com.six.system.domain.User;
import com.six.system.service.IDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptUserMapper deptUserMapper;
    @Autowired
    private RoleUserMapper roleUserMapper;
    @Autowired
    private HandMapper handMapper;
    @Autowired
    private ToolMapper toolMapper;
    @Autowired
    private SixConfig sixConfig;

    @Override
    public List<DeptNode> selectTree() {
        List<Dept> deptList = deptMapper.selectAll();
        List<User> leadList = handMapper.selectLeadAll(sixConfig.getRoleLeadId());
        LinkedMultiValueMap<Integer, User> leadMap = new LinkedMultiValueMap<>();
        for (User user : leadList) {
            leadMap.add(user.getDeptId(), user);
        }
        LinkedMultiValueMap<String, DeptNode> deptMap = new LinkedMultiValueMap<>();
        List<DeptNode> rootList = new ArrayList<>();
        for (Dept dept: deptList) {
            DeptNode node = DeptNode.adapt(dept);
            node.setLead(leadMap.get(node.getId()));
            if (node.getLevel().equals("0")) {
                rootList.add(node);
            } else {
                deptMap.add(node.getLevel(), node);
            }
        }
        Collections.sort(rootList, Comparator.comparingInt(DeptNode::getSeq));
        transformDeptTree(rootList, "0", deptMap);
        return rootList;
    }

    @Override
    public List<Dept> selectList(Integer userId, Boolean child) {
        if (ObjectUtils.isEmpty(userId)) {
            userId = SessionLocal.getUser().getId();
        }
        List<Dept> deptList = deptMapper.selectByUserId(userId);
        if (child) {
            deptList.addAll(deptMapper.selectChildByUserId(userId));
        }
        return deptList;
    }


    @Override
    public String insert(DeptParam param) {
        if (checkExist(param.getParentId(), param.getId(), "name", param.getName())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该区域名称");
        }
        Dept dept = new Dept();
        BeanUtils.copyProperties(param, dept);
        dept.setLevel(LevelUtil.calculateLevel(getLevel(dept.getParentId()), dept.getParentId()));
        List<Dept> list = deptMapper.selectByParentId(dept.getParentId());
        if (list.size() > 0) {
            dept.setSeq(list.get(list.size() - 1).getSeq() + 1);
        } else {
            dept.setSeq(1);
        }
        SixUtil.setOperate(dept);
        deptMapper.insert(dept);
        return "新增区域成功";
    }

    @Override
    public String update(DeptParam param) {
        if (checkExist(param.getParentId(), param.getId(), "name", param.getName())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该区域名称");
        }
        Dept dept = deptMapper.selectByPrimaryKey(param.getId());
        if (ObjectUtils.isEmpty(dept)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        BeanUtils.copyProperties(param, dept);
        SixUtil.setOperate(dept);
        deptMapper.updateByPrimaryKey(dept);
        Role role = roleMapper.selectByPrimaryKey(sixConfig.getRoleLeadId());
        if (ObjectUtils.isEmpty(role)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到主管这个角色");
        }
        role.setDeptId(dept.getId());
        SixUtil.setOperate(role);
        List<Integer> oldKeys = roleUserMapper.selectLeadKeysByRoleIdAndDeptId(role.getId(), dept.getId());
        List<Integer> newKeys = param.getLeadKeys();
        Map<String, List<Integer>> deptMap = SixUtil.handelKeys(oldKeys, newKeys);
        if (deptMap.containsKey("addKeys")) {
            roleUserMapper.insertForeach(role, deptMap.get("addKeys"));
        }
        if (deptMap.containsKey("delKeys")) {
            roleUserMapper.deleteForeach(role, deptMap.get("delKeys"));
        }
        return "修改区域成功";
    }

    @Override
    public String delete(Integer deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        if (ObjectUtils.isEmpty(dept)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        if (!CollectionUtils.isEmpty(deptMapper.selectByParentId(deptId))) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "请先删除该区域的下级");
        }
        if (!CollectionUtils.isEmpty(deptUserMapper.selectUserKeysByDeptId(deptId))) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "请先清理该区域的用户");
        }
        deptMapper.deleteByPrimaryKey(deptId);
        return "区域删除成功";
    }

    @Override
    @Transactional
    public String up(Integer deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        List<Dept> list = deptMapper.selectByParentId(dept.getParentId());
        int index = list.indexOf(dept);
        if (index > 0) {
            Dept up = list.get(index - 1);
            int seq = up.getSeq();
            up.setSeq(dept.getSeq());
            dept.setSeq(seq);
            deptMapper.updateByPrimaryKey(up);
            deptMapper.updateByPrimaryKey(dept);
        } else {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "已经到顶了");
        }
        return "上移成功";
    }

    @Override
    public String down(Integer deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        List<Dept> list = deptMapper.selectByParentId(dept.getParentId());
        int index = list.indexOf(dept);
        if (index < list.size() - 1) {
            Dept down = list.get(index + 1);
            int seq = down.getSeq();
            down.setSeq(dept.getSeq());
            dept.setSeq(seq);
            deptMapper.updateByPrimaryKey(down);
            deptMapper.updateByPrimaryKey(dept);
        } else {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "已经到底了");
        }
        return "下移成功";
    }

    @Override
    public List<User> setDeptOfUser(List<User> userList) {
        List<Dept> deptList = handMapper.selectDeptAllOfUser();
        LinkedMultiValueMap<Integer, Dept> deptMap = new LinkedMultiValueMap<>();
        for (Dept dept : deptList) {
            deptMap.add(dept.getUserId(), dept);
        }
        for (User user : userList) {
            user.setDept(deptMap.get(user.getId()));
        }
        return userList;
    }

    private String getLevel(Integer id) {
        Dept dept = deptMapper.selectByPrimaryKey(id);
        return ObjectUtils.isEmpty(dept) ? null : dept.getLevel();
    }

    private boolean checkExist(Integer parentId, Integer deptId, String field, String value) {
        return toolMapper.existSize("sys_dept", parentId, deptId, field, value) > 0;
    }


    /**
     * 递归处理子部门树
     * @param levelList
     * @param level
     * @param deptMap
     */
    private void transformDeptTree(List<DeptNode> levelList, String level, LinkedMultiValueMap<String,DeptNode> deptMap) {
        for (DeptNode node: levelList) {
            String nextLevel = LevelUtil.calculateLevel(level, node.getId());
            List<DeptNode> children = deptMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(children)) {
                Collections.sort(children, Comparator.comparingInt(DeptNode::getSeq));
                // 设置子部门数据
                node.setChildren(children);
                // 处理子部门
                transformDeptTree(children, nextLevel, deptMap);
            }
        }
    }
}
