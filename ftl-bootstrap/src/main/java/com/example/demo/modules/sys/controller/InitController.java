package com.example.demo.modules.sys.controller;

import com.example.demo.modules.sys.model.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 秦建平 on 2018/7/5.
 */

@Api(value ="初始模块", description = "初始模块Api",tags = {"初始模块"})
@RequestMapping
@Controller
public class InitController {

    @ApiOperation(value = "首页界面视图",notes = "首页界面视图")
    @GetMapping("/toIndex")
    public ModelAndView toIndex(ModelMap map, HttpServletRequest request){
        SysUser vo=(SysUser) request.getSession().getAttribute("user");
        if(vo!=null){
            map.put("user",vo);
        }
        return new ModelAndView("index");
    }

    @ApiOperation(value = "修改密码界面视图",notes = "修改密码界面视图")
    @GetMapping("/modifyPassword")
    public String modifyPassword(){
        return "modifyPWD";
    }

    @ApiOperation(value = "错误页面视图",notes = "错误页面视图")
    @GetMapping("/toError")
    public String error(){
        return "error";
    }

    @ApiOperation(value = "注册页面视图",notes = "注册页面视图")
    @GetMapping("/showRegister")
    public String showRegister(){
        return "register";
    }

    @ApiOperation(value = "主页视图",notes = "主页视图")
    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @ApiOperation(value = "头像页面视图",notes = "头像页面视图")
    @GetMapping("/avatar")
    public String avatar(){
        return "avatar";
    }

}
