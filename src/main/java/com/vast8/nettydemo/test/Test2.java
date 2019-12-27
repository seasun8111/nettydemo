package com.vast8.nettydemo.test;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/28 18:35
 */
public class Test2 {


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {



        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,16,1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));


        Future f = threadPoolExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sec = 5;
                Thread.sleep(TimeUnit.SECONDS.toMillis(sec));
                return sec;

            }
        });
        System.out.println(f.get());
        threadPoolExecutor.shutdownNow();
    }
}
