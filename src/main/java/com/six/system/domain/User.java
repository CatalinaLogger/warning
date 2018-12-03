package com.six.system.domain;

import lombok.Data;

import java.util.Date;

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

    private String weChat;

    private String qq;

    private String openWe;

    private String openQq;

    private String username;

    private String password;

    private String resetCode;

    private Integer parentId;

    private String parentName;

    private String parentUsername;

    private String level;

    private Integer status;

    private String remark;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;

    private String jsonInfo;
}