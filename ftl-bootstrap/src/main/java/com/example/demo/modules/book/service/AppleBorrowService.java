package com.example.demo.modules.book.service;

import com.example.demo.modules.book.model.AppleBorrow;
import com.example.demo.modules.book.vo.AppleBorrowBookVo;
import com.example.demo.modules.book.vo.BorrowHistoryVo;
import com.example.demo.modules.sys.model.SysUser;

import java.util.List;

public interface AppleBorrowService {
    List<AppleBorrowBookVo> getBorrowList(AppleBorrowBookVo appleBorrowBookVo,SysUser sysUser) throws Exception;

    void continueBorrow(AppleBorrow appleBorrow, SysUser sysUserVo);

    void returnBook(AppleBorrow appleBorrow, SysUser sysUserVo);

    List<BorrowHistoryVo> selectBorrowHistoryList(Integer bookId, Integer id);

    List<BorrowHistoryVo> selectBorrowHistoryListByUser(Integer id);
}
