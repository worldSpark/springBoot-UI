package com.example.demo.modules.juhe.book.entity;


import java.util.List;

public class SyncBookResult {
    private List<SyncBookEntity> data;
    private Integer totalNum;
    private Integer pn;
    private Integer rn;

    public List<SyncBookEntity> getData() {
        return data;
    }

    public void setData(List<SyncBookEntity> data) {
        this.data = data;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getRn() {
        return rn;
    }

    public void setRn(Integer rn) {
        this.rn = rn;
    }
}
