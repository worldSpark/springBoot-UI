package com.example.demo.conf;
import com.example.demo.modules.sys.model.SysUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
 
/**
 * 登录验证拦截
 *
 */
@Controller
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	Logger log = Logger.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String basePath = request.getContextPath();
		String path = request.getRequestURI();
		if(!doLoginInterceptor(path, basePath) ){//是否进行登陆拦截
			return true;
		}

		//如果登录了，会把用户信息存进session
		HttpSession session = request.getSession();
		SysUser users =  (SysUser) session.getAttribute("user");
		if(users==null){
			String requestType = request.getHeader("X-Requested-With");
			if(requestType!=null && requestType.equals("XMLHttpRequest")){
				response.setHeader("sessionstatus","timeout");
				response.getWriter().print("LoginTimeout");
				return false;
			} else {
				log.info("尚未登录，跳转到登录界面");
				response.sendRedirect("/toLogin");
			}
			return false;
		}else{
			SysUserContext.setUser(users);
		}
		return true;
	}
	
	/**
	 * 是否进行登陆过滤
	 * @param path
	 * @param basePath
	 * @return
	 */
	private boolean doLoginInterceptor(String path,String basePath){
		path = path.substring(basePath.length());
		Set<String> notLoginPaths = new HashSet<>();
		//设置不进行登录拦截的路径：登录注册和验证码
		notLoginPaths.add("/");
		notLoginPaths.add("/toLogin");
		notLoginPaths.add("/toError");
		notLoginPaths.add("/showRegister");
		notLoginPaths.add("/sys/login/logout");
		notLoginPaths.add("/swagger-ui.html");
		notLoginPaths.add("/error");
		notLoginPaths.add("/main");
		if(notLoginPaths.contains(path)) return false;
		return true;
	}
}