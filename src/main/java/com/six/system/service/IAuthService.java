package com.six.system.service;

import com.six.system.common.param.AuthParam;
import com.six.system.common.pojo.AuthNode;
import com.six.system.domain.Auth;

import java.util.List;

public interface IAuthService {
    List<AuthNode> selectTree(Integer userId);

    List<Auth> selectList(Integer roleId);

    String insert(AuthParam param);

    String update(AuthParam param);

    String delete(Integer authId);

    String up(Integer authId);

    String down(Integer authId);
}
