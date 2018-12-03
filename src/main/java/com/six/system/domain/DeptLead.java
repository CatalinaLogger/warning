package com.six.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DeptLead {
    private Integer id;

    private Integer deptId;

    private Integer leadId;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}