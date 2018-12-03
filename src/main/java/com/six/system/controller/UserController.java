package com.six.system.controller;

import com.six.system.common.param.UserParam;
import com.six.system.common.pojo.JsonData;
import com.six.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="用户管理", description="维护用户信息、状态")
@Slf4j
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("新增用户")
    @GetMapping("/insert")
    public JsonData insertUser(@Valid UserParam param, BindingResult bindingResult) {
        userService.insert(param);
        return JsonData.successOperate();
    }
}
