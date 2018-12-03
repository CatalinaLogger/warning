package com.six.system.common.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class LoginInfo {
    private String name;
    private String avatar;
    private List<String> roles;
}
