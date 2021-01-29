package com.controller;

import com.config.V2Config;
import com.domain.AjaxResult;
import com.entity.SysUser;
import com.util.ShiroUtils;
import com.util.StringUtils;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 登录
 * @author: Mr.Wan
 * @create: 2021-01-26 17:19
 **/
@Slf4j
@Controller
public class LoginController {

    //配置文件
    @Autowired
    public V2Config v2Config;

    /**
     * 注册
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        return "register";
    }

    /**
     * 登录
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap modelMap) {
        try {
            if ((null != SecurityUtils.getSubject() && SecurityUtils.getSubject().isAuthenticated()) || SecurityUtils.getSubject().isRemembered()) {
                return "redirect:/index";
            } else {
                modelMap.put("RollVerification", v2Config.getRollVerification());
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }

    /**
     * 首页
     * @param user
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public AjaxResult index(@RequestBody SysUser user, String captcha, boolean rememberMe,
                        HttpServletRequest request) {
        Boolean yz = false;
        // 获取session中的验证码
        String verCode = (String) request.getSession().getAttribute("captcha");
        // 判断验证码
        if (captcha!=null && captcha.toLowerCase().equals(verCode.trim().toLowerCase())) {
            //清除验证码
            CaptchaUtil.clear(request);  // 清除session中的验证码
            yz=true;
        }

        // 判断验证码
        if (yz) {
            String userName = user.getUsername();
            Subject currentUser = SecurityUtils.getSubject();
            // 是否验证通过
            if (!currentUser.isAuthenticated()) {
                UsernamePasswordToken token = new UsernamePasswordToken(userName, user.getPassword());
                try {
                    if (rememberMe) {
                        token.setRememberMe(true);
                    }
                    // 存入用户
                    currentUser.login(token);
                    if (StringUtils.isNotNull(ShiroUtils.getUser())) {
                        // 若为前后端分离版本，则可把sessionId返回，作为分离版本的请求头authToken
                        return AjaxResult.success();
                    } else {
                        return AjaxResult.error(500, "未知账户");
                    }
                } catch (UnknownAccountException uae) {
                    log.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
                    return AjaxResult.error(500, "未知账户");
                } catch (IncorrectCredentialsException ice) {
                    log.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
                    return AjaxResult.error(500, "用户名或密码不正确");
                } catch (LockedAccountException lae) {
                    log.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
                    return AjaxResult.error(500, "账户已锁定");
                } catch (ExcessiveAttemptsException eae) {
                    log.info("对用户[" + userName + "]进行登录验证..验证未通过,错误次数过多");
                    return AjaxResult.error(500, "用户名或密码错误次数过多");
                } catch (AuthenticationException ae) {
                    // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
                    log.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
                    ae.printStackTrace();
                    return AjaxResult.error(500, "用户名或密码不正确");
                }
            } else {
                if (StringUtils.isNotNull(ShiroUtils.getUser())) {
                    return AjaxResult.success();
                } else {
                    return AjaxResult.error(500, "未知账户");
                }
            }
        } else {
            return AjaxResult.error(500, "验证码不正确!");
        }
    }

}
