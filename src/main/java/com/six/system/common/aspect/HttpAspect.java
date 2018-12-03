package com.six.system.common.aspect;

import com.auth0.jwt.interfaces.Claim;
import com.six.system.common.exception.SixException;
import com.six.system.common.pojo.LoginUser;
import com.six.system.common.pojo.RedisKey;
import com.six.system.common.pojo.ResultEnum;
import com.six.system.common.utils.JWToken;
import com.six.system.common.utils.NetworkUtil;
import com.six.system.common.utils.SessionLocal;
import com.six.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class HttpAspect {

    @Autowired
    private IUserService userService;
    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;

    @Pointcut("execution(public * com.six.*.controller.*.*(..))")
    public void pointCut(){
    }

    @Before("pointCut()")
    public void doBefore(){
        LoginUser loginUser;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = NetworkUtil.getIpAddress(request);
        String token = request.getHeader("X-Token");
        Map<String, Claim> map;
        try {
            map = JWToken.verifyToken(token);
        } catch (Exception e) {
            throw new SixException(ResultEnum.ERROR_ACCESS.getCode(), "令牌已过期，请重新登录");
        }
        Integer id = map.get("id").asInt();
        String loginKey = RedisKey.LOGINKEY + ipAddress + ":" + id;
        // 缓存存在
        if (redisTemplate.hasKey(loginKey)) {
            // 从缓存中获取用户
            loginUser = redisTemplate.opsForValue().get(loginKey);
        } else {
            /** 如果Token未过期,则刷新Token */
            String newToken = userService.refreshToken(id);
            HttpServletResponse response = attributes.getResponse();
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.setHeader("token", newToken);
            loginUser = redisTemplate.opsForValue().get(loginKey);
            if (loginUser == null) {
                throw new SixException(ResultEnum.ERROR_ACCESS.getCode(), "用户已退出，请重新登录");
            }
        }
        loginUser.setOperateIp(ipAddress);
        System.out.println("获取用户>>>> " + loginUser.toString());
        SessionLocal.setUser(loginUser);
    }

    @AfterReturning(returning = "object", pointcut = "pointCut()")
    public void doAfter(){
        SessionLocal.remove();
    }
}
