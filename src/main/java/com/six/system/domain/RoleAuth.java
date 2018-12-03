package com.six.system.domain;

import lombok.Data;

import java.util.Date;
@Data
public class RoleAuth {
    private Integer id;

    private Integer roleId;

    private Integer menuId;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}