package com.six.system.common.pojo;

import com.six.system.domain.Dept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeptNode extends Dept {
    private List<DeptNode> children = new ArrayList<>();

    public static DeptNode adapt(Dept dept) {
        DeptNode node = new DeptNode();
        BeanUtils.copyProperties(dept, node);
        return node;
    }
}
