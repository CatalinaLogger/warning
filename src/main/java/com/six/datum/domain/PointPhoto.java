package com.six.datum.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PointPhoto {
    private Integer id;

    private String url;

    private Integer pointId;

    private String operateIp;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}