package com.six.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserLogin {
    private Integer id;

    private Integer userId;

    private String userName;

    private String loginIp;

    private Date loginTime;

    private String loginAddress;

    private String loginSystem;

    private String loginBrowser;

    private String loginBrowserVersion;
}