package com.six.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DeptUser {
    private Integer id;

    private Integer deptId;

    private Integer userId;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}