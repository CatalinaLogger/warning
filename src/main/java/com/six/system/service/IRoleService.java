package com.six.system.service;

import com.six.system.common.param.RoleParam;
import com.six.system.common.pojo.RoleNode;

import java.util.List;

public interface IRoleService {
    List<RoleNode> selectTree();

    String insert(RoleParam param);

    String update(RoleParam param);

    String delete(Integer roleId);

    String up(Integer roleId);

    String down(Integer roleId);

    String insertRoleUser(Integer roleId, List<Integer> userKeys);

    String deleteRoleUser(Integer roleId, List<Integer> userKeys);
}
