package com.example.demo.modules.book.service;

import com.example.demo.core.util.DateUtil;
import com.example.demo.modules.book.mapper.AppleBookMapper;
import com.example.demo.modules.book.mapper.AppleBorrowMapper;
import com.example.demo.modules.book.model.AppleBook;
import com.example.demo.modules.book.model.AppleBorrow;
import com.example.demo.modules.sys.model.SysUser;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AppleBookServiceImpl implements AppleBookService {
    @Autowired
    private AppleBookMapper appleBookMapper;

    @Autowired
    private AppleBorrowMapper appleBorrowMapper;


    @Override
    public List<AppleBook> getAll(AppleBook appleBook, String keyword) {
        PageHelper.startPage(appleBook.getPage(),appleBook.getRows());
        Example example=new Example(AppleBook.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(keyword)){
            keyword="%"+keyword+"%";
            criteria.andLike("bookCode",keyword);
            criteria.orLike("bookName",keyword);
            criteria.orLike("author",keyword);
            criteria.orLike("publish",keyword);
        }
        return appleBookMapper.selectByExample(example);
    }

    @Override
    public void borrow(Integer bookId, SysUser sysUser) {
        //先将书籍的状态更新为借阅中
        AppleBook book = appleBookMapper.selectByPrimaryKey(bookId);
        if(book!=null && "库存中".equals(book.getState())){
            book.setState("借阅中");
            appleBookMapper.updateByPrimaryKey(book);
            AppleBorrow appleBorrow=new AppleBorrow();
            appleBorrow.setBookId(bookId);
            appleBorrow.setBorrowTime(new Date());
            appleBorrow.setOperator(sysUser.getUsername());
            appleBorrow.setOperatorTime(new Date());
            appleBorrow.setUserId(sysUser.getId());
            appleBorrow.setDepartReturnTime(DateUtil.getDateAgo(30));    //借阅30天
            appleBorrowMapper.insertAppleBorrow(appleBorrow);
        }
    }

    @Override
    public AppleBook getBookById(Integer bookId) {
        return appleBookMapper.selectByPrimaryKey(bookId);
    }
}
