package com.example.demo.modules.book.vo;

import com.example.demo.modules.book.model.AppleBook;
import com.example.demo.modules.book.model.AppleBorrow;

public class AppleBorrowBookVo extends AppleBorrow{
    private AppleBook book;


    public AppleBook getBook() {
        return book;
    }

    public void setBook(AppleBook book) {
        this.book = book;
    }

}
