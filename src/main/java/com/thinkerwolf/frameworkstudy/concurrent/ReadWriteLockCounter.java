package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockCounter {
    private static void millisSleep(long l){try{Thread.sleep(l);}catch(Exception e){}}
    private static int count = 1;
    private static Random random = new Random();
    private static void read(Lock lock) {
        try { lock.lock();
              millisSleep(random.nextInt(300) + 50);
              System.out.println("Read count " + count);
        } finally { lock.unlock(); }
    }
    private static void write(Lock lock, int c) {
        try { lock.lock();
              millisSleep(random.nextInt(300) + 50);
              count = c;
        } finally { lock.unlock(); }
    }
    private static void test(Lock rk, Lock wk, int readc, int writec) {
        final long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(readc + writec);
        for (int i = 0; i < readc; i++) new Thread(() -> {read(rk); latch.countDown();}).start();
        for (int i = 0; i < writec; i++) new Thread(() -> {write(wk, random.nextInt()); latch.countDown();}).start();
        try { latch.await(); } catch (InterruptedException e) {}
        System.out.println("Spend time : " + (System.currentTimeMillis() - start));
    }
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        test(lock, lock, 18, 5);
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        Lock rk = rwLock.readLock(); Lock wk = rwLock.writeLock();
        test(rk, wk, 18, 5);
    }
}
