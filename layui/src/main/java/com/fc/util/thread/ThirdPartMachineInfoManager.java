package com.fc.util.thread;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述:通过单例去比较是否包含设备号
 *
 * @Author: wanpq
 * @Date: 2020/7/14 0014 15:32
 * @Version 1.0
 */
public class ThirdPartMachineInfoManager {

    private static final ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(1, 1, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    private static ThirdPartMachineInfoManager thirdPartMachineInfoManager = new ThirdPartMachineInfoManager();

    public static ThirdPartMachineInfoManager getInstance(){
        return thirdPartMachineInfoManager;
    }

    public void addExecuteTask(Runnable task) {
        if (task != null) {
            mThreadPool.execute(task);
        }
    }
}
