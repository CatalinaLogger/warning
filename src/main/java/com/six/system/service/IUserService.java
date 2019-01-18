package com.six.system.service;

import com.six.system.common.param.PageParam;
import com.six.system.common.param.UserParam;
import com.six.system.common.pojo.LoginInfo;
import com.six.system.common.pojo.PageData;
import com.six.system.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    /**
     * 根据部门ID分页查询用户列表
     * @param deptId 部门ID
     * @param query 查询条件（username, name, phone, mail）
     * @param page 分页条件
     * @return 用户列表
     */
    PageData<User> selectPageDept(Integer deptId, String query, PageParam page);

    /**
     * 根据角色ID分页查询用户列表
     * @param bound 是否是绑定了角色的用户
     * @param roleId 角色ID
     * @param query 查询条件（username, name, phone, mail）
     * @param param 分页条件
     * @return 用户列表
     */
    PageData<User> selectPageRole(Boolean bound, Integer roleId, String query, PageParam param);

    /**
     * 检查用户信息是否已存在
     * @param userId 用户ID
     * @param field 属性
     * @param value 属性值
     * @return 是否存在
     */
    boolean exists(Integer userId, String field, String value);

    /**
     * 新增用户
     * @param param
     * @return 成功提示语
     */
    String insert(UserParam param);

    /**
     * 修改用户
     * @param param
     * @return 成功提示语
     */
    String update(UserParam param);

    /**
     * 冻结用户
     * @param userId 用户ID
     * @return 成功提示语
     */
    String stop(Integer userId);

    /**
     * 解冻用户
     * @param userId 用户ID
     * @return 成功提示语
     */
    String start(Integer userId);

    /**
     * 删除用户
     * @param userId
     * @return 成功提示语
     */
    String delete(Integer userId);

    /**
     * 用户登录
     * @param username 用户名/姓名/手机/邮箱
     * @param password 密码
     * @return 授权令牌
     */
    String login(String username, String password);

    /**
     * 用户登出
     * @return 成功提示语
     */
    String logout();

    /**
     * 根据token获取用户信息
     * @param token
     * @return 用户信息
     */
    LoginInfo loginInfo(String token);

    /**
     * 刷新token
     * @param userId 用户ID
     * @return 新的token
     */
    String refreshToken(Integer userId);

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 成功提示语
     */
    String updatePassword(String oldPassword, String newPassword);

    /**
     * 修改头像
     * @param file 头像文件
     * @return 成功提示语
     */
    String avatar(MultipartFile file);
}
