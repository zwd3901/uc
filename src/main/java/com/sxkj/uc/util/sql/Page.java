package com.sxkj.uc.util.sql;

import lombok.Data;

/**
 * @author zwd
 *
 * 分页参数类
 */
@Data
public class Page {
    /** 每页记录数 */
    private int pageSize = 10;
    /** 总记录数 */
    private int total;
    /** 当前页 */
    private int currentPage = 1;

    public Page(int total, int pageSize, int currentPage) {
        this.total = total;
        this.pageSize = pageSize;
        this .currentPage = currentPage;
    }
}
