package com.six.system.common.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AuthParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiParam(value = "权限名称", required = true)
    @NotBlank(message = "权限名称不能为空")
    @Length(max = 10, min = 1, message = "权限名称长度在1~10位之间")
    private String name;
    @ApiParam(value = "权限编码", required = true)
    @NotBlank(message = "权限编码不能为空")
    @Length(max = 50, min = 1, message = "权限编码长度在1~50位之间")
    private String path;
    @ApiParam("重定向路径")
    @Length(max = 100, message = "重定向路径不能超过100位")
    private String redirect;
    @ApiParam("权限图标")
    private String icon;
    @ApiParam(value = "隐藏：0 否，1 是", required = true, example = "0")
    @NotNull(message = "隐藏值不能为空")
    @Min(value = 0, message = "是否隐藏值只能为0或1")
    @Max(value = 1, message = "是否隐藏值只能为0或1")
    private Integer hidden;
    @ApiParam(value = "缓存：0 否，1 是", required = true, example = "0")
    @NotNull(message = "缓存值不能为空")
    @Min(value = 0, message = "是否缓存值只能为0或1")
    @Max(value = 1, message = "是否缓存值只能为0或1")
    private Integer cache;
    @ApiParam(value = "父节点ID", example = "0")
    private Integer parentId = 0;
    @ApiParam(value = "类型：0 目录，1 权限，2 按钮", required = true, example = "0")
    @NotNull(message = "类型不能为空")
    @Min(value = 0, message = "类型只能为0,1,2")
    @Max(value = 2, message = "类型只能为0,1,2")
    private Integer type;
    @ApiParam("备注")
    @Length(max = 150, message = "备注长度不能超过150位")
    private String remark;
}
