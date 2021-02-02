package com.example.demo.modules.job.task;

import com.example.demo.modules.book.mapper.AppleBookMapper;
import com.example.demo.modules.book.model.AppleBook;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TaskTest implements Job{
	//@Autowired
	//SimpMessagingTemplate template;
	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private AppleBookMapper appleBookMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//template.convertAndSend("/topic/getResponse", new Response("Welcome,websocket!"));
		//System.out.println("我是非参数任务");
		long currentTimes= DateTime.now().getMillis();
		AppleBook appleBook=new AppleBook();
		appleBook.setBookName("帘外"+currentTimes+"天");
		appleBook.setBookCode("XB-"+currentTimes);
		appleBook.setAuthor("作者"+currentTimes);
		appleBook.setPublish("出版社"+currentTimes);
		appleBook.setBookType("纸质");
		appleBook.setLanguageClassification("中文");
		appleBook.setState("库存中");
		appleBook.setPrice(BigDecimal.valueOf(23.65));
		appleBook.setPublishTime(new Date());
		appleBook.setIsbn(currentTimes+"");
		appleBook.setIndustry("测试");
		appleBookMapper.insert(appleBook);

	}
}