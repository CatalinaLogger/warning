package com.six.system.common.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UserParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiParam(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @Length(min = 1, max = 20, message = "用户名长度在1~20位之间")
    private String username;
    @ApiParam(value = "姓名", required = true)
    @NotBlank(message = "姓名不能为空")
    @Length(min = 1, max = 20, message = "姓名长度在1~20位之间")
    private String name;
    @ApiParam(value = "号码", required = true)
    @NotBlank(message = "号码不能为空")
    @Length(min = 1, max = 20, message = "号码长度在1~13位之间")
    private String phone;
    @ApiParam(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    @Length(min = 5, max = 60, message = "邮箱长度在5~60位之间")
    private String mail;
    @ApiParam("备注")
    @Length(max = 200, message = "备注长度不能超过200位")
    private String remark;
    @ApiParam(name = "deptKeys", value = "主管ID数组", type = "string", required = true)
    @NotEmpty(message = "所属区域不能为空")
    private List<Integer> deptKeys;
}
