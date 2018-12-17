package com.six.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ToolMapper {
    /**
     * 查询已存在记录数
     * @param tableName 数据库表名
     * @param parentId 父级ID
     * @param id ID
     * @param field 属性名
     * @param value 属性值
     * @return 存在记录数
     */
    int existSize(@Param("tableName") String tableName, @Param("parentId") Object parentId, @Param("id") Object id, @Param("field") String field, @Param("value") Object value);
}