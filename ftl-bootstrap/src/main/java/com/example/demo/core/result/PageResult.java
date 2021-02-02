package com.example.demo.core.result;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Niko on 2017/06/26.
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -3644950655568598241L;

    private long total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult(PageInfo<T> pageInfo){
        this.rows=pageInfo.getList();
        this.total=pageInfo.getTotal();
    }

    public PageResult(List<T> rows){
        PageInfo<T> pageInfo=new PageInfo<>(rows);
        this.rows=pageInfo.getList();
        this.total=pageInfo.getTotal();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
