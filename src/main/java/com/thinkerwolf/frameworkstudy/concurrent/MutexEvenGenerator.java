package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator {

    private int curEventCount = 0;

    private ReentrantLock lock = new ReentrantLock(false);

    @Override
    public int next() {
        lock.lock();
        try {
            curEventCount++;
            Thread.yield();
            curEventCount++;
            return curEventCount;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        IntGenerator ig = new MutexEvenGenerator();
        EvenChecker.test(ig, 10);
    }
}
