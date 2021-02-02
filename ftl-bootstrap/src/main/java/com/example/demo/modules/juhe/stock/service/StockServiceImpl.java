package com.example.demo.modules.juhe.stock.service;

import com.example.demo.modules.juhe.stock.api.StockApi;
import com.example.demo.modules.juhe.stock.vo.StockResult;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Override
    public StockResult getStockListByType(Integer page, String type) {
        if("HK".equalsIgnoreCase(type)){                    // 香港股市列表
           return StockApi.getHKStockList(page);
        }else if("SH".equalsIgnoreCase(type)){            // 沪股列表
            return StockApi.getSHStockList(page);
        }else if("SZ".equalsIgnoreCase(type)){            // 深圳股市列表
            return StockApi.getSZStockList(page);
        }else if("USA".equalsIgnoreCase(type)){          // 美国股市列表
            return StockApi.getUSAStockList(page);
        }
        return new StockResult();
    }
}
