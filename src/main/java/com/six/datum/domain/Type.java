package com.six.datum.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Type {
    private Integer id;

    private String name;

    private Integer parentId;

    private Integer seq;

    private String remark;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}