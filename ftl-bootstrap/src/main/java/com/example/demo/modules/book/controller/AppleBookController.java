package com.example.demo.modules.book.controller;

import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.result.PageResult;
import com.example.demo.modules.book.model.AppleBook;
import com.example.demo.modules.book.service.AppleBookService;
import com.example.demo.modules.sys.model.SysUser;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.demo.core.entity.ProcessResult.ERROR;

@RestController
@RequestMapping("/api/book")
public class AppleBookController {
    @Autowired
    private AppleBookService appleBookService;

    @RequestMapping("/list")
    private ModelAndView showTableList(){
        return new ModelAndView("/modules/book/list");
    }

    @RequestMapping("/form")
    private ModelAndView showForm(){
        return new ModelAndView("/modules/book/form");
    }

    @RequestMapping("/pages")
    public PageResult<AppleBook> getAll(AppleBook appleBook, String keyword, HttpServletRequest request) {
        List<AppleBook> roleList = appleBookService.getAll(appleBook,keyword);
        return new PageResult(new PageInfo<AppleBook>(roleList));
    }

    @RequestMapping("/borrow/{bookId}")
    public ProcessResult borrow(@PathVariable Integer bookId,HttpServletRequest request) {
        SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
        try {
            if(sysUser==null) throw new Exception("用户未登录");
            appleBookService.borrow(bookId,sysUser);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @RequestMapping("/showBookDetail/{bookId}")
    public ProcessResult showBookDetail(@PathVariable Integer bookId) {
        try {
            AppleBook appleBook=appleBookService.getBookById(bookId);
            return new ProcessResult(appleBook);
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }


}
