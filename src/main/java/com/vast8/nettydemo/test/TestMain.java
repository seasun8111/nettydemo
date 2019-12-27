package com.vast8.nettydemo.test;

/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/28 18:08
 */
public class TestMain {

    public static void main(String[] args) {
        Res res = new Res() ;


        Thread t1 = new Thread(new Thead1(res));
        Thread t2 = new Thread(new Thead1(res));

        t1.start();
        t2.start();

    }

}
