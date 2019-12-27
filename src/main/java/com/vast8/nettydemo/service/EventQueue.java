package com.vast8.nettydemo.service;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 15:24
 */
public enum  EventQueue {

    INSTANCE;

    public static EventQueue getInstance() {
        return INSTANCE;
    }

    @Getter
    private ArrayBlockingQueue queue;

    EventQueue() {
        this.queue = new ArrayBlockingQueue(9999,true);
    }

}
