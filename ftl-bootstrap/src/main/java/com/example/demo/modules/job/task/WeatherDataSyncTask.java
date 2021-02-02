package com.example.demo.modules.job.task;

import com.example.demo.modules.weather.service.WeatherDataService;
import com.example.demo.modules.weather.vo.WeatherResponse;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WeatherDataSyncTask implements Job {
    private final static Logger logger= LoggerFactory.getLogger(WeatherDataSyncTask.class);
    @Autowired
    private WeatherDataService weatherDataService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        WeatherResponse weatherResponse=weatherDataService.getDataByCityName("广州");
        logger.info("weatherResponse  DATA");
    }
}
