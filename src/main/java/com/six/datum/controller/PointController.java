package com.six.datum.controller;

import com.six.datum.common.param.PointParam;
import com.six.datum.service.IPointService;
import com.six.system.common.pojo.JsonData;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags="监测点", description="监测点管理")
@Slf4j
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/datum/point")
public class PointController {

    @Autowired
    private IPointService pointService;

    @ApiOperation("获取区域监测点列表")
    @GetMapping("/select/list")
    public JsonData selectDeptList(@ApiParam(value = "用户ID：不传查询当前用户下所有区域监测点") @RequestParam(value = "deptId", required = false) Integer deptId,
                                   @ApiParam(value = "是否包含子监测点：默认不包含") @RequestParam(value = "child", defaultValue = "false") Boolean child) {
        return JsonData.success(pointService.selectByDeptId(deptId, child));
    }

    @ApiOperation("新增监测点")
    @PostMapping("/insert")
    public JsonData insert(@Valid PointParam param, BindingResult bindingResult) {
        return JsonData.successOperate(pointService.insert(param));
    }

    @ApiOperation("修改监测点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "监测点ID", dataType = "int", paramType = "query", required = true, example = "1"),
            @ApiImplicitParam(name = "leadKeys", value = "主管ID数组", dataType = "string", paramType = "query")
    })
    @PutMapping("/update")
    public JsonData update(@Valid PointParam param, BindingResult bindingResult) {
        return JsonData.successOperate(pointService.update(param));
    }

    @ApiOperation("删除监测点")
    @DeleteMapping("/delete")
    public JsonData delete(@ApiParam(value = "监测点ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(pointService.delete(deptId));
    }

    @ApiOperation("上移监测点")
    @PutMapping("/up")
    public JsonData up(@ApiParam(value = "监测点ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(pointService.up(deptId));
    }

    @ApiOperation("下移监测点")
    @PutMapping("/down")
    public JsonData down(@ApiParam(value = "监测点ID", required = true) @RequestParam("deptId") Integer deptId) {
        return JsonData.successOperate(pointService.down(deptId));
    }
}
