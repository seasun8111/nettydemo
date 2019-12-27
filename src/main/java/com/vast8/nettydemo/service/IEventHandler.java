package com.vast8.nettydemo.service;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/25 14:44
 */
public interface IEventHandler<E> extends Runnable{



    public void doEvent(E event);
}
