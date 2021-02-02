package com.example.demo.modules.job.task;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

@Service
public class WelcomeTask implements Job {


    public void sayWelcome() throws Exception {
        System.out.println("sayWelcome");
        //template.convertAndSend("/topic/getResponse", new Response("Welcome,websocket!"));
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("WelcomeTask");
    }
}
