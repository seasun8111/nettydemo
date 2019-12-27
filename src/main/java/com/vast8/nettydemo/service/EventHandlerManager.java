package com.vast8.nettydemo.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 16:50
 */
public enum  EventHandlerManager{
    manager();


    private final Logger logger = LoggerFactory.getLogger(EventHandlerManager.class);

    boolean ON  = true;

    long threshold = 100;

    @Getter
    Thread manageThead ;


    public void start() {
        this.manageThead.start();
    }

    EventHandlerManager() {

        this.manageThead = new Thread(()-> {
            {
                ArrayBlockingQueue eventQueue = EventQueue.getInstance().getQueue();
                ThreadPoolExecutor executor = HandlerPool.getInstance().getThreadPoolExecutor();
                int lastRemain = eventQueue.remainingCapacity();
                int remainingCapacity;
                while (ON) {
                    // 1秒检查一次queue 如果消费速度慢于生产速度，达到一定阈值后，自动增加消费handler
//                    logger.info("Active EventHander count is {} Remain Capacity is {}.",
//                    executor.getActiveCount(),  eventQueue.remainingCapacity());
                    remainingCapacity = eventQueue.remainingCapacity();
                    if( (remainingCapacity < threshold
                            && remainingCapacity < lastRemain)
                            ||  executor.getActiveCount() == 0 ) {

                        logger.info("{} add new EventHander ",   executor.getActiveCount());
                        Runnable  eventHandler =  new VastEventHandler(eventQueue);
//                        executor.execute(task);

                        executor.execute(eventHandler);

                    }
                    lastRemain = remainingCapacity;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        manageThead.setDaemon(true);


    }





}
