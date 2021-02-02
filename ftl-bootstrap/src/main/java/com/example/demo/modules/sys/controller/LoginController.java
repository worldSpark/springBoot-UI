
package com.example.demo.modules.sys.controller;

import com.example.demo.core.annotation.SysLog;
import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.property.PropertiesValue;
import com.example.demo.core.util.MD5Utils;
import com.example.demo.modules.sys.model.SysUser;
import com.example.demo.modules.sys.service.RoleService;
import com.example.demo.modules.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @author qjp
 * @since 2015-12-19 11:10
 */

@Api(value ="登录模块", description = "用户登录Api",tags = {"登录操作接口"})
@RestController
@RequestMapping("/sys/login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PropertiesValue propertiesValue;


    @ApiOperation(value = "用户登录",notes = "用户登录")
    @SysLog("用户登录")
    @RequestMapping(value = { "/signIn" }, method = RequestMethod.POST)
    public ProcessResult login(@ApiParam(name = "user",value="用户实体",required = true) SysUser user, HttpServletRequest request) {
        ProcessResult result=new ProcessResult();
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(token);
            request.getSession().setAttribute("user",userService.findByUserName(user.getUsername()));
            result.setResultStat(ProcessResult.SUCCESS);
            result.setData(request.getUserPrincipal());
            return result;
        } catch (AuthenticationException e) {
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }


    @SysLog("用户登出")
    @ApiOperation(value = "用户登出",notes = "用户登出")
    @RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
    public ModelAndView logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ModelAndView("login");
    }


    @ApiOperation(value = "修改密码",notes = "修改密码")
    @SysLog("修改密码")
    @RequestMapping(value = "/modifyPWD",method = RequestMethod.POST)
    public ProcessResult modifyPWD(@ApiParam(name = "oldpwd",value="旧密码",required = true) String oldpwd,
                                   @ApiParam(name = "password",value="新密码",required = true)String password,HttpSession session) {
        SysUser user=(SysUser) session.getAttribute("user");
        ProcessResult result=new ProcessResult();
        if(!MD5Utils.md5(oldpwd).equals(user.getPassword())){
            result.setMess("旧密码有误！");
            result.setResultStat(ProcessResult.ERROR);
            return result;
        }
        user.setPassword(MD5Utils.md5(password));
        int i = userService.save(user);
        SysUser sysUser = userService.findByUserName(user.getUsername());
        session.setAttribute("user",sysUser);
        result.setData(i);
        result.setResultStat(ProcessResult.SUCCESS);
        return result;
    }

    @ApiOperation(value = "校验注册验证码",notes = "校验注册验证码")
    @RequestMapping(value = "/showVerify",method = RequestMethod.POST)
    public  ProcessResult showVerify(@ApiParam(name = "email",value="邮箱",required = true) String email){
        return  userService.showVerify(email);
    }


    @ApiOperation(value = "用户注册",notes = "用户注册")
    @SysLog("用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public  ProcessResult register(@ApiParam(name = "sysUser",value="用户信息",required = true) SysUser vo){
        return  userService.saveUser(vo);
    }
}
