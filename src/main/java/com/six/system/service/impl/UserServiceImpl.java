package com.six.system.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.six.system.common.exception.SixException;
import com.six.system.common.param.PageParam;
import com.six.system.common.param.UserParam;
import com.six.system.common.pojo.*;
import com.six.system.common.utils.*;
import com.six.system.dao.*;
import com.six.system.domain.User;
import com.six.system.domain.UserLogin;
import com.six.system.service.IAuthService;
import com.six.system.service.IDeptService;
import com.six.system.service.IUserService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptUserMapper deptUserMapper;
    @Autowired
    private RoleUserMapper roleUserMapper;
    @Autowired
    private UserLoginMapper loginMapper;
    @Autowired
    private ToolMapper toolMapper;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IAuthService authService;
    @Autowired
    private LoginUser loginUser;
    @Autowired
    private LoginInfo loginInfo;
    @Autowired
    private SixConfig sixConfig;
    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;

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
    public PageData<User> selectDeptPage(Integer deptId, String query, PageParam page) {
        if (ObjectUtils.isEmpty(deptId)) {
            int total = userMapper.countAll();
            List<User> list = userMapper.selectPageAll(query, page);
            return new PageData<>(page.getPage(), page.getSize(), total, list);
        } else {
            int total = userMapper.countByDeptId(deptId, query);
            List<User> list = userMapper.selectPageByDeptId(deptId, query, page);
            return new PageData<>(page.getPage(), page.getSize(), total, list);
        }
    }

    // TODO deptService.setDeptOfUser() 当用户量较大时待优化
    @Override
    public PageData<User> selectRolePage(Boolean bound, Integer roleId, String query, PageParam page) {
        if (bound) {
            int total = userMapper.countByRoleId(roleId, query);
            List<User> list = userMapper.selectPageByRoleId(roleId, query, page);
            return new PageData<>(page.getPage(), page.getSize(), total, deptService.setDeptOfUser(list));
        } else {
            int total = userMapper.countUnRoleId(roleId, query);
            List<User> list = userMapper.selectPageUnRoleId(roleId, query, page);
            return new PageData<>(page.getPage(), page.getSize(), total, deptService.setDeptOfUser(list));
        }
    }

    @Override
    public boolean exists(Integer userId, String field, String value) {
        return toolMapper.existSize("sys_user", null, userId, field, value) > 0 ? true : false;
    }

    @Override
    @Transactional
    public String insert(UserParam param) {
        if (exists(param.getId(),"username", param.getUsername())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "用户名已存在");
        }
        if (exists(param.getId(),"phone", param.getPhone())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "手机已存在");
        }
        if (exists(param.getId(),"mail", param.getMail())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "邮箱已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(param, user);
        //TODO 密码改随机密码
        // String password = PasswordUtil.randomPassword();
        String encryptedPassword = MD5Util.encrypt("111111");
        user.setPassword(encryptedPassword);
        user.setStatus(0);
        SixUtil.setOperate(user);
        userMapper.insert(user);
        deptUserMapper.insertForeach(user, param.getDeptKeys());
        return "新增用户成功";
    }

    @Override
    @Transactional
    public String update(UserParam param) {
        if (exists(param.getId(),"username", param.getUsername())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "用户名已存在");
        }
        if (exists(param.getId(),"phone", param.getPhone())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "手机已存在");
        }
        if (exists(param.getId(),"mail", param.getMail())) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "邮箱已存在");
        }
        User user = userMapper.selectByPrimaryKey(param.getId());
        if (ObjectUtils.isEmpty(user)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        BeanUtils.copyProperties(param, user);
        SixUtil.setOperate(user);
        userMapper.updateByPrimaryKey(user);
        List<Integer> oldDeptKeys = deptUserMapper.selectDeptKeysByUserId(user.getId());
        List<Integer> newDeptKeys = param.getDeptKeys();
        Map<String, List<Integer>> deptMap = SixUtil.handelKeys(oldDeptKeys, newDeptKeys);
        if (deptMap.containsKey("addKeys")) {
            deptUserMapper.insertForeach(user, deptMap.get("addKeys"));
        }
        if (deptMap.containsKey("delKeys")) {
            deptUserMapper.deleteForeach(user, deptMap.get("delKeys"));
        }
        return "修改用户成功";
    }

    @Override
    public String stop(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        if (user.getStatus().equals(0)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "用户未激活");
        }
        user.setStatus(2);
        SixUtil.setOperate(user);
        userMapper.updateByPrimaryKey(user);
        return "冻结用户成功";
    }

    @Override
    public String start(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        if (user.getStatus().equals(0)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "用户未激活");
        }
        user.setStatus(1);
        SixUtil.setOperate(user);
        userMapper.updateByPrimaryKey(user);
        return "解冻用户成功";
    }

    @Override
    @Transactional
    public String delete(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "未找到相匹配的记录");
        }
        deptUserMapper.deleteByUserId(userId);
        roleUserMapper.deleteByUserId(userId);
        userMapper.deleteByPrimaryKey(userId);
        return "删除用户成功";
    }

    @Override
    public String login(String username, String password) {
        User user = userMapper.selectByKeyword(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "请输入正确的账号和密码");
        } else if (!user.getPassword().equals(MD5Util.encrypt(password))) {
            if (user.getResetCode() != null && user.getResetCode().equals(MD5Util.encrypt(password))) {
                /** 当使用重置后的密码登录时使用新密码替换旧密码 */
                user.setPassword(user.getResetCode());
                user.setResetCode("");
                userMapper.updateByPrimaryKey(user);
                handelLoginLog(user);
                return handelAuthorize(user);
            } else {
                throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "请输入正确的账号和密码");
            }
        } else if (user.getStatus() == 2) {
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "账号被冻结");
        } else {
            if (user.getStatus() == 0) {
                /** 初次登录，自动激活账号 */
                user.setStatus(1);
                userMapper.updateByPrimaryKey(user);
            }
            handelLoginLog(user);
            return handelAuthorize(user);
        }
    }

    /**
     * 保存登录日志
     * @param user
     */
    private void handelLoginLog(User user) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = NetworkUtil.getIpAddress(request);
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        UserLogin userLogin = new UserLogin(user.getId(), user.getName(), ipAddress, new Date(),
                "", userAgent.getOperatingSystem().getName(), userAgent.getBrowser().getName(),
                userAgent.getBrowserVersion().getVersion());
        loginMapper.insert(userLogin);
    }

    private String handelAuthorize(User user) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = NetworkUtil.getIpAddress(request);
        try {
            String token = JWToken.createToken(user);
            BeanUtils.copyProperties(user, loginUser);
            String loginKey = RedisKey.LOGINKEY + ipAddress + ":" + user.getId();
            clearUserCache(loginKey);
            // log.info("用户登录，缓存用户信息 >> " + loginKey);
            redisTemplate.opsForValue().set(loginKey, loginUser, 5, TimeUnit.HOURS);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SixException(ResultEnum.EXCEPTION.getCode(), "签发令牌失败");
        }
    }

    private void clearUserCache (String loginKey) {
        if (redisTemplate.hasKey(loginKey)) {
            // 缓存存在
            redisTemplate.delete(loginKey);
        }
    }

    @Override
    public String logout() {
        return null;
    }

    @Override
    public LoginInfo loginInfo(String token) {
        try {
            Map<String, Claim> map = JWToken.verifyToken(token);
            Integer id = map.get("id").asInt();
            User user = userMapper.selectByPrimaryKey(id);
            loginInfo.setName(user.getName());
            String avatar = user.getAvatar();
            if (StringUtils.isEmpty(avatar)) {
                loginInfo.setAvatar(sixConfig.getAvatar());
            } else {
                loginInfo.setAvatar(sixConfig.getFastUrl() + avatar);
            }
            loginInfo.setRole(roleMapper.selectByUserId(user.getId()));
            // loginInfo.setAuth(authService.selectTree());
            loginInfo.setAuth(authService.selectTreeByUserId(user.getId()));
            return loginInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SixException(ResultEnum.ERROR_PARAM.getCode(), "非法令牌或令牌已过期");
        }
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
