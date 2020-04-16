package com.thinkerwolf.frameworkstudy;

public class CacheLineTests {
    static class T { volatile long l; }
    static class P extends T { long p1, p2, p3, p4, p5, p6, p7; }
    private static final long LOOP_NUM = 1000_0000;
    static T[] arr = new T[2];
    static {
        arr[0] = new T(); arr[1] = new T(); // no padding
        //arr[0] = new P(); arr[1] = new P(); // padding
    }
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < LOOP_NUM; i++)
                arr[0].l = i;
        });
        Thread t2 = new Thread(() -> {
            for (long i = 0; i < LOOP_NUM; i++)
                arr[1].l = i;
        });
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println(System.currentTimeMillis() - start);
    }
}
