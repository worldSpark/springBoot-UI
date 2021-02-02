package com.example.demo.modules.book.mapper;

import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.book.model.AppleBook;

import java.util.List;


public interface AppleBookMapper extends MyMapper<AppleBook> {

    List<AppleBook> selectByBookName(String name);
}