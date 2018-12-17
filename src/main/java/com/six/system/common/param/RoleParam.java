package com.six.system.common.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleParam {
    @ApiParam(hidden = true)
    private Integer id;
    @NotBlank(message = "角色组/角色名称不能为空")
    @Length(max = 20, min = 1, message = "角色组/角色名称长度在1~20位之间")
    private String name;
    @ApiParam(value = "角色组ID", example = "0")
    private Integer parentId = 0;
    @ApiParam(hidden = true)
    private List<Integer> authKeys;
}
