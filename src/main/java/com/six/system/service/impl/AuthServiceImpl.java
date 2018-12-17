package com.six.system.service.impl;

import com.six.system.common.exception.SixException;
import com.six.system.common.param.AuthParam;
import com.six.system.common.pojo.AuthNode;
import com.six.system.common.pojo.ResultEnum;
import com.six.system.common.utils.LevelUtil;
import com.six.system.common.utils.SixUtil;
import com.six.system.dao.AuthMapper;
import com.six.system.dao.RoleAuthMapper;
import com.six.system.dao.ToolMapper;
import com.six.system.domain.Auth;
import com.six.system.service.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Autowired
    private ToolMapper toolMapper;

    @Override
    public List<AuthNode> selectTree() {
        return handelAuthTree(authMapper.selectAll());
    }

    @Override
    public List<AuthNode> selectTreeByUserId(Integer userId) {
        return handelAuthTree(authMapper.selectByUserId(userId));
    }

    @Override
    public List<Auth> selectByRoleId(Integer roleId) {
        return authMapper.selectByRoleId(roleId);
    }

    @Override
    public String insert(AuthParam param) {
        if (checkExist(param.getParentId(), param.getId(), "path", param.getPath())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该权限名称");
        }
        if (checkExist(param.getParentId(), param.getId(), "name", param.getName())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该权限编码");
        }
        Auth auth = new Auth();
        BeanUtils.copyProperties(param, auth);
        auth.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        List<Auth> list = authMapper.selectByParentId(auth.getParentId());
        if (list.size() > 0) {
            auth.setSeq(list.get(list.size() - 1).getSeq() + 1);
        } else {
            auth.setSeq(1);
        }
        SixUtil.setOperate(auth);
        authMapper.insert(auth);
        return "新增权限成功";
    }

    @Override
    public String update(AuthParam param) {
        if (checkExist(param.getParentId(), param.getId(), "path", param.getPath())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该权限名称");
        }
        if (checkExist(param.getParentId(), param.getId(), "name", param.getName())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "当前节点已存在该权限编码");
        }
        Auth auth = authMapper.selectByPrimaryKey(param.getId());
        if (ObjectUtils.isEmpty(auth)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        BeanUtils.copyProperties(param, auth);
        SixUtil.setOperate(auth);
        authMapper.updateByPrimaryKey(auth);
        return "修改权限成功";
    }

    @Override
    @Transactional
    public String delete(Integer authId) {
        Auth auth = authMapper.selectByPrimaryKey(authId);
        if (ObjectUtils.isEmpty(auth)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        roleAuthMapper.deleteByAuthId(authId);
        authMapper.deleteByPrimaryKey(authId);
        return "权限删除成功";
    }

    @Override
    @Transactional
    public String up(Integer authId) {
        Auth auth = authMapper.selectByPrimaryKey(authId);
        List<Auth> list = authMapper.selectByParentId(auth.getParentId());
        int index = list.indexOf(auth);
        if (index > 0) {
            Auth up = list.get(index - 1);
            int seq = up.getSeq();
            up.setSeq(auth.getSeq());
            auth.setSeq(seq);
            authMapper.updateByPrimaryKey(up);
            authMapper.updateByPrimaryKey(auth);
        } else {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "已经到顶了");
        }
        return "上移成功";
    }

    @Override
    public String down(Integer authId) {
        Auth auth = authMapper.selectByPrimaryKey(authId);
        List<Auth> list = authMapper.selectByParentId(auth.getParentId());
        int index = list.indexOf(auth);
        if (index < list.size() - 1) {
            Auth down = list.get(index + 1);
            int seq = down.getSeq();
            down.setSeq(auth.getSeq());
            auth.setSeq(seq);
            authMapper.updateByPrimaryKey(down);
            authMapper.updateByPrimaryKey(auth);
        } else {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "已经到底了");
        }
        return "下移成功";
    }

    private String getLevel(Integer id) {
        Auth auth = authMapper.selectByPrimaryKey(id);
        return ObjectUtils.isEmpty(auth) ? null : auth.getLevel();
    }

    private boolean checkExist(Integer parentId, Integer authId, String field, String value) {
        return toolMapper.existSize("sys_auth", parentId, authId, field, value) > 0;
    }

    /**
     *  将权限列表处理成权限树
     * @param authList 权限列表
     * @return 权限树
     */
    public List<AuthNode> handelAuthTree(List<Auth> authList) {
        LinkedMultiValueMap<String, AuthNode> authMap = new LinkedMultiValueMap<>();
        List<AuthNode> rootList = new ArrayList<>();
        for (Auth auth: authList) {
            AuthNode node = AuthNode.adapt(auth);
            if (node.getLevel().equals("0")) {
                rootList.add(node);
            } else {
                authMap.add(node.getLevel(), node);
            }
        }
        Collections.sort(rootList, Comparator.comparingInt(AuthNode::getSeq));
        transformAuthTree(rootList, "0", authMap);
        return rootList;
    }

    /**
     * 地柜处理子权限树
     * @param levelList 父级权限层级列表
     * @param level 父级限层级编码
     * @param authMap Key为层级编码的权限Map
     */
    private void transformAuthTree(List<AuthNode> levelList, String level, LinkedMultiValueMap<String,AuthNode> authMap) {
        for (AuthNode node: levelList) {
            String nextLevel = LevelUtil.calculateLevel(level, node.getId());
            List<AuthNode> children = authMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(children)) {
                Collections.sort(children, Comparator.comparingInt(AuthNode::getSeq));
                // 设置子部门数据
                node.setChildren(children);
                // 处理子部门
                transformAuthTree(children, nextLevel, authMap);
            }
        }
    }
}
