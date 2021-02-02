package com.example.demo.modules.book.mapper;

import com.example.demo.core.mapper.MyMapper;
import com.example.demo.modules.book.model.AppleBorrow;
import com.example.demo.modules.book.vo.AppleBorrowBookVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 因为mapper文件中写了多个resultMap,所以公共的api失效了，建议重写api
 */
public interface AppleBorrowMapper extends MyMapper<AppleBorrow> {
    List<AppleBorrowBookVo> selectBorrowByUser(@Param("username") String username);

    void updateAppleBorrowByPrimaryKeySelective(AppleBorrow appleBorrow);

    void insertAppleBorrow(AppleBorrow appleBorrow);

    List<AppleBorrow> selectBorrowHistoryList(@Param("userId")Integer userId,@Param("bookId") Integer bookId);

    List<AppleBorrow> selectBorrowHistoryListByUser(Integer userId);
}