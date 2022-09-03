package com.chenxingX.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 0XFA_CHENXINGX on 2022/8/30
 */

public class ExecutorFactory {

    public ExecutorService createThreadPoolInstance(Integer threadCount) {
         int corePoolSize = threadCount == null || threadCount <=0 ? 4 : threadCount;
         int maxPoolSize = 4;
         int queueCapacity = 10000;
         long keepAliveSeconds = 60L;
         maxPoolSize = corePoolSize * 2;
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueCapacity), new ThreadPoolExecutor.AbortPolicy());
    }

}
