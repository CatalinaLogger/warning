package com.six.system.common.pojo;

import com.six.system.domain.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleNode extends Role {
    private List<RoleNode> children = new ArrayList<>();

    public static RoleNode adapt(Role role) {
        RoleNode node = new RoleNode();
        BeanUtils.copyProperties(role, node);
        return node;
    }
}
