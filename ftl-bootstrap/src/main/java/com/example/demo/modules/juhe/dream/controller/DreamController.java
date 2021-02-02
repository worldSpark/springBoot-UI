package com.example.demo.modules.juhe.dream.controller;

import com.example.demo.modules.juhe.dream.service.DreamService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dream")
public class DreamController {
    @Autowired
    private DreamService dreamService;

    @RequestMapping("/getDreamInfo")
    public JSONObject getDreamInfo(String keyword){
        return dreamService.getDreamInfo(keyword);
    }


}
