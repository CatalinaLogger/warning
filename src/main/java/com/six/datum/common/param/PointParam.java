package com.six.datum.common.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PointParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiParam(value = "概况")
    @Length(max = 1000, message = "概况不能超过1000字")
    private String summary;
    @ApiParam(value = "详细信息")
    private String jsonInfo;
}
