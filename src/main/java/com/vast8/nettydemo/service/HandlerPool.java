package com.vast8.nettydemo.service;


import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Getter;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.ObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 15:13
 */
public enum  HandlerPool {

    INSTANCE;

    public static HandlerPool getInstance() {
        return INSTANCE;
    }

    @Getter
    private ThreadPoolExecutor threadPoolExecutor;
    private ArrayBlockingQueue taskQueue = new ArrayBlockingQueue<Runnable>(16,false);

    HandlerPool(){

        this.threadPoolExecutor = new ThreadPoolExecutor(0, 16, 2, TimeUnit.SECONDS, taskQueue);

        threadPoolExecutor.setThreadFactory(new DefaultThreadFactory(VastEventHandler.class));
        threadPoolExecutor.setRejectedExecutionHandler((task, threadPoolExecutor1) ->{
            threadPoolExecutor1.getQueue().offer(task);
        });

    }

}
