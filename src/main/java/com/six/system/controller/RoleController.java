package com.six.system.controller;

import com.six.system.common.param.RoleParam;
import com.six.system.common.pojo.JsonData;
import com.six.system.service.IRoleService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags="角色组/角色授权", description="角色组/角色管理，用户授权等")
@Slf4j
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation("获取角色树")
    @GetMapping("/select/tree")
    public JsonData selectRoleTree() {
        return JsonData.success(roleService.selectTree());
    }


    @ApiOperation("新增角色组/角色")
    @ApiImplicitParam(name = "authKeys", value = "权限ID数组，示例：1,2,3", dataType = "string", paramType = "query")
    @PostMapping("/insert")
    public JsonData insert(@Valid RoleParam param, BindingResult bindingResult) {
        return JsonData.successOperate(roleService.insert(param));
    }

    @ApiOperation("修改角色组/角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色组ID/角色ID", dataType = "int", paramType = "query", required = true, example = "1"),
            @ApiImplicitParam(name = "authKeys", value = "权限ID数组，示例：1,2,3", dataType = "string", paramType = "query")
    })
    @PutMapping("/update")
    public JsonData update(@Valid RoleParam param, BindingResult bindingResult) {
        return JsonData.successOperate(roleService.update(param));
    }

    @ApiOperation("删除角色组/角色")
    @DeleteMapping("/delete")
    public JsonData delete(@ApiParam(value = "角色组ID/角色ID", required = true) @RequestParam("roleId") Integer roleId) {
        return JsonData.successOperate(roleService.delete(roleId));
    }

    @ApiOperation("上移角色组/角色")
    @PutMapping("/up")
    public JsonData up(@ApiParam(value = "角色组ID/角色ID", required = true) @RequestParam("roleId") Integer roleId) {
        return JsonData.successOperate(roleService.up(roleId));
    }

    @ApiOperation("下移角色组/角色")
    @PutMapping("/down")
    public JsonData down(@ApiParam(value = "角色组ID/角色ID", required = true) @RequestParam("roleId") Integer roleId) {
        return JsonData.successOperate(roleService.down(roleId));
    }

    @ApiOperation("角色添加成员")
    @ApiImplicitParam(name = "userKeys", value = "用户ID数组，示例：1,2,3", dataType = "string", paramType = "query", required = true)
    @PostMapping("/user/insert")
    public JsonData insert(@ApiParam(value = "角色ID", required = true) @RequestParam("roleId") Integer roleId,
                           @RequestParam("userKeys") List<Integer> userKeys) {
        return JsonData.successOperate(roleService.insertRoleUser(roleId, userKeys));
    }

    @ApiOperation("角色移除成员")
    @ApiImplicitParam(name = "userKeys", value = "用户ID数组，示例：1,2,3", dataType = "string", paramType = "query", required = true)
    @DeleteMapping("/user/delete")
    public JsonData delete(@ApiParam(value = "角色ID", required = true) @RequestParam("roleId") Integer roleId,
                           @RequestParam("userKeys") List<Integer> userKeys) {
        return JsonData.successOperate(roleService.deleteRoleUser(roleId, userKeys));
    }
}
