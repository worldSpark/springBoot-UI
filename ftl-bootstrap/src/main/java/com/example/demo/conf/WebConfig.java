package com.example.demo.conf;
 
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
 
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	LoginInterceptor loginInterceptor;
 
	/**
	 * 不需要登录拦截的url:登录注册和验证码
	 */
	final String[] notLoginInterceptPaths = {"/toLogin","/showRegister","/toError","/","/sys/login/signIn","/sys/login/logout","/swagger-ui.html"};
 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 日志拦截器
		//registry.addInterceptor(logInterceptor).addPathPatterns("/**");
		// 登录拦截器
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
	}
 
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
 
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/templates/");
		resolver.setSuffix(".ftl");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
 
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
 
	}
}