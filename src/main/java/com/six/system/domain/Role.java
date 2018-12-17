package com.six.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
@Data
public class Role {
    private Integer id;

    private String name;

    private Integer parentId;

    private Integer edit;

    private Integer seq;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
    @JsonIgnore
    private Integer deptId;
}