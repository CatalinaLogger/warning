package com.six.system;

import com.six.system.common.pojo.JsonData;
import com.six.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags="登录授权", description="用户登录、重置密码、验证码图片、登录页背景")
@Slf4j
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private IUserService userService;

    @ApiOperation("用户登录")
    @GetMapping("/login")
    public JsonData login(@ApiParam(value = "账号/手机/邮箱", required = true) @RequestParam("username") String username,
                          @ApiParam(value = "密码", required = true) @RequestParam("password") String password) {
        String token = userService.login(username, password);
        return JsonData.success(token);
    }
}
