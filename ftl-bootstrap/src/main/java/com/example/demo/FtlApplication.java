package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;
@Controller
@EnableWebMvc
@SpringBootApplication(scanBasePackages = "com.example.demo")
@MapperScan(basePackages = "com.example.demo.modules.*.mapper")
@EnableScheduling
@Configuration
@EnableCaching
@EnableSwagger2
@ServletComponentScan
@ImportResource(locations = { "classpath:druid-bean.xml" })
public class FtlApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {
	private Logger logger= LoggerFactory.getLogger(FtlApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FtlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("服务启动完成！");
	}

	@RequestMapping({"/","","/toLogin"})
	public String home(){
		return "login";
	}
}
