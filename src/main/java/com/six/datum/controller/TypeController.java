package com.six.datum.controller;

import com.six.datum.common.param.TypeParam;
import com.six.datum.service.ITypeService;
import com.six.system.common.pojo.JsonData;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="类型值", description="类型值管理")
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/datum/type")
public class TypeController {

    @Autowired
    private ITypeService typeService;

    @ApiOperation("获取类型值列表")
    @GetMapping("/select/list")
    public JsonData selectList(@ApiParam(value = "类型ID：不传查询类型列表") @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        return JsonData.success(typeService.selectList(parentId));
    }

    @ApiOperation("新增类型值")
    @PostMapping("/insert")
    public JsonData insert(@Valid TypeParam param, BindingResult bindingResult) {
        return JsonData.successOperate(typeService.insert(param));
    }

    @ApiOperation("修改类型值")
    @ApiImplicitParam(name = "id", value = "类型值ID", dataType = "int", paramType = "query", required = true, example = "1")
    @PutMapping("/update")
    public JsonData update(@Valid TypeParam param, BindingResult bindingResult) {
        return JsonData.successOperate(typeService.update(param));
    }

    @ApiOperation("删除类型值")
    @DeleteMapping("/delete")
    public JsonData delete(@ApiParam(value = "类型值ID", required = true) @RequestParam("typeId") Integer typeId) {
        return JsonData.successOperate(typeService.delete(typeId));
    }

    @ApiOperation("上移类型值")
    @PutMapping("/up")
    public JsonData up(@ApiParam(value = "类型值ID", required = true) @RequestParam("typeId") Integer typeId) {
        return JsonData.successOperate(typeService.up(typeId));
    }

    @ApiOperation("下移类型值")
    @PutMapping("/down")
    public JsonData down(@ApiParam(value = "类型值ID", required = true) @RequestParam("typeId") Integer typeId) {
        return JsonData.successOperate(typeService.down(typeId));
    }
}
