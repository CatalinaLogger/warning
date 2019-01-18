package com.six.datum.common.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class TypeParam {
    @ApiParam(hidden = true)
    private Integer id;
    @NotBlank(message = "类型/类型值名称不能为空")
    @Length(max = 20, min = 1, message = "类型/类型值名称长度在1~20字之间")
    private String name;
    @ApiParam(value = "类型ID", example = "0")
    private Integer parentId = 0;
    @ApiParam(value = "备注")
    @Length(max = 100, message = "备注不能超过100字")
    private String remark;
}
