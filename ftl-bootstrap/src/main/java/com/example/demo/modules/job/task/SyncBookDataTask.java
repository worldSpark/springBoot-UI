package com.example.demo.modules.job.task;

import com.example.demo.modules.juhe.book.service.SyncBookService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SyncBookDataTask implements Job {

    @Autowired
    private SyncBookService syncBookService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        syncBookService.saveSyncBookInfo();
    }
}
