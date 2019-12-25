package com.sxkj.uc.util.sql;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageUtil {

    /**
     * 每页记录数
     */
    private int pageSize = 10;
    /**
     * 总记录数
     */
    private int totalRow;
    /**
     * 当前页
     */
    private int currentPage = 1;

    private int totalPage;

    private String sql;

    public PageUtil(String sql, Page page) {
        setPageSize(page.getPageSize());
        setCurrentPage(page.getCurrentPage());
        setTotalRow(page.getTotal());
        setTotalPage();
        setSql();
        this.sql = sql + getSql();
    }

    public void setSql() {
        if (currentPage == 1) {
            sql = " limit " + pageSize;
        } else {
            sql = " limit " + (currentPage - 1) * pageSize + " " + pageSize;
        }
    }

    public void setTotalPage() {
        if (pageSize == 0) {
            totalPage = 0;
        } else {
            if (totalRow % pageSize == 0) {
                totalPage = totalRow / pageSize;
            } else {
                totalPage = (totalRow / pageSize) + 1;
            }
        }
    }

    public static void main(String[] args) {
        String sql = "select * from t_user where satus=1";
        Page page = new Page(108, 10, 3);
        PageUtil pu = new PageUtil(sql, page);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
