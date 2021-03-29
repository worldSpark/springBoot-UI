package com.fc.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description 用户工具
 * @author luoy
 * @Date 2018年10月20日 上午10:22:00
 * @version 1.0.0
 **/
@Component
public class SecurityUser {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUser.class);

    //存取到session用户的key
    public static final String SESSION_ATTRIBUTE_USER = "frontUser";

    private static SecurityUser securityUser;

    //实例化
    @PostConstruct
    public void init() {
        securityUser = this;
    }

    private SecurityUser() {

    }

    //------------------------------ 公用方法↓ ------------------------------



    /**
     * @Description 获得当前连接的session
     * @return session
     */
    public static HttpSession getCurrentSession(){
        HttpSession session = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            session = request.getSession();
        }catch (Exception e){
            logger.error("SecurityUser获取Session失败！");
        }
        return session;
    }

    //------------------------------ 公用方法↑ ------------------------------
    //------------------------------ 私有方法↓ ------------------------------

    //------------------------------ 私有方法↑ ------------------------------
}
