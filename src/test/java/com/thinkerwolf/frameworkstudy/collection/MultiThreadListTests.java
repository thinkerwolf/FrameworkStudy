package com.thinkerwolf.frameworkstudy.collection;

import com.thinkerwolf.frameworkstudy.common.Util;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 多线程下jdk不同List的执行效率
 *
 * @author wukai
 */
public class MultiThreadListTests {


    public static void test(List list) {
        Thread[] threads = new Thread[100];
        for (int d = 0; d < threads.length; d++) {
            threads[d] = new Thread(() -> {
                int startSize = list.size();
                for (int i = 0; i < 1000; i++)
                    list.add(i + "-" + Util.nextInt(100000));
                for (int i = startSize; i < startSize + 1000; i++) {
                    list.get(i);
                }
            });
        }
        final long start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }


        System.out.printf(list.getClass().getSimpleName() + "-> Num:%d, T:%d\n", list.size(), (System.currentTimeMillis() - start));
    }


    public static void main(String[] args) {
        test(new CopyOnWriteArrayList());
        test(new Vector());
    }

}
