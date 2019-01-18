package com.six.system.controller;

import com.six.system.common.param.AuthParam;
import com.six.system.common.pojo.JsonData;
import com.six.system.service.IAuthService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="权限控制", description="组织架构管理")
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/system/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @ApiOperation("获取权限树")
    @GetMapping("/select/tree")
    public JsonData selectTree() {
        return JsonData.success(authService.selectTree(null));
    }

    @ApiOperation("获取角色权限列表")
    @GetMapping("/select/list")
    public JsonData selectList(@ApiParam(value = "角色ID", required = true) @RequestParam("roleId") Integer roleId) {
        return JsonData.success(authService.selectList(roleId));
    }

    @ApiOperation("新增权限")
    @PostMapping("/insert")
    public JsonData insert(@Valid AuthParam param, BindingResult bindingResult) {
        return JsonData.successOperate(authService.insert(param));
    }

    @ApiOperation("修改权限")
    @ApiImplicitParam(name = "id", value = "权限ID", dataType = "int", paramType = "query", required = true, example = "1")
    @PutMapping("/update")
    public JsonData update(@Valid AuthParam param, BindingResult bindingResult) {
        return JsonData.successOperate(authService.update(param));
    }

    @ApiOperation("删除权限")
    @DeleteMapping("/delete")
    public JsonData delete(@ApiParam(value = "权限ID", required = true) @RequestParam("authId") Integer authId) {
        return JsonData.successOperate(authService.delete(authId));
    }

    @ApiOperation("上移权限")
    @PutMapping("/up")
    public JsonData up(@ApiParam(value = "权限ID", required = true) @RequestParam("authId") Integer authId) {
        return JsonData.successOperate(authService.up(authId));
    }

    @ApiOperation("下移权限")
    @PutMapping("/down")
    public JsonData down(@ApiParam(value = "权限ID", required = true) @RequestParam("authId") Integer authId) {
        return JsonData.successOperate(authService.down(authId));
    }
}
