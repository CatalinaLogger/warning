package com.six.system.service.impl;

import com.six.system.common.param.PageParam;
import com.six.system.common.param.UserParam;
import com.six.system.common.pojo.LoginInfo;
import com.six.system.common.pojo.PageData;
import com.six.system.dao.UserMapper;
import com.six.system.domain.User;
import com.six.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User select() {
        return null;
    }

    @Override
    public List<User> selectByName(String name) {
        return null;
    }

    @Override
    public User selectByUsername(String username) {
        return null;
    }

    @Override
    public List<User> selectByRoleCode(String roleCode) {
        return null;
    }

    @Override
    public PageData<User> selectPageByDeptId(Integer deptId, String query, PageParam page) {
        return null;
    }

    @Override
    public String insert(UserParam param) {
        return null;
    }

    @Override
    public String update(UserParam param) {
        return null;
    }

    @Override
    public String delete(Integer userId) {
        return null;
    }

    @Override
    public String deleteBatch(List<Integer> ids) {
        return null;
    }

    @Override
    public boolean checkExist(Integer userId, String field, String value) {
        return false;
    }

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public String logout() {
        return null;
    }

    @Override
    public LoginInfo loginInfo(String token) {
        return null;
    }

    @Override
    public String refreshToken(Integer userId) {
        return null;
    }

    @Override
    public String resetPassword(String mail, String code) {
        return null;
    }

    @Override
    public String updatePassword(String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public String avatar(MultipartFile file) {
        return null;
    }
}
