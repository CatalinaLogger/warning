package com.six.datum.controller;

import com.six.datum.common.param.PointParam;
import com.six.datum.service.IPointService;
import com.six.system.common.param.PageParam;
import com.six.system.common.pojo.JsonData;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Api(tags="监测点", description="监测点管理")
@Slf4j
@CrossOrigin // 支持跨域请求
@RestController
@RequestMapping("/datum/point")
public class PointController {

    @Autowired
    private IPointService pointService;

    @ApiOperation("分页获取区域监测点列表")
    @GetMapping("/select/page")
    public JsonData selectPage(@ApiParam(value = "是否绑定了区域：默认true") @RequestParam(value = "bound", defaultValue = "true") boolean bound,
                               @ApiParam(value = "区域ID", example = "1") @RequestParam(value = "deptId", required = false) Integer deptId,
                               @ApiParam(value = "预警等级：默认返回不带预警等级的结果", example = "1") @RequestParam(value = "level", required = false) Integer level,
                               @ApiParam("查询条件") @RequestParam(value = "query", required = false) String query,
                               @Valid PageParam param, BindingResult bindingResult) {
        return JsonData.success(pointService.selectPage(bound, deptId, level, query, param));
    }

    @ApiOperation("获取监测点详情")
    @GetMapping("/select")
    public JsonData select(@ApiParam(value = "监测点ID", required = true) @RequestParam("pointId") Integer pointId) {
        return JsonData.success(pointService.select(pointId));
    }

    @ApiOperation("新增监测点")
    @PostMapping("/insert")
    public JsonData insert(@Valid PointParam param, BindingResult bindingResult) {
        return JsonData.successOperate(pointService.insert(param));
    }

    @ApiOperation("修改监测点")
    @ApiImplicitParam(name = "id", value = "监测点ID", dataType = "int", paramType = "query", required = true, example = "1")
    @PutMapping("/update")
    public JsonData update(@Valid PointParam param, BindingResult bindingResult) {
        return JsonData.successOperate(pointService.update(param));
    }

    @ApiOperation("删除监测点")
    @DeleteMapping("/delete")
    public JsonData delete(@ApiParam(value = "监测点ID", required = true) @RequestParam("pointId") Integer pointId) {
        return JsonData.successOperate(pointService.delete(pointId));
    }

    @ApiOperation("上移监测点")
    @PutMapping("/up")
    public JsonData up(@ApiParam(value = "监测点ID", required = true) @RequestParam("pointId") Integer pointId) {
        return JsonData.successOperate(pointService.up(pointId));
    }

    @ApiOperation("下移监测点")
    @PutMapping("/down")
    public JsonData down(@ApiParam(value = "监测点ID", required = true) @RequestParam("pointId") Integer pointId) {
        return JsonData.successOperate(pointService.down(pointId));
    }

    @ApiOperation("监测点绑定区域")
    @ApiImplicitParam(name = "pointKeys", value = "监测点ID数组，示例：1,2,3", dataType = "string", paramType = "query", required = true)
    @PostMapping("/dept/insert")
    public JsonData insertDept(@ApiParam(value = "区域ID", required = true) @RequestParam("deptId") Integer deptId,
                               @RequestParam("pointKeys") List<Integer> pointKeys) {
        return JsonData.successOperate(pointService.insertPointDept(deptId, pointKeys));
    }

    @ApiOperation("监测点解绑区域")
    @ApiImplicitParam(name = "pointKeys", value = "监测点ID数组，示例：1,2,3", dataType = "string", paramType = "query", required = true)
    @PostMapping("/dept/delete")
    public JsonData deleteDept(@RequestParam("pointKeys") List<Integer> pointKeys) {
        return JsonData.successOperate(pointService.deletePointDept(pointKeys));
    }

    @ApiOperation("获取监测点预警等级")
    @GetMapping("/level/select")
    public JsonData selectLevel(@ApiParam(value = "监测点ID", required = true, example = "1") @RequestParam(value = "pointId") Integer pointId) {
        return JsonData.success(pointService.selectLevel(pointId));
    }

    @ApiOperation("根据时间段获取监测点预警等级列表")
    @GetMapping("/level/select/list")
    public JsonData selectLevelList(@ApiParam(value = "监测点ID", required = true, example = "1") @RequestParam(value = "pointId") Integer pointId,
                                    @ApiParam("开始日期") @RequestParam(value = "start", required = false) String start,
                                    @ApiParam("结束日期") @RequestParam(value = "end", required = false) String end,
                                    @ApiParam(value = "索引：从1开始", required = true) @RequestParam(value = "index", defaultValue = "1") int index) {
        return JsonData.success(pointService.selectLevelList(pointId, start, end, index));
    }

    @ApiOperation("上传监测点现场图")
    @PostMapping("/photo/upload")
    public JsonData uploadPhoto(@ApiParam(value = "监测点ID", required = true, example = "1") @RequestParam(value = "pointId") Integer pointId,
                                @ApiParam(value = "图片文件", required = true) @RequestParam("file") MultipartFile file) {
        return JsonData.successOperate(pointService.uploadPhoto(pointId, file));
    }

    @ApiOperation("获取监测点现场图列表")
    @GetMapping("/photo/select/list")
    public JsonData selectPhotoList(@ApiParam(value = "监测点ID", required = true, example = "1") @RequestParam(value = "pointId") Integer pointId) {
        return JsonData.success(pointService.selectPhotoList(pointId));
    }

    @ApiOperation("删除监测点现场图")
    @DeleteMapping("/photo/delete")
    public JsonData deletePhoto(@ApiParam(value = "现场图ID", required = true) @RequestParam("photoId") Integer photoId) {
        return JsonData.successOperate(pointService.deletePhoto(photoId));
    }
}
