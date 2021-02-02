package com.example.demo.core.exception;

import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.util.WebUtil;
import com.example.demo.modules.sys.model.SysLog;
import com.example.demo.modules.sys.model.SysUser;
import com.example.demo.modules.sys.mapper.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * 
 * <p>Title: MyExceptionResolver</p>
 * <p>Description: 自定义异常处理器</p>
 */
public class MyExceptionResolver implements HandlerExceptionResolver {
	@Autowired
	private SysLogMapper sysLogMapper;
	//前端控制器DispatcherServlet在进行HandlerMapping、调用HandlerAdapter执行Handler过程中，如果遇到异常就会执行此方法
	//handler最终要执行的Handler，它的真实身份是HandlerMethod
	//Exception ex就是接收到异常信息
	public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

		SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
		//将异常日志保存到数据库
		SysLog sysLog=new SysLog();
		sysLog.setLogLevel(40000);
		sysLog.setCreateTime(new Date());
		sysLog.setUserId(sysUser.getId());
		sysLog.setUsername(sysUser.getUsername());
		sysLog.setUrl(request.getRequestURI());
		sysLog.setResult(ex.toString());
		sysLogMapper.insert(sysLog);
		//输出异常
		//ex.printStackTrace();

		String url = "/toError";

		//统一异常处理代码
		//针对系统自定义的CustomException异常，就可以直接从异常类中获取异常信息，将异常处理在错误页面展示
		//异常信息
		String message = "";
		/*CustomException customException;
		//如果ex是系统 自定义的异常，直接取出异常信息
		if(ex instanceof CustomException){
			url = "/WEB-INF/jsp/timeout.jsp";
			customException = (CustomException)ex;
		}else if(ex instanceof UnauthorizedException){
			customException = new CustomException("功能功能未授权，请联系管理员！");
			url = "/WEB-INF/jsp/refuse.jsp";
		}else if(ex instanceof UnauthenticatedException){
			customException = new CustomException("当前会话已经超时，请重新登录！");
		}else if(ex instanceof UnknownSessionException){
			customException = new CustomException("当前会话已经超时，请重新登录！");
		}else{
			//针对非CustomException异常，对这类重新构造成一个CustomException，异常信息为“未知错误”
			customException = new CustomException("未知错误");
		}*/

		// ajax请求,则返回异常提示
		if (isAjax(request)) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			WebUtil.renderJson(new ProcessResult(ProcessResult.ERROR, ex.getMessage()), response);
			return null;
		}

		// 其他Http请求 直接返回错误页面
		else {
			//错误 信息
			message = ex.getMessage();
			request.setAttribute("message", message);
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			try {
				//转向到错误 页面
				request.getRequestDispatcher(url).forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		return new ModelAndView();
	}

	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	//前端控制器DispatcherServlet在进行HandlerMapping、调用HandlerAdapter执行Handler过程中，如果遇到异常就会执行此方法
	//handler最终要执行的Handler，它的真实身份是HandlerMethod
	//Exception ex就是接收到异常信息
	/*@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//输出异常
		ex.printStackTrace();
		
		//统一异常处理代码
		//针对系统自定义的CustomException异常，就可以直接从异常类中获取异常信息，将异常处理在错误页面展示
		//异常信息
		String message = null;
		CustomException customException = null;
		//如果ex是系统 自定义的异常，直接取出异常信息
		if(ex instanceof CustomException){
			customException = (CustomException)ex;
		}else{
			//针对非CustomException异常，对这类重新构造成一个CustomException，异常信息为“未知错误”
			customException = new CustomException("未知错误");
		}
		
		//错误 信息
		message = customException.getMessage();
		
		request.setAttribute("message", message);

		
		try {
			//转向到错误 页面
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView();
	}*/

}
