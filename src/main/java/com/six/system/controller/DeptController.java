package com.six.system.controller;

import com.six.system.common.param.DeptParam;
import com.six.system.common.pojo.DeptNode;
import com.six.system.common.pojo.JsonData;
import com.six.system.service.IDeptService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="组织架构", description="组织架构管理")
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/system/dept")
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @ApiOperation(value = "获取组织架构树", response = DeptNode.class)
    @GetMapping("/select/tree")
    public JsonData selectTree() {
        return JsonData.success(deptService.selectTree());
    }

    @ApiOperation("获取用户组织架构列表")
    @GetMapping("/select/list")
    public JsonData selectList(@ApiParam(value = "用户ID：不传查询当前用户") @RequestParam(value = "userId", required = false) Integer userId,
                               @ApiParam(value = "是否包含子组织架构：默认不包含") @RequestParam(value = "child", defaultValue = "false") Boolean child) {
        return JsonData.success(deptService.selectList(userId, child));
    }

    @ApiOperation("新增组织架构")
    @PostMapping("/insert")
    public JsonData insert(@Valid DeptParam param, BindingResult bindingResult) {
        return JsonData.successOperate(deptService.insert(param));
    }

    @ApiOperation("修改组织架构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "组织架构ID", dataType = "int", paramType = "query", required = true, example = "1"),
            @ApiImplicitParam(name = "leadKeys", value = "主管ID数组", dataType = "string", paramType = "query")
    })
    @PutMapping("/update")
    public JsonData update(@Valid DeptParam param, BindingResult bindingResult) {
        return JsonData.successOperate(deptService.update(param));
    }

    @ApiOperation("删除组织架构")
    @DeleteMapping("/delete")
    public JsonData delete(@ApiParam(value = "组织架构ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(deptService.delete(deptId));
    }

    @ApiOperation("上移组织架构")
    @PutMapping("/up")
    public JsonData up(@ApiParam(value = "组织架构ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(deptService.up(deptId));
    }

    @ApiOperation("下移组织架构")
    @PutMapping("/down")
    public JsonData down(@ApiParam(value = "组织架构ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(deptService.down(deptId));
    }
}
