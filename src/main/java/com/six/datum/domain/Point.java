package com.six.datum.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Point {
    private Integer id;

    private Integer deptId;

    private String deptName;

    private Integer typeId;

    private String typeName;

    private String name;

    private String preview;

    private String address;

    private String summary;

    private Double lat;

    private Double lng;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;

    private String jsonInfo;
}