package com.thinkerwolf.frameworkstudy.concurrent;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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
        p.synchronizedPrint();
        p.lockPrint();
        p.casPrint();
        p.pipedPrint();
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
                        } catch (InterruptedException e) {
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
                        } catch (InterruptedException e) {
                        }
                    }
                }
                oneFinish.set(true);
                lock.notify();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }
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
                    } catch (InterruptedException e) {
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
                    } catch (InterruptedException e) {
                    }
                }
                c1.signal();
            } finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }
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
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }
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
                } catch (IOException e) {
                }
                System.out.println(n + ":" + c);
                try {
                    output1.write(1);
                } catch (IOException e) {
                }
            }
        });
        Thread t2 = new Thread(() -> {
            String n = Thread.currentThread().getName();
            try {
                output2.write(2);
            } catch (IOException e) {
            }
            for (char c : ca2) {
                try {
                    input2.read();
                } catch (IOException e) {
                }
                System.out.println(n + ":" + c);
                try {
                    output2.write(2);
                } catch (IOException e) {
                }
            }
        });
        t1.start();
        t2.start();
    }

}
