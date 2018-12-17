package com.six.system.controller;

import com.six.system.common.param.DeptParam;
import com.six.system.common.pojo.DeptNode;
import com.six.system.common.pojo.JsonData;
import com.six.system.domain.Dept;
import com.six.system.service.IDeptService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags="组织架构", description="组织架构管理")
@Slf4j
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/system/dept")
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @ApiOperation("获取区域树")
    @GetMapping("/select/tree")
    public JsonData selectDeptTree() {
        return JsonData.success(deptService.selectTree());
    }

    @ApiOperation("获取用户区域列表")
    @GetMapping("/select/list")
    public JsonData selectDeptList(@ApiParam(value = "用户ID", required = true) @RequestParam("userId") Integer userId) {
        return JsonData.success(deptService.selectByUserId(userId));
    }

    @ApiOperation("新增区域")
    @PostMapping("/insert")
    public JsonData insert(@Valid DeptParam param, BindingResult bindingResult) {
        return JsonData.successOperate(deptService.insert(param));
    }

    @ApiOperation("修改区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "区域ID", dataType = "int", paramType = "query", required = true, example = "1"),
            @ApiImplicitParam(name = "leadKeys", value = "主管ID数组", dataType = "string", paramType = "query")
    })
    @PutMapping("/update")
    public JsonData update(@Valid DeptParam param, BindingResult bindingResult) {
        return JsonData.successOperate(deptService.update(param));
    }

    @ApiOperation("删除区域")
    @DeleteMapping("/delete")
    public JsonData delete(@ApiParam(value = "区域ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(deptService.delete(deptId));
    }

    @ApiOperation("上移区域")
    @PutMapping("/up")
    public JsonData up(@ApiParam(value = "区域ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(deptService.up(deptId));
    }

    @ApiOperation("下移区域")
    @PutMapping("/down")
    public JsonData down(@ApiParam(value = "区域ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(deptService.down(deptId));
    }
}
