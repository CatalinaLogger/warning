package com.six.system.common.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginUser {

    private Integer id;

    private String name;

    private String phone;

    private String mail;

    private String username;

    private Integer status;

    private String operateIp;
}
