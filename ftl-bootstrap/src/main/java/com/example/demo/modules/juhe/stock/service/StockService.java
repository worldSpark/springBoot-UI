package com.example.demo.modules.juhe.stock.service;

import com.example.demo.modules.juhe.stock.vo.StockResult;

public interface StockService {
    StockResult getStockListByType(Integer page, String type);
}
