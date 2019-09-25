package com.thinkerwolf.frameworkstudy.concurrent;

import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerAndConsumer {


    static class Producer extends Thread {

        final List<Integer> queue;

        Producer(String name, List<Integer> queue) {
            super(name);
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                synchronized (queue) {
                    for (; ; ) {
                        if (!queue.isEmpty()) {
                            queue.wait();
                        }
                        if (!queue.isEmpty()) {
                            continue;
                        }
                        for (int i = 0; i < 10; i++) {
                            Thread.sleep(100);
                            queue.add(Util.nextInt(1, 100));
                        }
                        queue.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class Consumer extends Thread {
        final List<Integer> queue;
        AtomicInteger count = new AtomicInteger(1);

        Consumer(String name, List<Integer> queue) {
            super(name);
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                synchronized (queue) {
                    for (; ; ) {
                        if (queue.isEmpty()) {
                            queue.wait();
                        }
                        if (queue.isEmpty()) {
                            continue;
                        }

                        for (Integer i : queue) {
                            System.out.println(Thread.currentThread().getName() + " - " + i);
                        }
                        queue.clear();
                        System.out.println("execute times -> " + count.getAndIncrement());
                        queue.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                String name = Thread.currentThread().getName();
                System.out.println("consumer " + name + " stop....");
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        Producer producer = new Producer("p1", list);
        Producer producer1 = new Producer("p2", list);
        Consumer consumer = new Consumer("c1", list);
        Consumer consumer1 = new Consumer("c2", list);
        producer.start();
        producer1.start();
        consumer.start();
        consumer1.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        consumer1.interrupt();

    }
}
