package com.six.system.domain;

import lombok.Data;

import java.util.Date;
@Data
public class Role {
    private Integer id;

    private String code;

    private String name;

    private Integer parentId;

    private Integer edit;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}