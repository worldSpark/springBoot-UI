package com.example.demo.modules.book.service;

import com.example.demo.modules.book.model.AppleBook;
import com.example.demo.modules.sys.model.SysUser;

import java.util.List;

public interface AppleBookService {
    List<AppleBook> getAll(AppleBook appleBook, String keyword);

    void borrow(Integer bookId, SysUser sysUser);

    AppleBook getBookById(Integer bookId);
}
