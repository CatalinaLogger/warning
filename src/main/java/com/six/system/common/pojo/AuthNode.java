package com.six.system.common.pojo;

import com.six.system.domain.Auth;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthNode extends Auth {
    private List<AuthNode> children = new ArrayList<>();

    public static AuthNode adapt(Auth auth) {
        AuthNode node = new AuthNode();
        BeanUtils.copyProperties(auth, node);
        return node;
    }
}
