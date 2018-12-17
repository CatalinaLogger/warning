package com.six.system.common.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class LoginUser implements Serializable {
    private Integer id;

    private String name;

    private String phone;

    private String mail;

    private String username;

    private Integer status;

    private String operateIp;
}
