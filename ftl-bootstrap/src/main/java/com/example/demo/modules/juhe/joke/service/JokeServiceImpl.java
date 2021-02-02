package com.example.demo.modules.juhe.joke.service;

import com.example.demo.modules.juhe.joke.api.JokeApi;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class JokeServiceImpl implements JokeService {

    @Override
    public JSONObject getRecentJokeList(Integer page, Integer pagesize) {
        String str= JokeApi.getRecentJokeList(page,pagesize);
        JSONObject object=JSONObject.fromObject(str);
        return object;
    }
}
