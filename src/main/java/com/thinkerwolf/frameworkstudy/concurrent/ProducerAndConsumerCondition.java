package com.thinkerwolf.frameworkstudy.concurrent;

import com.thinkerwolf.frameworkstudy.common.Util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumerCondition {
    private final Queue<Integer> queue;
    private final ReentrantLock lock = new ReentrantLock(false);
    private AtomicInteger count = new AtomicInteger(1);
    private int MAX_SIZE = 100;
    private volatile boolean stopProduce = false;
    private Condition condProduce = lock.newCondition();
    private Condition condConsume = lock.newCondition();

    public ProducerAndConsumerCondition() {
        this.queue = new LinkedList<>();
    }

    public static void main(String[] args) {
        ProducerAndConsumerCondition pc = new ProducerAndConsumerCondition();
        Thread p1 = new Thread(() -> {
            pc.produce();
        }, "p1");
        Thread p2 = new Thread(() -> {
            pc.produce();
        }, "p2");
        Thread c1 = new Thread(() -> {
            pc.consume();
        }, "c1");
        Thread c2 = new Thread(() -> {
            pc.consume();
        }, "c2");
        p1.start();
        p2.start();
        c1.start();
        c2.start();
        System.out.println("start .....");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pc.stopProduce();
    }

    private boolean checkConsumeStop() {
        return queue.isEmpty() && stopProduce;
    }

    public void consume() {
        lock.lock();
        try {
            for (; ; ) {
                try {
                    if (checkConsumeStop()) {
                        break;
                    }
                    while (queue.isEmpty()) {
                        condConsume.await();
                    }
                    System.out.println(Thread.currentThread().getName() + " - " + queue.poll());
                    condProduce.signalAll();
                    if (checkConsumeStop()) {
                        break;
                    }
                    condConsume.await(); // 停止一下，给其他消费者机会消费
                    Thread.sleep(Util.nextInt(100));
                } catch (InterruptedException e) {
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void produce() {
        lock.lock();
        try {
            for (; ; ) {
                try {
                    if (stopProduce) {
                        break;
                    }
                    while (queue.size() > MAX_SIZE) {
                        condProduce.await();
                    }
                    if (stopProduce) {
                        break;
                    }
                    int v = count.getAndIncrement();
                    queue.add(v);
                    System.out.println(Thread.currentThread().getName() + " - " + v);
                    condConsume.signalAll();
                    condProduce.await(); // 停止一下，给其他生产者机会生产
                    Thread.sleep(Util.nextInt(50));
                } catch (InterruptedException e) {
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void stopProduce() {
        lock.lock();
        try {
            this.stopProduce = true;
            condConsume.signalAll();
            condProduce.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
