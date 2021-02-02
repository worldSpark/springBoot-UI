package com.example.demo.modules.juhe.stock.controller;

import com.example.demo.core.result.PageResult;
import com.example.demo.modules.juhe.stock.service.StockService;
import com.example.demo.modules.juhe.stock.vo.StockData;
import com.example.demo.modules.juhe.stock.vo.StockResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @RequestMapping("/getStockListByType/{type}")
    public PageResult<StockData> getStockListByType(@PathVariable("type") String type, Integer page){
        PageResult pageResult=new PageResult<>();
        StockResult stockResult= stockService.getStockListByType(page,type);
        pageResult.setRows(stockResult.getData());
        pageResult.setTotal(Long.parseLong(stockResult.getTotalCount()));
        return pageResult;
    }

    @RequestMapping("/hk/list")
    public ModelAndView hkStock(){
        return new ModelAndView("/modules/stock/hk");
    }

    @RequestMapping("/sh/list")
    public ModelAndView shStock(){
        return new ModelAndView("/modules/stock/sh");
    }

    @RequestMapping("/usa/list")
    public ModelAndView usaStock(){
        return new ModelAndView("/modules/stock/usa");
    }

    @RequestMapping("/sz/list")
    public ModelAndView szStock(){
        return new ModelAndView("/modules/stock/sz");
    }

}
