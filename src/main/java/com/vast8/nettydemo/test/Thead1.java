package com.vast8.nettydemo.test;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/28 18:07
 */
public class Thead1 implements Runnable{

    int i ;

    private Res res ;

    public Thead1(Res res){

        this.res = res;
    }

    @Override
    public void run() {
        for(;;) {
            System.out.println(

                    Thread.currentThread() +" : "+
                    res.increase());


        }
    }
}
