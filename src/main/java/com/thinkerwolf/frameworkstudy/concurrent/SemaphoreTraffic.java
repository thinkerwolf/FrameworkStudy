package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;

public class SemaphoreTraffic {
    public static void main(String[] args) {
        Semaphore s = new Semaphore(1); // 1次允许一个线程跑
        new Thread(()->{
            try {
                s.acquire();
                System.out.println("Thread t1 start ...");
                Thread.sleep(100);
                System.out.println("Thread t1 end ...");
            } catch (InterruptedException e) {
            } finally {
                s.release();
            }

        }).start();
        new Thread(()->{
            try {
                s.acquire();
                System.out.println("Thread t2 start ...");
                Thread.sleep(200);
                System.out.println("Thread t2 end ...");
            } catch (InterruptedException e) {
            } finally {
                s.release();
            }
        }).start();
    }
}
