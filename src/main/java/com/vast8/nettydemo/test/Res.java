package com.vast8.nettydemo.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/28 18:20
 */
public class Res {


    private int i ;



    public int increase() {
        this.i++;
        LockSupport.parkNanos(Thread.currentThread(), TimeUnit.SECONDS.toNanos(1));
        return i;

    }
}
