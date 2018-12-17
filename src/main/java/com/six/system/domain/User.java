package com.six.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Integer id;

    private String name;

    private String nickName;

    private Integer sex;

    private Date birthday;

    private String phone;

    private String mail;

    private String say;

    private String avatar;

    private String username;

    private String password;

    private String resetCode;

    private Integer status;

    private String remark;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;

    private String jsonInfo;

    private List<Dept> dept;
    @JsonIgnore
    private Integer deptId;
}