package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class AQSCounter {

    private int num;

    private ReentrantLock lock = new ReentrantLock(true);

    public void increase() {
        lock.lock();
        try {
            num++;
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        AQSCounter aqs = new AQSCounter();
        for (int i = 0; i < 100; i++) {
            new Thread(aqs::increase, "t-" + i).start();
        }
    }
}
