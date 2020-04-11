package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicEvenGenerator extends IntGenerator {

    private volatile int currentEventCount = 0;

    private static AtomicIntegerFieldUpdater<AtomicEvenGenerator> updater;

    static {
        updater = AtomicIntegerFieldUpdater.newUpdater(AtomicEvenGenerator.class, "currentEventCount");
    }

    @Override
    public int next() {
        return updater.addAndGet(this, 2);
    }

    public static void main(String[] args) {
        AtomicEvenGenerator ig = new AtomicEvenGenerator();
        EvenChecker.test(ig, 10);
    }
}
