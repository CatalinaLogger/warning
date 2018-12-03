package com.six.system.common.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author jin
 * @description: 分页查询数据
 * @date 2018/5/5
 */
@Data
public class PageData<T> {

    private Integer page; // 当前页码
    private Integer size; // 每页数量

    private Integer number; // 总页数
    private Integer total; // 总记录数

    private Integer count; // 当前页记录数
    private List<T> data;   // 当前页记录

    public PageData(Integer page, Integer size, Integer total, List<T> data) {
        this.page = page;
        this.size = size;
        this.number = total / this.size + 1;
        this.total = total;
        if (data == null) {
            this.count = 0;
        } else {
            this.count = data.size();
        }
        this.data = data;
    }
}
