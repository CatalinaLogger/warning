package com.six.datum.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PointLevel {
    private Integer id;

    private Integer pointId;

    private Integer level;

    private String operateName;

    private Date time;

    private Integer status;
}