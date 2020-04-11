package com.thinkerwolf.frameworkstudy.concurrent;

/**
 * 不合适共享资源获取
 */
public class EvenGenerator extends IntGenerator {

    private int curEvenValue = 0;

    @Override
    public synchronized int next() {
        ++curEvenValue;
        Thread.yield();
        ++curEvenValue;
        return curEvenValue;
    }

    public static void main(String[] args) {
        EvenGenerator generator = new EvenGenerator();
        EvenChecker.test(generator, 10);
    }
}
