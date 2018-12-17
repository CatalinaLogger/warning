package com.six.system.common.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class DeptParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiParam(value = "区域名称", required = true)
    @NotBlank(message = "区域名称不能为空")
    @Length(max = 60, min = 1, message = "区域名称长度在1~60位之间")
    private String name;
    @ApiParam(value = "父节点ID", example = "0")
    private Integer parentId = 0;
    @ApiParam("备注")
    @Length(max = 150, message = "备注长度不能超过150位")
    private String remark;
    @ApiParam(hidden = true)
    private List<Integer> leadKeys;
}
