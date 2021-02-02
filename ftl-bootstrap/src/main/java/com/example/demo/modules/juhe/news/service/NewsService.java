package com.example.demo.modules.juhe.news.service;

import net.sf.json.JSONObject;

public interface NewsService {
    public JSONObject getNewsList(String keyword);
}
