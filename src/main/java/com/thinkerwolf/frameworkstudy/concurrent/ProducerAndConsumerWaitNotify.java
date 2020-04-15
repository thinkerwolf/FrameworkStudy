package com.thinkerwolf.frameworkstudy.concurrent;

import com.thinkerwolf.frameworkstudy.common.Util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerAndConsumerWaitNotify {
    final Queue<Integer> queue;
    private final Object lock = new Object();
    AtomicInteger count = new AtomicInteger(1);
    AtomicInteger consumeCounter = new AtomicInteger(1);
    private int MAX_SIZE = 100;
    private volatile boolean produceStop = false;

    public ProducerAndConsumerWaitNotify() {
        this.queue = new LinkedList<>();
    }

    public static void main(String[] args) {
        ProducerAndConsumerWaitNotify pc = new ProducerAndConsumerWaitNotify();
        Thread p1 = new Thread(pc::produce, "p1");
        Thread p2 = new Thread(pc::produce, "p2");
        Thread c1 = new Thread(pc::consume, "c1");
        Thread c2 = new Thread(pc::consume, "c2");
        System.out.println("start .....");
        p1.start();
        p2.start();
        c1.start();
        c2.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pc.stopProduce();
    }

    private boolean checkConsumeStop() {
        return queue.isEmpty() && produceStop;
    }

    public void consume() {
        synchronized (lock) {
            for (; ; ) {
                try {
                    if (checkConsumeStop()) {
                        break;
                    }
                    while (queue.isEmpty()) {
                        lock.wait();
                    }
                    System.out.println(Thread.currentThread().getName() + " - " + queue.poll());
                    lock.notifyAll();
                    if (checkConsumeStop()) {
                        break;
                    }
                    lock.wait();
                    Thread.sleep(Util.nextInt(100));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public void produce() {
        synchronized (lock) {
            for (; ; ) {
                try {
                    if (produceStop) {
                        break;
                    }
                    while (queue.size() > MAX_SIZE) {
                        lock.wait();
                    }
                    if (produceStop) {
                        break;
                    }
                    int v = count.getAndIncrement();
                    queue.add(v);
                    System.out.println(Thread.currentThread().getName() + " - " + v);
                    lock.notifyAll();
                    lock.wait();
                    Thread.sleep(Util.nextInt(50));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public void stopProduce() {
        synchronized (lock) {
            this.produceStop = true;
            lock.notifyAll();
        }
    }
}
