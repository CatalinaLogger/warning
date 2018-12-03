package com.six.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class RoleUser {
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private Integer deptId;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}