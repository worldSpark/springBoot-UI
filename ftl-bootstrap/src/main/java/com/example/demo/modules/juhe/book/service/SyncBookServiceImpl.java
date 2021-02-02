package com.example.demo.modules.juhe.book.service;

import com.example.demo.modules.book.mapper.AppleBookMapper;
import com.example.demo.modules.book.model.AppleBook;
import com.example.demo.modules.juhe.book.api.SyncBookApi;
import com.example.demo.modules.juhe.book.entity.SyncBookEntity;
import com.example.demo.modules.juhe.book.entity.SyncBookResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SyncBookServiceImpl implements SyncBookService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AppleBookMapper appleBookMapper;

    @Value("${spring.redis.isopen}")
    private boolean REDIS_IS_OPEN;

    private JSONObject getBookCatalog() {
        String key="catalog";
        String str="";
        JSONObject object=null;
        if(REDIS_IS_OPEN){
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            //采用redis缓存,先查缓存，如果存在则在缓存中获取
            if(stringRedisTemplate.hasKey(key)) {
                str = ops.get(key);
            }else{
                str= SyncBookApi.getBookCatalog();
                ops.set(key,str,6L, TimeUnit.HOURS);
            }
        }else{
            str= SyncBookApi.getBookCatalog();
        }
        object = JSONObject.fromObject(str);
        return object;
    }

    private List<Map<String,String>> convertCatalog(){
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String,String>> list=new ArrayList<>();
        JSONObject data = getBookCatalog();
        if(data!=null && data.getInt("error_code")==0){
            list= mapper.convertValue(data.get("result"), List.class);
        }
        return list;
    }


    private List<SyncBookEntity> getBookListInfo(String catalogId, Integer page) {
        List<SyncBookEntity> list=new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String str= SyncBookApi.getBookList(catalogId,page);
        JSONObject object = JSONObject.fromObject(str);
        if(object!=null && object.getInt("error_code")==0){
            SyncBookResult result= mapper.convertValue(object.get("result"), SyncBookResult.class);
            list=result.getData();
        }
        return list;
    }

    @Override
    public void saveSyncBookInfo() {
        List<Map<String,String>> catalogs=convertCatalog();
        catalogs.stream().forEach(map->{
            String catalogId=map.get("id");
            int page=0;
            List<SyncBookEntity> list;
            do{
                list=getBookListInfo(catalogId,page);
                page++;
                if(list.size()>0){
                    list.stream().forEach(entity->saveAppleBook(entity));
                }
            }while (list.size()>0);
        });
    }


    private void saveAppleBook(SyncBookEntity entity){
        List<AppleBook> books=appleBookMapper.selectByBookName(entity.getTitle());
        if(books.size()==0){
            AppleBook appleBook=new AppleBook();
            long currentTimes= DateTime.now().getMillis();
            appleBook.setBookName(entity.getTitle());
            appleBook.setBookCode("SYNC-"+currentTimes);
            appleBook.setBookType("纸质");
            appleBook.setLanguageClassification("中文");
            appleBook.setState("库存中");
            appleBook.setPublishTime(new Date());
            appleBook.setIsbn(currentTimes+"");
            appleBook.setIndustry(entity.getCatalog());
            appleBook.setCatalog(entity.getOnline());
            appleBook.setContent(entity.getSub1()+entity.getSub2());
            appleBook.setImg(entity.getImg());
            appleBookMapper.insert(appleBook);
        }
    }
}
