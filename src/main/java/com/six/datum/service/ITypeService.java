package com.six.datum.service;

import com.six.datum.common.param.TypeParam;
import com.six.datum.domain.Type;

import java.util.List;

public interface ITypeService {

    List<Type> selectList(Integer parentId);

    String insert(TypeParam param);

    String update(TypeParam param);

    String delete(Integer typeId);

    String up(Integer typeId);

    String down(Integer typeId);
}
