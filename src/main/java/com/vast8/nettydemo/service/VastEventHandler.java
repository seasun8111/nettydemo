package com.vast8.nettydemo.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 15:05
 */
@Slf4j
public class VastEventHandler extends EventHandler<JSONObject>{


    public VastEventHandler(ArrayBlockingQueue queue) {
        super(queue);
    }

    @Override
    public void doEvent(JSONObject event) {
        try {
            log.info(" ====  取得事件处理中... :  "+event);

            log.info(" ====  处理完毕 :  ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
