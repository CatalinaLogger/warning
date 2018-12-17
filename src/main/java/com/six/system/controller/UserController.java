package com.six.system.controller;

import com.six.system.common.param.PageParam;
import com.six.system.common.param.UserParam;
import com.six.system.common.pojo.JsonData;
import com.six.system.common.pojo.LoginInfo;
import com.six.system.common.pojo.PageData;
import com.six.system.domain.User;
import com.six.system.service.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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

    @ApiOperation("获取登录用户初始化数据，角色、权限、头像等")
    @GetMapping("/info")
    public JsonData info(@ApiParam(value = "授权令牌", required = true) @RequestParam("token") String token) {
        LoginInfo loginInfo = userService.loginInfo(token);
        return JsonData.success(loginInfo);
    }

    @ApiOperation("根据部门分页获取用户列表")
    @GetMapping("/dept/select/page")
    public JsonData selectDept(@ApiParam(value = "部门ID", example = "1") @RequestParam(value = "deptId", defaultValue = "") Integer deptId,
                           @ApiParam("查询条件") @RequestParam(value = "query", required = false) String query,
                           @Valid PageParam param, BindingResult bindingResult) {
        query = ObjectUtils.isEmpty(query) ? null : query;
        PageData<User> page = userService.selectDeptPage(deptId, query, param);
        return JsonData.success(page);
    }

    @ApiOperation("根据角色分页获取用户列表")
    @GetMapping("/role/select/page")
    public JsonData selectRole(@ApiParam(value = "是否是绑定了角色的用户", required = true) @RequestParam(value = "bound") Boolean bound,
                               @ApiParam(value = "角色ID", example = "1") @RequestParam(value = "roleId", defaultValue = "") Integer roleId,
                               @ApiParam("查询条件") @RequestParam(value = "query", required = false) String query,
                               @Valid PageParam param, BindingResult bindingResult) {
        query = ObjectUtils.isEmpty(query) ? null : query;
        PageData<User> page = userService.selectRolePage(bound, roleId, query, param);
        return JsonData.success(page);
    }

    @ApiOperation("检查是否已存在拥有某个属性的用户")
    @GetMapping("/exists")
    public JsonData exists(@ApiParam(value = "用户ID", example = "1")  @RequestParam(value = "userId", defaultValue = "") Integer userId,
                           @ApiParam(value = "属性名", required = true) @RequestParam("field") String field,
                           @ApiParam(value = "属性值", required = true) @RequestParam("value") String value) {
        return JsonData.success(userService.exists(userId, field, value));
    }

    @ApiOperation("新增用户")
    @PostMapping("/insert")
    public JsonData insert(@Valid UserParam param, BindingResult bindingResult) {
        return JsonData.successOperate(userService.insert(param));
    }

    @ApiOperation("修改用户")
    @ApiImplicitParam(name = "id", value = "用户ID", dataType = "int", paramType = "query", required = true, example = "1")
    @PutMapping("/update")
    public JsonData update(@Valid UserParam param, BindingResult bindingResult) {
        return JsonData.successOperate(userService.update(param));
    }

    @ApiOperation("冻结用户")
    @PutMapping("/stop")
    public JsonData stop(@ApiParam(value = "用户ID", example = "1", required = true)  @RequestParam("userId") Integer userId) {
        return JsonData.successOperate(userService.stop(userId));
    }

    @ApiOperation("解冻用户")
    @PutMapping("/start")
    public JsonData start(@ApiParam(value = "用户ID", example = "1", required = true)  @RequestParam("userId") Integer userId) {
        return JsonData.successOperate(userService.start(userId));
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete")
    public JsonData delete(@ApiParam(value = "用户ID", required = true) @RequestParam("userId") Integer userId) {
        return JsonData.successOperate(userService.delete(userId));
    }
}
