package com.thinkerwolf.frameworkstudy.concurrent;

/**
 * 一种死锁实现方法
 *
 * @author wukai
 */
public class ThreadDeadLock {


    static class MyService {

        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        public void serviceA() {
            synchronized (lock1) {
                String n = Thread.currentThread().getName();
                System.out.println("Thread -> " + n + ", serviceA-AA");
                synchronized (lock2) {
                    n = Thread.currentThread().getName();
                    System.out.println("Thread -> " + n + ", serviceA-BB");
                }
            }
        }

        public void serviceB() {
            synchronized (lock2) {
                String n = Thread.currentThread().getName();
                System.out.println("Thread -> " + n + ", serviceB-AA");
                synchronized (lock1) {
                    n = Thread.currentThread().getName();
                    System.out.println("Thread -> " + n + ", serviceB-BB");
                }

            }
        }
    }


    public static void main(String[] args) {
        final MyService service = new MyService();
        Thread t1 = new Thread(() -> service.serviceA(), "A");
        Thread t2 = new Thread(() -> service.serviceB(), "B");
        t1.start();
        t2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end ........");
    }


}
