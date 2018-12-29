package com.six.system.service;

import com.six.system.common.param.DeptParam;
import com.six.system.common.pojo.DeptNode;
import com.six.system.domain.Dept;
import com.six.system.domain.User;

import java.util.List;

public interface IDeptService {
    List<DeptNode> selectTree();

    List<Dept> selectByUserId(Integer userId, Boolean child);

    String insert(DeptParam param);

    String update(DeptParam param);

    String delete(Integer deptId);

    String up(Integer deptId);

    String down(Integer deptId);

    /**
     * 用户关联区域列表
     * @param list
     * @return
     */
    List<User> setDeptOfUser(List<User> list);

}
