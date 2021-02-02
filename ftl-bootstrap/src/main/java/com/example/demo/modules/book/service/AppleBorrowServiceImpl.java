package com.example.demo.modules.book.service;

import com.example.demo.core.util.DateUtil;
import com.example.demo.modules.book.mapper.AppleBookMapper;
import com.example.demo.modules.book.mapper.AppleBorrowMapper;
import com.example.demo.modules.book.model.AppleBook;
import com.example.demo.modules.book.model.AppleBorrow;
import com.example.demo.modules.book.vo.AppleBorrowBookVo;
import com.example.demo.modules.book.vo.BorrowHistoryVo;
import com.example.demo.modules.sys.model.SysUser;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AppleBorrowServiceImpl implements AppleBorrowService {
    @Autowired
    private AppleBookMapper appleBookMapper;

    @Autowired
    private AppleBorrowMapper appleBorrowMapper;


    @Override
    public List<AppleBorrowBookVo> getBorrowList(AppleBorrowBookVo appleBorrowBookVo,SysUser sysUser) throws Exception {
        PageHelper.startPage(appleBorrowBookVo.getPage(),appleBorrowBookVo.getRows());
        if(sysUser==null){
            throw new Exception("用户未登录");
        }
        return appleBorrowMapper.selectBorrowByUser(sysUser.getUsername());
    }

    //续借  30天
    @Override
    public void continueBorrow(AppleBorrow appleBorrow, SysUser sysUser) {
        appleBorrow.setDepartReturnTime(DateUtil.getDateAgo(30));    //借阅30天
        appleBorrowMapper.updateAppleBorrowByPrimaryKeySelective(appleBorrow);
    }

    //还书
    @Override
    public void returnBook(AppleBorrow appleBorrow, SysUser sysUser) {
        //将书籍的状态设置为‘库存中’
        AppleBook appleBook=new AppleBook();
        appleBook.setState("库存中");
        appleBook.setId(appleBorrow.getBookId());
        appleBookMapper.updateByPrimaryKeySelective(appleBook);

        //设置借阅记录的归还日期
        AppleBorrow borrow=new AppleBorrow();
        borrow.setId(appleBorrow.getId());
        borrow.setReturnTime(new Date());
        appleBorrowMapper.updateAppleBorrowByPrimaryKeySelective(borrow);
    }

    @Override
    public List<BorrowHistoryVo> selectBorrowHistoryList(Integer bookId, Integer userId) {
        List<BorrowHistoryVo> vos=new ArrayList<>();
        List<AppleBorrow> list = appleBorrowMapper.selectBorrowHistoryList(userId, bookId);
        if(list.size()>0){
            for (AppleBorrow borrow:list){
                BorrowHistoryVo vo=new BorrowHistoryVo();
                BeanUtils.copyProperties(borrow,vo);
                AppleBook book = appleBookMapper.selectByPrimaryKey(borrow.getBookId());
                if(book!=null){
                    vo.setBookName(book.getBookName());
                }
                vos.add(vo);
            }
        }
        return vos;
    }

    @Override
    public List<BorrowHistoryVo> selectBorrowHistoryListByUser(Integer userId) {
        List<BorrowHistoryVo> vos=new ArrayList<>();
        List<AppleBorrow> list = appleBorrowMapper.selectBorrowHistoryListByUser(userId);
        if(list.size()>0){
            for (AppleBorrow borrow:list){
                BorrowHistoryVo vo=new BorrowHistoryVo();
                BeanUtils.copyProperties(borrow,vo);
                AppleBook book = appleBookMapper.selectByPrimaryKey(borrow.getBookId());
                if(book!=null){
                    vo.setBookName(book.getBookName());
                }
                vos.add(vo);
            }
        }
        return vos;
    }
}
