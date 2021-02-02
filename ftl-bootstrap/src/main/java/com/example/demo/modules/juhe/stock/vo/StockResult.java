package com.example.demo.modules.juhe.stock.vo;

import java.util.List;

public class StockResult {
    private String totalCount;
    private String page;
    private String num;
    private List<StockData> data;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<StockData> getData() {
        return data;
    }

    public void setData(List<StockData> data) {
        this.data = data;
    }
}
