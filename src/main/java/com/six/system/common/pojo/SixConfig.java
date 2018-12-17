package com.six.system.common.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SixConfig {
    @Value("${six.active}")
    private String active;
    @Value("${six.avatar}")
    private String avatar;
    @Value("${six.fast-url}")
    private String fastUrl;
    @Value("${six.role-lead-id}")
    private Integer roleLeadId;
}
