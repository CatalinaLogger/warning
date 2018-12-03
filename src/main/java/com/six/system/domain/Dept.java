package com.six.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Dept {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private String remark;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}