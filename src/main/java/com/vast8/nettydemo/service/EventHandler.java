package com.vast8.nettydemo.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 14:43
 */
@Slf4j
public  abstract class EventHandler<EVENT> implements IEventHandler<EVENT>{


    private ArrayBlockingQueue queue;

    public EventHandler(ArrayBlockingQueue queue) {
        this.queue = queue;



    }

    @Override
    public void run() {
        while (true) {
            EVENT event = null;
            try {
                // 如果队列中没有数据了。2秒后任务结束
                event = (EVENT)queue.poll(20, TimeUnit.SECONDS);
                if (null == event) {
                    log.info("队列中没有数据了。EVENT HANDLE 任务结束");
                    return;
//                    Thread.sleep(2000);
//                    event = (EVENT)queue.poll(2, TimeUnit.SECONDS);
//                    if (null == event )
//                        throw new InterruptedException();
                }
                doEvent(event);
            } catch (InterruptedException ex) {
                log.info("队列中没有数据了。EVENT HANDLE 任务结束");
                return;
            }

        }
    }


    public abstract void doEvent(EVENT event);

}
