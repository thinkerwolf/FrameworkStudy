package com.thinkerwolf.frameworkstudy.concurrent;

import com.thinkerwolf.frameworkstudy.common.Util;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在两个线程中交替打印"1234567890"和"abcdefhijk"中的字符
 *
 * @author wukai
 * @date 2020/4/17 17:39
 */
public class WaitNotifyAlternatePrint {

    private static char[] ca1 = "1234567890".toCharArray();
    private static char[] ca2 = "abcdefhijk".toCharArray();

    public static void main(String[] args) {
        WaitNotifyAlternatePrint p = new WaitNotifyAlternatePrint();

        long start = System.currentTimeMillis();
        p.synchronizedPrint();
        System.err.println("synchronizedPrint " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        p.lockPrint();
        System.err.println("lockPrint " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        p.casPrint();
        System.err.println("casPrint " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        p.pipedPrint();
        System.err.println("pipedPrint " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        p.transferQueuePrint();
        System.err.println("transferQueuePrint " + (System.currentTimeMillis() - start));
    }

    public void synchronizedPrint() {
        final Object lock = new Object();
        final AtomicBoolean oneFinish = new AtomicBoolean(false);
        Thread t1 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            synchronized (lock) {
                for (char c : ca1) {
                    System.out.println(n + ":" + c);
                    if (!oneFinish.get()) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
                oneFinish.set(true);
                lock.notify();
            }
        });
        Thread t2 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            synchronized (lock) {
                for (char c : ca2) {
                    System.out.println(n + ":" + c);
                    if (!oneFinish.get()) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
                oneFinish.set(true);
                lock.notify();
            }
        });
        t1.start();
        t2.start();
        Util.joinQuietly(t1);
        Util.joinQuietly(t2);
    }

    public void lockPrint() {
        ReentrantLock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Thread t1 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            lock.lock();
            try {
                for (char c : ca1) {
                    System.out.println(n + ":" + c);
                    c2.signal();
                    try {
                        c1.await();
                    } catch (InterruptedException ignored) {
                    }
                }
                c2.signal();
            } finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            lock.lock();
            try {
                for (char c : ca2) {
                    System.out.println(n + ":" + c);
                    c1.signal();
                    try {
                        c2.await();
                    } catch (InterruptedException ignored) {
                    }
                }
                c1.signal();
            } finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
        Util.joinQuietly(t1);
        Util.joinQuietly(t2);
    }

    public void casPrint() {
        AtomicBoolean b = new AtomicBoolean();
        Thread t1 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            for (char c : ca1) {
                for (; ; ) {
                    if (b.get()) {
                        System.out.println(n + ":" + c);
                        b.set(false);
                        break;
                    }
                }
            }
        });
        Thread t2 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            for (char c : ca2) {
                for (; ; ) {
                    if (!b.get()) {
                        System.out.println(n + ":" + c);
                        b.set(true);
                        break;
                    }
                }
            }
        });
        t1.start();
        t2.start();
        Util.joinQuietly(t1);
        Util.joinQuietly(t2);
    }

    public void pipedPrint() {
        final PipedInputStream input1 = new PipedInputStream();
        final PipedInputStream input2 = new PipedInputStream();
        final PipedOutputStream output1 = new PipedOutputStream();
        final PipedOutputStream output2 = new PipedOutputStream();
        try {
            output1.connect(input2);
            output2.connect(input1);
        } catch (IOException e) {
            return;
        }

        Thread t1 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            for (char c : ca1) {
                try {
                    input1.read();
                } catch (IOException ignored) {
                }
                System.out.println(n + ":" + c);
                try {
                    output1.write(1);
                } catch (IOException ignored) {
                }
            }
        });
        Thread t2 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            try {
                output2.write(2);
            } catch (IOException ignored) {
            }
            for (char c : ca2) {
                try {
                    input2.read();
                } catch (IOException ignored) {
                }
                System.out.println(n + ":" + c);
                try {
                    output2.write(2);
                } catch (IOException ignored) {
                }
            }
        });
        t1.start();
        t2.start();
        Util.joinQuietly(t1);
        Util.joinQuietly(t2);
    }

    public void transferQueuePrint() {
        TransferQueue<Character> transfer1 = new LinkedTransferQueue<>();
        TransferQueue<Character> transfer2 = new LinkedTransferQueue<>();
        Thread t1 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            for (char c : ca2) {
                try {
                    transfer2.put(c);
                    char ch = transfer1.take();
                    System.out.println(n + ":" + ch);
                } catch (InterruptedException ignored) {
                }
            }
        });
        Thread t2 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            for (char c : ca1) {
                try {
                    char ch = transfer2.take();
                    System.out.println(n + ":" + ch);
                    transfer1.put(c);
                } catch (InterruptedException ignored) {
                }
            }
        });
        t1.start();
        t2.start();
        Util.joinQuietly(t1);
        Util.joinQuietly(t2);
    }

}
