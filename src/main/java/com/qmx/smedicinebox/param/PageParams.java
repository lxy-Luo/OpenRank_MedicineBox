package com.qmx.smedicinebox.param;

import lombok.Data;

@Data
public class PageParams {
    /**
     * 当前页码
     */
    public Integer page;
    /**
     * 每页显示记录数
     */
    public Integer limit;
    /**
     * 排序字段
     */
    public String order_field;
    /**
     * 排序方式
     */
    public String order;
    /**
     * 模糊匹配字段
     */
    private String search;

    /**
     * 模糊匹配的字段名
     */
    private String searchField;
}
