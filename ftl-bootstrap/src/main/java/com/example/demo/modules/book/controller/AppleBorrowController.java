package com.example.demo.modules.book.controller;

import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.result.PageResult;
import com.example.demo.modules.book.model.AppleBorrow;
import com.example.demo.modules.book.service.AppleBorrowService;
import com.example.demo.modules.book.vo.AppleBorrowBookVo;
import com.example.demo.modules.book.vo.BorrowHistoryVo;
import com.example.demo.modules.sys.model.SysUser;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.core.entity.ProcessResult.ERROR;

@RestController
@RequestMapping("/api/borrow")
public class AppleBorrowController {
    @Autowired
    private AppleBorrowService appleBorrowService;

    @RequestMapping("/list")
    private ModelAndView showTableList(){
        return new ModelAndView("/modules/borrow/list");
    }

    @RequestMapping("/form")
    private ModelAndView showForm(){
        return new ModelAndView("/modules/borrow/borrowHistory");
    }

    @RequestMapping("/pages")
    public PageResult<AppleBorrowBookVo> getBorrowList(AppleBorrowBookVo appleBorrowBookVo,  HttpServletRequest request) throws Exception {
        SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
        List<AppleBorrowBookVo> roleList = appleBorrowService.getBorrowList(appleBorrowBookVo,sysUser);
        return new PageResult(new PageInfo<AppleBorrowBookVo>(roleList));
    }

  @RequestMapping("/continueBorrow")
    public ProcessResult continueBorrow(AppleBorrow appleBorrow, HttpServletRequest request) {
        SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
        try {
            if(sysUser==null) throw new Exception("用户未登录");
            appleBorrowService.continueBorrow(appleBorrow,sysUser);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @RequestMapping("/returnBook")
    public ProcessResult returnBook(AppleBorrow appleBorrow, HttpServletRequest request) {
        SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
        try {
            if(sysUser==null) throw new Exception("用户未登录");
            appleBorrowService.returnBook(appleBorrow,sysUser);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }


    @RequestMapping("/showBorrowHistoryList/{bookId}")
    public ProcessResult showBorrowHistoryList(@PathVariable Integer bookId,HttpServletRequest request) {
        try {
            SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
            if(sysUser==null) throw new Exception("用户未登录");
            List<BorrowHistoryVo> borrowHistoryVos=appleBorrowService.selectBorrowHistoryList(bookId,sysUser.getId());
            borrowHistoryVos=borrowHistoryVos.stream().sorted(Comparator.comparing(BorrowHistoryVo::getOperatorTime)).collect(Collectors.toList());
            return new ProcessResult(borrowHistoryVos);
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @RequestMapping("/showBorrowHistoryListByUser")
    public ProcessResult showBorrowHistoryListByUser(HttpServletRequest request) {
        try {
            SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
            if(sysUser==null) throw new Exception("用户未登录");
            List<BorrowHistoryVo> borrowHistoryVos=appleBorrowService.selectBorrowHistoryListByUser(sysUser.getId());
            //borrowHistoryVos=borrowHistoryVos.stream().sorted(Comparator.comparing(BorrowHistoryVo::getOperatorTime)).collect(Collectors.toList());
            return new ProcessResult(borrowHistoryVos);
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }


}
