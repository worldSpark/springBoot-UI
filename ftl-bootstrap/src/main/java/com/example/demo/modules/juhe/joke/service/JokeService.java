package com.example.demo.modules.juhe.joke.service;

import net.sf.json.JSONObject;

public interface JokeService {
    JSONObject getRecentJokeList(Integer page, Integer pagesize);
}
